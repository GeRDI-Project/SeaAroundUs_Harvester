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
import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.Entry;
import de.gerdiproject.harvest.seaaroundus.constants.RegionParameters;
import de.gerdiproject.harvest.seaaroundus.constants.UrlVO;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollectionResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

import java.util.List;

import com.google.gson.reflect.TypeToken;


/**
 *
 * @author Robin Weiss
 */
public class GenericRegionHarvester<T extends GenericRegion> extends AbstractSauFeatureHarvester<FeatureCollectionResponse, FeatureProperties>
{
    private final static Entry MEASURE_VALUE = new Entry("value", "Real 2010 value (US$)");
    private final static Entry MEASURE_TONNAGE = new Entry("tonnage", "Catches");

    static final Entry[] MEASURES = { MEASURE_VALUE, MEASURE_TONNAGE };

    protected final RegionParameters params;


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
    public GenericRegionHarvester(RegionParameters params)
    {
        super(
            String.format(
                DataCiteConstants.NAME_FORMAT,
                params.getRegionType().urlName.charAt(0),
                params.getRegionType().urlName.substring(1)),
            params.getRegionType().urlName,
            FeatureCollectionResponse.class
        );

        this.params = params;
    }


    @Override
    protected void enrichDocument(DataCiteJson document, String apiUrl, Feature<FeatureProperties> entry)
    {
        TypeToken<GenericResponse<T>> typeToken = new TypeToken<GenericResponse<T>>() {};
        T regionObject = httpRequester.<GenericResponse<T>>getObjectFromUrl(apiUrl, typeToken.getType()).getData();

        // add subjects
        enrichSubjects(document.getSubjects(), regionObject);

        // add links and files
        enrichWebLinksAndFiles(
            document.getWebLinks(),
            document.getFiles(),
            apiUrl,
            regionObject
        );
    }


    @Override
    protected String getMainTitleString(String regionName)
    {
        return String.format(
                   DataCiteConstants.GENERIC_LABEL,
                   params.getRegionType().displayName,
                   regionName);
    }


    /**
     * Creates weblinks and downloads for a region and adds them to a document
     *
     * @param weblinks the weblinks list of the document
     * @param files the file list of the document
     * @param apiUrl a download URL prefix
     * @param regionObject the region object source
     */
    protected void enrichWebLinksAndFiles(List<WebLink> weblinks, List<File> files, String apiUrl, T regionObject)
    {
        int regionId = regionObject.getId();
        String viewUrl = getViewUrl(regionId);
        String regionName = regionObject.getTitle();
        Entry regionType = params.getRegionType();
        UrlVO urls = params.getUrls();
        List<Entry> dimensions = params.getDimensions();

        // Primary Production
        String primaryProductionLabel = String.format(DataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionType.displayName, regionName);

        WebLink primaryProduction = new WebLink(urls.getPrimaryProductionViewUrl(viewUrl, regionId));
        primaryProduction.setName(primaryProductionLabel);
        primaryProduction.setType(WebLinkType.ViewURL);
        weblinks.add(primaryProduction);

        File ppFile = new File(
            urls.getPrimaryProductionDownloadUrl(apiUrl, regionId),
            primaryProductionLabel);
        files.add(ppFile);

        // Stock Status
        String stockStatusLabel = String.format(DataCiteConstants.STOCK_STATUS_LABEL, regionType.displayName, regionName);

        WebLink stockStatus = new WebLink(urls.getStockStatusViewUrl(viewUrl, regionId));
        stockStatus.setName(stockStatusLabel);
        stockStatus.setType(WebLinkType.ViewURL);
        weblinks.add(stockStatus);

        File ssFile = new File(
            urls.getStockStatusDownloadUrl(apiUrl, regionId),
            stockStatusLabel);
        files.add(ssFile);

        // Marine Trophic Index
        String marineTrophicIndexLabel = String.format(DataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);

        WebLink marineTrophicIndex = new WebLink(urls.getMarineTrophicIndexViewUrl(viewUrl, regionId));
        marineTrophicIndex.setName(marineTrophicIndexLabel);
        marineTrophicIndex.setType(WebLinkType.ViewURL);
        weblinks.add(marineTrophicIndex);

        File mtiFile = new File(
            urls.getMarineTrophicIndexViewUrl(viewUrl, regionId),
            marineTrophicIndexLabel);
        files.add(mtiFile);

        // Catches and Value
        for (Entry measure : MEASURES) {
            for (Entry dimension : dimensions) {

                WebLink catchesByDimension = new WebLink(
                    urls.getCatchesViewUrl(viewUrl, regionId, dimension.urlName, measure.urlName));

                String catchesLabel = String.format(
                                          DataCiteConstants.CATCHES_LABEL,
                                          measure.displayName,
                                          dimension.displayName,
                                          regionType.displayName,
                                          regionName
                                      );
                catchesByDimension.setName(catchesLabel);
                catchesByDimension.setType(WebLinkType.ViewURL);
                weblinks.add(catchesByDimension);

                File cbdFile = new File(
                    urls.getCatchesDownloadUrl(apiUrl, regionId, dimension.urlName, measure.urlName),
                    catchesLabel);
                files.add(cbdFile);
            }
        }
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
     * Retrieves the {@linkplain GeoJson} from the base region object and adds it to a list of {@linkplain GeoLocation}s.
     *
     * @param geoLocations a list of {@linkplain GeoLocation}s from the harvested document
     * @param regionObject the region object source
     */
    protected void enrichGeoLocations(List<GeoLocation> geoLocations, T regionObject)
    {
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setPolygon(regionObject.getGeojson());
        geoLocations.add(geoLocation);
    }
}
