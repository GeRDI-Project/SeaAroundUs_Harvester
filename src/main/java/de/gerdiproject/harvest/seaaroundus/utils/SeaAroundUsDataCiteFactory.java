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
package de.gerdiproject.harvest.seaaroundus.utils;

import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.harvest.seaaroundus.vos.UrlVO;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Source;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;
import de.gerdiproject.json.geo.GeoJson;

/**
 * A static helper class for creating SeaAroundUs DataCite fields.
 *
 * @author Robin Weiss
 */
public final class SeaAroundUsDataCiteFactory
{
    private static SeaAroundUsDataCiteFactory instance = new SeaAroundUsDataCiteFactory();

    private String singleEntryApiUrl = null;
    private String allEntriesApiUrl = null;
    private String catchesApiUrl = null;


    /**
     * Private constructor, because this is a static class
     */
    private SeaAroundUsDataCiteFactory()
    {

    }


    /**
     * Retrieves the singleton instance of this class.
     *
     * @return the singleton instance of this class
     */
    public static SeaAroundUsDataCiteFactory instance()
    {
        return instance;
    }


    /**
     * Changes the SeaAroundUs API version.
     *
     * @param version the SeaAroundUs API version
     */
    public void setVersion(String version)
    {
        String apiPrefix = String.format(SeaAroundUsUrlConstants.API_URL, version);
        singleEntryApiUrl = apiPrefix + SeaAroundUsUrlConstants.REGION_URL_SUFFIX;
        allEntriesApiUrl = apiPrefix + SeaAroundUsUrlConstants.REGION_IDS_URL;
        catchesApiUrl = apiPrefix + SeaAroundUsUrlConstants.CATCHES_URL;
    }


    /**
     * Creates the {@linkplain Source} URL object for a SeaAroundUs region.
     *
     * @param apiUrl the SeaAroundUs API URL of a single region
     *
     * @return the source URL object for a region
     */
    public Source createSource(String apiUrl)
    {
        Source source = new Source(
            apiUrl,
            SeaAroundUsDataCiteConstants.PROVIDER);
        source.setProviderURI(SeaAroundUsUrlConstants.PROVIDER_URI);
        return source;
    }


    /**
     * Assembles a URL to download an overview of specified regions.
     *
     * @param regionName the SeaAroundUs API name of the region
     *
     * @return a URL to download all regions
     */
    public String getAllRegionsUrl(String regionName)
    {
        return String.format(allEntriesApiUrl, regionName);
    }


    /**
     * Assembles a URL to view the JSON response of a single region.
     *
     * @param regionName the SeaAroundUs API name of the region
     * @param regionId a SeaAroundUs unique identifier of a region
     *
     * @return a URL to download a single regions
     */
    public String getRegionEntryUrl(String regionName, int regionId)
    {
        return String.format(singleEntryApiUrl, regionName, regionId);
    }


    /**
     * Assembles a URL to download the catch data of a single region.
     *
     * @param regionName the SeaAroundUs API name of the region
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param measure the catch measure
     * @param dimension the catch dimension
     *
     * @return a URL to download the catch data of a single region
     */
    public String getCatchesUrl(String regionName, int regionId, EntryVO measure, EntryVO dimension)
    {
        return String.format(catchesApiUrl, regionName, measure.urlName, dimension.urlName, regionId);
    }


    /**
     * Creates a link to the primary production of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a link to the primary production of a region
     */
    public WebLink createPrimaryProductionLink(RegionParametersVO regionParams, int regionId, String regionName)
    {
        EntryVO regionType = regionParams.getRegionType();
        String primaryProductionLabel = String.format(SeaAroundUsDataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionType.displayName, regionName);
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionType.urlName);

        WebLink primaryProduction = new WebLink(regionParams.getUrls().getPrimaryProductionViewUrl(viewUrl, regionId));
        primaryProduction.setName(primaryProductionLabel);
        primaryProduction.setType(WebLinkType.ViewURL);

