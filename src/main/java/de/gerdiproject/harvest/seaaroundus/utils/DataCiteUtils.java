package de.gerdiproject.harvest.seaaroundus.utils;

import de.gerdiproject.harvest.seaaroundus.constants.Entry;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;

/**
 * A static helper class for creating DataCite fields.
 *
 * @author Robin Weiss
 */
public final class DataCiteUtils
{
    private static DataCiteUtils instance = new DataCiteUtils();

    private String singleEntryApiUrl = null;
    private String allEntriesApiUrl = null;
    private String catchesApiUrl = null;


    /**
     * Private constructor, because this is a static class
     */
    private DataCiteUtils()
    {

    }


    /**
     * Retrieves the singleton instance of this class.
     *
     * @return the singleton instance of this class
     */
    public static DataCiteUtils instance()
    {
        return instance;
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
}
