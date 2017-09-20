/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package de.gerdiproject.harvest.harvester.subHarvesters;

import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.DimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.Entry;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollectionResponse;
import de.gerdiproject.harvest.seaaroundus.json.mariculture.SauMariculture;
import de.gerdiproject.harvest.seaaroundus.json.mariculture.SauMaricultureResponse;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;

import java.util.LinkedList;
import java.util.List;

/**
 * This harvester crawls through all SeaAroundUs maricultures and generates one document per entry.
 * <br><br>
 * All Maricultures:     http://api.seaaroundus.org/api/v1/mariculture/<br>
 * Example Mariculture: http://api.seaaroundus.org/api/v1/mariculture/57
 *
 * @author Robin Weiss
 */
public class MaricultureHarvester extends AbstractSauFeatureHarvester<FeatureCollectionResponse, FeatureProperties>
{
    /**
     * Creates a harvester for harvesting all maricultures.
     */
    public MaricultureHarvester()
    {
        super("mariculture", FeatureCollectionResponse.class);
    }


    @Override
    protected String getMainTitleString(String regionName)
    {
        return DataCiteConstants.MARICULTURE_LABEL_PREFIX + regionName;
    }


    @Override
    protected void enrichDocument(DataCiteJson document, String apiUrl, Feature<FeatureProperties> entry)
    {
        List<SauMariculture> subRegions = httpRequester.getObjectFromUrl(apiUrl, SauMaricultureResponse.class).getData();

        // add completely new data
        document.setFiles(createFiles(apiUrl, entry.getProperties(), subRegions));

        // enrich existing data
        enrichSubjects(document.getSubjects(), subRegions);
        enrichGeoLocations(document.getGeoLocations(), subRegions);
    }


    /**
     * Creates a list of downloadable {@linkplain File}s of the mariculture datasets.
     *
     * @param apiUrl the API URL for SeaAroundUs maricultures
     * @param properties mariculture properties
     * @param subRegions a list of relevant regions within the mariculture country
     *
     * @return a list of downloadable {@linkplain File}s
     */
    private List<File> createFiles(String apiUrl, FeatureProperties properties, List<SauMariculture> subRegions)
    {
        String countryName = properties.getTitle();
        int regionId = getRegionId(properties);

        List<File> files = new LinkedList<>();

        for (Entry dimension : DimensionConstants.DIMENSIONS_MARICULTURE) {
            // add download for combined sub-regions
            files.add(new File(
                          String.format(UrlConstants.MARICULTURE_DOWNLOAD_ALL_URL, apiUrl, dimension.urlName, regionId),
                          String.format(DataCiteConstants.MARICULTURE_FILE_NAME, dimension.displayName, countryName)));

            // add sub-region downloads
            subRegions.forEach((SauMariculture subRegion) -> {
                files.add(new File(
                              String.format(UrlConstants.MARICULTURE_DOWNLOAD_SUBREGION_URL, apiUrl, dimension.urlName, regionId, subRegion.getRegionId()),
                              String.format(DataCiteConstants.MARICULTURE_SUBREGION_FILE_NAME, dimension.displayName, countryName, subRegion.getTitle())));
            });
        }

        return files;
    }


    /**
     * Enriches a list of {@linkplain Subject}s for a SeaAroundUs mariculture.
     *
     * @param subjects the subjects that are to be enriched
     * @param subRegions a list of relevant regions within the mariculture country
     */
    private void enrichSubjects(List<Subject> subjects, List<SauMariculture> subRegions)
    {
        // add titles of sub-regions
        subRegions.forEach((SauMariculture subRegion) -> {
            Subject subRegionTitle = new Subject(subRegion.getTitle());
            subRegionTitle.setLang(DataCiteConstants.SAU_LANGUAGE);
            subjects.add(subRegionTitle);
        });
    }


    /**
     * Enriches {@linkplain GeoLocation}s by sub-region geo data.
     *
     * @param geoLocations the geolocations that are to be enriched
     * @param subRegions a list of relevant regions within the mariculture country
     */
    protected void enrichGeoLocations(List<GeoLocation> geoLocations, List<SauMariculture> subRegions)
    {
        // add sub-region geometry
        subRegions.forEach((SauMariculture subRegion) -> {

            // only add location if it has geo json data
            if (subRegion.getGeojson() != null || subRegion.getPointGeojson() != null)
            {
                GeoLocation subLocation = new GeoLocation();
                subLocation.setPolygon(subRegion.getGeojson());
                subLocation.setPoint(subRegion.getPointGeojson());
                subLocation.setPlace(subRegion.getTitle());

                geoLocations.add(subLocation);
            }
        });
    }
}
