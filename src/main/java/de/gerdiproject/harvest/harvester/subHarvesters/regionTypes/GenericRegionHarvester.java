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
package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;


import de.gerdiproject.harvest.harvester.subHarvesters.AbstractSauFeatureHarvester;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollectionResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.extension.ResearchData;
import de.gerdiproject.json.datacite.extension.WebLink;
import de.gerdiproject.json.geo.GeoJson;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;


/**
 * This harvester deals with creating documents for a generic SeaAroundUs region type, such as EEZ, LME, or RFMO.
 *
 * @author Robin Weiss
 */
public class GenericRegionHarvester<T extends GenericRegion> extends AbstractSauFeatureHarvester<FeatureCollectionResponse, FeatureProperties>
{
    protected final RegionParametersVO params;
    private final Type responseType;


    /**
     *
     * @param harvestedDocuments
     *            the list to which harvested documents are added
     * @param regionType
     *            the type of the regions that are to be harvested
     * @param dimensions
     *            the dimensions that are available for each region
     * @param urls
     *            a value object of URLs that are used to retrieve region
     *            metadata
     * @param numberOfDocumentsPerEntry
     *            the maximum number of documents that can be retrieved from a
     *            single entry
     */
    public GenericRegionHarvester(TypeToken<GenericResponse<T>> responseTypeToken, RegionParametersVO params)
    {
        super(
            String.format(
                SeaAroundUsDataCiteConstants.NAME_FORMAT,
                params.getRegionType().urlName.charAt(0),
                params.getRegionType().urlName.substring(1)),
            params.getRegionType().urlName,
            FeatureCollectionResponse.class
        );

        this.responseType = responseTypeToken.getType();
        this.params = params;
    }


    @Override
    protected void enrichDocument(DataCiteJson document, String apiUrl, Feature<FeatureProperties> entry)
    {
        document.setFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);

        GenericResponse<T> response = httpRequester.getObjectFromUrl(apiUrl, responseType);
        T regionObject = response.getData();

        enrichSubjects(document.getSubjects(), regionObject);
        enrichWebLinks(document.getWebLinks(), regionObject);

        document.setResearchDataList(new LinkedList<ResearchData>());
        enrichFiles(document.getResearchDataList(), regionObject);

        document.setGeoLocations(new LinkedList<GeoLocation>());
        enrichGeoLocations(document.getGeoLocations(), regionObject);
    }


    @Override
    protected String getMainTitleString(String regionName)
    {
        return String.format(
                   SeaAroundUsDataCiteConstants.GENERIC_LABEL,
                   params.getRegionType().displayName,
                   regionName);
    }


    /**
     * Parses a metrics array from a region object and creates a subject for each metric that is not zero.
     *
     * @param subjects a list of {@linkplain Subject}s from the harvested document
     * @param regionObject the region object source
     */
    protected void enrichSubjects(List<Subject> subjects, T regionObject)
    {
        regionObject.getMetrics().forEach((Metric m) -> {
            if (m.getValue() != 0)
                subjects.add(new Subject(m.getTitle()));
        });
    }

    /**
     * Parses a region object and adds relevant weblinks to a harvested document.
     *
     * @param links a list of {@linkplain WebLink}s from the harvested document
     * @param regionObject the region object source
     */
    protected void enrichWebLinks(List<WebLink> links, T regionObject)
    {
        int regionId = regionObject.getId();
        String regionName = regionObject.getTitle();

        links.add(SeaAroundUsDataCiteUtils.instance().createMarineTrophicIndexLink(params, regionId, regionName));
        links.add(SeaAroundUsDataCiteUtils.instance().createPrimaryProductionLink(params, regionId, regionName));
        links.add(SeaAroundUsDataCiteUtils.instance().createStockStatusLink(params, regionId, regionName));

        // add catches
        links.addAll(SeaAroundUsDataCiteUtils.instance().createCatchLinks(params, regionId, regionName));
    }


    /**
     * Parses a region object and adds relevant files to a harvested document.
     *
     * @param files a list of {@linkplain File}s from the harvested document
     * @param regionObject the region object source
     */
    protected void enrichFiles(List<ResearchData> files, T regionObject)
    {
        int regionId = regionObject.getId();
        String regionName = regionObject.getTitle();

        files.add(SeaAroundUsDataCiteUtils.instance().createMarineTrophicIndexFile(params, regionId, regionName));
        files.add(SeaAroundUsDataCiteUtils.instance().createPrimaryProductionFile(params, regionId, regionName));
        files.add(SeaAroundUsDataCiteUtils.instance().createStockStatusFile(params, regionId, regionName));

        // add catches
        files.addAll(SeaAroundUsDataCiteUtils.instance().createCatchFiles(params, regionId, regionName));
    }


    /**
     * Retrieves the {@linkplain GeoJson} from the base region object and adds it to a list of {@linkplain GeoLocation}s.
     *
     * @param geoLocations a list of {@linkplain GeoLocation}s from the harvested document
     * @param regionObject the region object source
     */
    protected void enrichGeoLocations(List<GeoLocation> geoLocations, T regionObject)
    {
        List<GeoJson> polys = new LinkedList<>();
        polys.add(regionObject.getGeojson());

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setPolygons(polys);
        geoLocations.add(geoLocation);
    }
}
