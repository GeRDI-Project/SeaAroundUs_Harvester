/**
 * Copyright © 2017 Robin Weiss (http://www.gerdi-project.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.gerdiproject.harvest.seaaroundus.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.harvest.seaaroundus.vos.UrlVO;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.extension.generic.ResearchData;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;
import de.gerdiproject.json.geo.GeoJson;

/**
 * A static helper class for creating SeaAroundUs DataCite fields.
 *
 * @author Robin Weiss
 */
public final class SeaAroundUsDataCiteUtils
{
    /**
     * Private constructor, because this is a static class
     */
    private SeaAroundUsDataCiteUtils()
    {

    }


    /**
     * Assembles a URL to download an overview of specified regions.
     *
     * @param regionName the SeaAroundUs API name of the region
     *
     * @return a URL to download all regions
     */
    public static String getAllRegionsUrl(String regionName)
    {
        return String.format(SeaAroundUsUrlConstants.REGION_IDS_URL, regionName);
    }


    /**
     * Assembles a URL to view the JSON response of a single region.
     *
     * @param regionName the SeaAroundUs API name of the region
     * @param regionId a SeaAroundUs unique identifier of a region
     *
     * @return a URL to download a single regions
     */
    public static String getRegionEntryUrl(String regionName, int regionId)
    {
        return String.format(SeaAroundUsUrlConstants.REGION_URL, regionName, regionId);
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
    public static String getCatchesUrl(String regionName, int regionId, EntryVO measure, EntryVO dimension)
    {
        return String.format(SeaAroundUsUrlConstants.CATCHES_URL, regionName, measure.getUrlName(), dimension.getUrlName(), regionId);
    }

    /**
     * Creates a link to the source of a document.
     * @param apiUrl the URL that leads to the source JSON
     *
     * @return a link to the source of a document
     */
    public static WebLink createSourceLink(String apiUrl)
    {
        WebLink source = new WebLink(apiUrl);
        source.setType(WebLinkType.SourceURL);
        return source;
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
    public static WebLink createPrimaryProductionLink(RegionParametersVO regionParams, int regionId, String regionName)
    {
        EntryVO regionType = regionParams.getRegionType();
        String primaryProductionLabel = String.format(SeaAroundUsDataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionType.getDisplayName(), regionName);
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionType.getUrlName());

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
    public static WebLink createStockStatusLink(RegionParametersVO regionParams, int regionId, String regionName)
    {
        EntryVO regionType = regionParams.getRegionType();
        String stockStatusLabel = String.format(SeaAroundUsDataCiteConstants.STOCK_STATUS_LABEL, regionType.getDisplayName(), regionName);
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionType.getUrlName());

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
    public static WebLink createMarineTrophicIndexLink(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String marineTrophicIndexLabel = String.format(SeaAroundUsDataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionParams.getRegionType().getUrlName());

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
    public static ResearchData createPrimaryProductionFile(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String primaryProductionLabel = String.format(SeaAroundUsDataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionParams.getRegionType().getDisplayName(), regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().getUrlName());

        ResearchData primaryProduction = new ResearchData(regionParams.getUrls().getPrimaryProductionDownloadUrl(apiUrl, regionId),
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
    public static ResearchData createStockStatusFile(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String stockStatusLabel = String.format(SeaAroundUsDataCiteConstants.STOCK_STATUS_LABEL, regionParams.getRegionType().getDisplayName(), regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().getUrlName());

        ResearchData stockStatus = new ResearchData(regionParams.getUrls().getStockStatusDownloadUrl(apiUrl, regionId),
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
    public static ResearchData createMarineTrophicIndexFile(RegionParametersVO regionParams, int regionId, String regionName)
    {
        String marineTrophicIndexLabel = String.format(SeaAroundUsDataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().getUrlName());

        ResearchData marineTrophicIndex = new ResearchData(
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
    public static List<ResearchData> createCatchResearchData(RegionParametersVO regionParams, int regionId, String regionName)
    {
        List<ResearchData> files = new LinkedList<>();

        EntryVO regionType = regionParams.getRegionType();
        List<EntryVO> dimensionList = regionParams.getDimensions();
        List<EntryVO> measureList = regionParams.getMeasures();
        UrlVO urls = regionParams.getUrls();
        String apiUrl = getAllRegionsUrl(regionType.getUrlName());

        for (EntryVO measure : measureList) {
            for (EntryVO dimension : dimensionList) {

                String catchesLabel = String.format(
                                          SeaAroundUsDataCiteConstants.CATCHES_LABEL,
                                          measure.getDisplayName(),
                                          dimension.getDisplayName(),
                                          regionType.getDisplayName(),
                                          regionName);
                ResearchData cbdFile = new ResearchData(
                    urls.getCatchesDownloadUrl(apiUrl, regionId, dimension.getUrlName(), measure.getUrlName()) + SeaAroundUsUrlConstants.CSV_FORM,
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
    public static List<WebLink> createCatchLinks(RegionParametersVO regionParams, int regionId, String regionName)
    {
        List<WebLink> links = new LinkedList<>();

        EntryVO regionType = regionParams.getRegionType();
        List<EntryVO> dimensionList = regionParams.getDimensions();
        List<EntryVO> measureList = regionParams.getMeasures();
        UrlVO urls = regionParams.getUrls();
        String viewUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL_PREFIX, regionType.getUrlName());

        for (EntryVO measure : measureList) {
            for (EntryVO dimension : dimensionList) {

                String catchesLabel = String.format(
                                          SeaAroundUsDataCiteConstants.CATCHES_LABEL,
                                          measure.getDisplayName(),
                                          dimension.getDisplayName(),
                                          regionType.getDisplayName(),
                                          regionName);
                WebLink cbdLink = new WebLink(urls.getCatchesViewUrl(viewUrl, regionId, dimension.getUrlName(), measure.getUrlName()));
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
    public static List<WebLink> createBasicWebLinks(String regionApiName, int regionId)
    {
        List<WebLink> webLinks = new LinkedList<>();

        // add logo
        webLinks.add(SeaAroundUsDataCiteConstants.LOGO_LINK);

        // add source
        String apiUrl = getRegionEntryUrl(regionApiName, regionId);
        webLinks.add(createSourceLink(apiUrl));

        // add view
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
    public static List<GeoLocation> createBasicGeoLocations(GeoJson regionBorders, String regionName)
    {
        List<GeoLocation> geoLocations = new LinkedList<>();

        if (regionBorders != null) {
            List<GeoJson> polygons = new LinkedList<>();
            polygons.add(regionBorders);

            GeoLocation g = new GeoLocation();
            g.setPlace(regionName);
            g.setPolygons(polygons);
            geoLocations.add(g);
        }

        return geoLocations;
    }
}
