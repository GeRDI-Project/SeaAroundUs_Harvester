package de.gerdiproject.harvest.seaaroundus.utils;

import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.Entry;
import de.gerdiproject.harvest.seaaroundus.constants.RegionParameters;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlVO;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.Source;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

/**
 * A static helper class for creating DataCite fields.
 *
 * @author Robin Weiss
 */
public final class DataCiteFactory
{
    private static DataCiteFactory instance = new DataCiteFactory();

    private String singleEntryApiUrl = null;
    private String allEntriesApiUrl = null;
    private String catchesApiUrl = null;


    /**
     * Private constructor, because this is a static class
     */
    private DataCiteFactory()
    {

    }


    /**
     * Retrieves the singleton instance of this class.
     *
     * @return the singleton instance of this class
     */
    public static DataCiteFactory instance()
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
        String apiPrefix = String.format(UrlConstants.API_URL, version);
        singleEntryApiUrl = apiPrefix + UrlConstants.REGION_URL_SUFFIX;
        allEntriesApiUrl = apiPrefix + UrlConstants.REGION_IDS_URL;
        catchesApiUrl = apiPrefix + UrlConstants.CATCHES_URL;
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
            DataCiteConstants.PROVIDER);
        source.setProviderURI(DataCiteConstants.PROVIDER_URI);
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
    public String getCatchesUrl(String regionName, int regionId, Entry measure, Entry dimension)
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
    public WebLink createPrimaryProductionLink(RegionParameters regionParams, int regionId, String regionName)
    {
        Entry regionType = regionParams.getRegionType();
        String primaryProductionLabel = String.format(DataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionType.displayName, regionName);
        String viewUrl = getRegionEntryUrl(regionType.urlName, regionId);

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
    public WebLink createStockStatusLink(RegionParameters regionParams, int regionId, String regionName)
    {
        Entry regionType = regionParams.getRegionType();
        String stockStatusLabel = String.format(DataCiteConstants.STOCK_STATUS_LABEL, regionType.displayName, regionName);
        String viewUrl = getRegionEntryUrl(regionType.urlName, regionId);

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
    public WebLink createMarineTrophicIndexLink(RegionParameters regionParams, int regionId, String regionName)
    {
        String marineTrophicIndexLabel = String.format(DataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);
        String viewUrl = getRegionEntryUrl(regionParams.getRegionType().urlName, regionId);

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
    public File createPrimaryProductionFile(RegionParameters regionParams, int regionId, String regionName)
    {
        String primaryProductionLabel = String.format(DataCiteConstants.PRIMARY_PRODUCTION_LABEL, regionParams.getRegionType().displayName, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().urlName);

        File primaryProduction = new File(regionParams.getUrls().getPrimaryProductionDownloadUrl(apiUrl, regionId),
                                          primaryProductionLabel);
        primaryProduction.setType(DataCiteConstants.CSV_FORMAT);

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
    public File createStockStatusFile(RegionParameters regionParams, int regionId, String regionName)
    {
        String stockStatusLabel = String.format(DataCiteConstants.STOCK_STATUS_LABEL, regionParams.getRegionType().displayName, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().urlName);

        File stockStatus = new File(regionParams.getUrls().getStockStatusDownloadUrl(apiUrl, regionId),
                                    stockStatusLabel);
        stockStatus.setType(DataCiteConstants.CSV_FORMAT);

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
    public File createMarineTrophicIndexFile(RegionParameters regionParams, int regionId, String regionName)
    {
        String marineTrophicIndexLabel = String.format(DataCiteConstants.MARINE_TROPHIC_INDEX_LABEL, regionName);
        String apiUrl = getAllRegionsUrl(regionParams.getRegionType().urlName);

        File marineTrophicIndex = new File(
            regionParams.getUrls().getMarineTrophicIndexDownloadUrl(apiUrl, regionId),
            marineTrophicIndexLabel);
        marineTrophicIndex.setType(DataCiteConstants.CSV_FORMAT);

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
    public List<File> createCatchFiles(RegionParameters regionParams, int regionId, String regionName)
    {
        List<File> files = new LinkedList<>();

        Entry regionType = regionParams.getRegionType();
        List<Entry> dimensionList = regionParams.getDimensions();
        List<Entry> measureList = regionParams.getMeasures();
        UrlVO urls = regionParams.getUrls();
        String apiUrl = getRegionEntryUrl(regionType.urlName, regionId);

        for (Entry measure : measureList) {
            for (Entry dimension : dimensionList) {

                String catchesLabel = String.format(
                                          DataCiteConstants.CATCHES_LABEL,
                                          measure.displayName,
                                          dimension.displayName,
                                          regionType.displayName,
                                          regionName);
                File cbdFile = new File(
                    urls.getCatchesDownloadUrl(apiUrl, regionId, dimension.urlName, measure.urlName),
                    catchesLabel);
                cbdFile.setType(DataCiteConstants.CSV_FORMAT);
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
    public List<WebLink> createCatchLinks(RegionParameters regionParams, int regionId, String regionName)
    {
        List<WebLink> links = new LinkedList<>();

        Entry regionType = regionParams.getRegionType();
        List<Entry> dimensionList = regionParams.getDimensions();
        List<Entry> measureList = regionParams.getMeasures();
        UrlVO urls = regionParams.getUrls();
        String viewUrl = String.format(UrlConstants.VIEW_URL, regionType.urlName, regionId);

        for (Entry measure : measureList) {
            for (Entry dimension : dimensionList) {

                String catchesLabel = String.format(
                                          DataCiteConstants.CATCHES_LABEL,
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
}