        return primaryProduction;
    }


    /**
     * Creates a link to the stock status of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a link to the stock status of a region
     */
    public WebLink createStockStatusLink(RegionParametersVO regionParams, int regionId, String regionName)
    {
        EntryVO regionType = regionParams.getRegionType();
        String stockStatusLabel = String.format(SeaAroundUsDataCiteConstants.STOCK_STATUS_LABEL, regionType.displayName, regionName);
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionType.urlName);

        WebLink stockStatus = new WebLink(regionParams.getUrls().getStockStatusViewUrl(viewUrl, regionId));
        stockStatus.setName(stockStatusLabel);
        stockStatus.setType(WebLinkType.ViewURL);

        return stockStatus;
    }


    /**
     * Creates a link to the marine trophic index of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a link to the marine trophic index of a region
     */
    public WebLink createMarineTrophicIndexLink(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String marineTrophicIndexLabel = String.format(SeaAroundUsDataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionParams.getRegionType().urlName);

        WebLink marineTrophicIndex = new WebLink(regionParams.getUrls().getMarineTrophicIndexViewUrl(viewUrl, regionId));
        marineTrophicIndex.setName(marineTrophicIndexLabel);
        marineTrophicIndex.setType(WebLinkType.ViewURL);

        return marineTrophicIndex;
    }


    /**
     * Creates a file of the primary production of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a file of the primary production of a region
     */
    public File createPrimaryProductionFile(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String primaryProductionLabel = String.format(SeaAroundUsDataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionParams.getRegionType().displayName, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().urlName);

        File primaryProduction = new File(regionParams.getUrls().getPrimaryProductionDownloadUrl(apiUrl, regionId),
                                          primaryProductionLabel);
        primaryProduction.setType(SeaAroundUsDataCiteConstants.CSV_FORMAT);

        return primaryProduction;
    }


    /**
     * Creates a file of the stock status of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a file of the stock status of a region
     */
    public File createStockStatusFile(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String stockStatusLabel = String.format(SeaAroundUsDataCiteConstants.STOCK_STATUS_LABEL, regionParams.getRegionType().displayName, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().urlName);

        File stockStatus = new File(regionParams.getUrls().getStockStatusDownloadUrl(apiUrl, regionId),
                                    stockStatusLabel);
        stockStatus.setType(SeaAroundUsDataCiteConstants.CSV_FORMAT);

        return stockStatus;
    }


    /**
     * Creates a file of the marine trophic index of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a file of the marine trophic index of a region
     */
    public File createMarineTrophicIndexFile(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String marineTrophicIndexLabel = String.format(SeaAroundUsDataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().urlName);

        File marineTrophicIndex = new File(
            regionParams.getUrls().getMarineTrophicIndexDownloadUrl(apiUrl, regionId),
            marineTrophicIndexLabel);
        marineTrophicIndex.setType(SeaAroundUsDataCiteConstants.CSV_FORMAT);

        return marineTrophicIndex;
    }


    /**
     * Creates a list of {@linkplain File}s regarding catches of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a list of {@linkplain File}s regarding catches of a region
     */
    public List<File> createCatchFiles(RegionParametersVO regionParams, int regionId, String regionName)
    {
        List<File> files = new LinkedList<>();

        EntryVO regionType = regionParams.getRegionType();
        List<EntryVO> dimensionList = regionParams.getDimensions();
        List<EntryVO> measureList = regionParams.getMeasures();
        UrlVO urls = regionParams.getUrls();
        String apiUrl = getAllRegionsUrl(regionType.urlName);

        for (EntryVO measure : measureList) {
            for (EntryVO dimension : dimensionList) {

                String catchesLabel = String.format(
                                          SeaAroundUsDataCiteConstants.CATCHES_LABEL,
                                          measure.displayName,
                                          dimension.displayName,
                                          regionType.displayName,
                                          regionName);
                File cbdFile = new File(
                    urls.getCatchesDownloadUrl(apiUrl, regionId, dimension.urlName, measure.urlName) + SeaAroundUsUrlConstants.CSV_FORM,
                    catchesLabel);
                cbdFile.setType(SeaAroundUsDataCiteConstants.CSV_FORMAT);
                files.add(cbdFile);
            }
        }

        return files;
    }


    /**
     * Creates a list of {@linkplain WebLink}s regarding catches of a region.
     *
     * @param regionParams parameters describing the region for which the link is created
     * @param regionId a SeaAroundUs unique identifier of a region
     * @param regionName the name of the linked region
     *
     * @return a list of {@linkplain WebLink}s regarding catches of a region
     */
    public List<WebLink> createCatchLinks(RegionParametersVO regionParams, int regionId, String regionName)
    {
        List<WebLink> links = new LinkedList<>();

        EntryVO regionType = regionParams.getRegionType();
        List<EntryVO> dimensionList = regionParams.getDimensions();
        List<EntryVO> measureList = regionParams.getMeasures();
        UrlVO urls = regionParams.getUrls();
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionType.urlName);

        for (EntryVO measure : measureList) {
            for (EntryVO dimension : dimensionList) {

                String catchesLabel = String.format(
                                          SeaAroundUsDataCiteConstants.CATCHES_LABEL,
                                          measure.displayName,
                                          dimension.displayName,
                                          regionType.displayName,
                                          regionName);
                WebLink cbdLink = new WebLink(urls.getCatchesViewUrl(viewUrl, regionId, dimension.urlName, measure.urlName));
                cbdLink.setName(catchesLabel);
                cbdLink.setType(WebLinkType.ViewURL);
                links.add(cbdLink);
            }
        }

        return links;
    }


    /**
     * Creates a list of region related {@linkplain WebLink}s.
     *
     * @param regionId a unique ID of a region within its domain
     *
     * @return the ViewURL and LogoURL
     */
    public List<WebLink> createBasicWebLinks(String regionApiName, int regionId)
    {
        List<WebLink> webLinks = new LinkedList<>();
        webLinks.add(SeaAroundUsDataCiteConstants.LOGO_LINK);

        WebLink viewLink = new WebLink(String.format(SeaAroundUsUrlConstants.VIEW_URL, regionApiName, regionId));
        viewLink.setType(WebLinkType.ViewURL);
        webLinks.add(viewLink);

        return webLinks;
    }


    /**
     * Creates a {@linkplain GeoLocation} object for the region.
     *
     * @param regionBorders a {@linkplain GeoJson} describing the region border or null
     * @param regionName a descriptive name of the region
     *
     * @return a {@linkplain GeoLocation} describing the region border or null
     *      if no {@linkplain GeoJson} data exists for the region feature
     */
    public List<GeoLocation> createBasicGeoLocations(GeoJson regionBorders, String regionName)
    {
        List<GeoLocation> geoLocations = new LinkedList<>();

        if (regionBorders != null) {
            GeoLocation g = new GeoLocation();
            g.setPlace(regionName);
            g.setPolygon(regionBorders);
            geoLocations.add(g);
        }

        return geoLocations;
    }
}
