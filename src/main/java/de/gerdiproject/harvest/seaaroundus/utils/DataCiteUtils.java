package de.gerdiproject.harvest.seaaroundus.utils;

import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.json.datacite.Source;

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

    /**
     * Private constructor, because this is a static class
     */
    private DataCiteUtils()
    {

    }

    public static DataCiteUtils instance()
    {
        return instance;
    }


    /**
     * Assembles a URL to download an overview of specified regions.
     * @param regionName the SeaAroundUs API name of the region
     *
     * @return a URL to download all regions
     */
    public String getAllRegionsUrl(String regionName)
    {
        return String.format(allEntriesApiUrl, regionName);
    }

    /**
     * Assembles a URL to download a single region.
     * @param regionName the SeaAroundUs API name of the region
     * @param regionId a SeaAroundUs unique identifier of a region
     *
     * @return a URL to download a single regions
     */
    public String getRegionEntryUrl(String regionName, int regionId)
    {
        return String.format(singleEntryApiUrl, regionName, regionId);
    }


    public void setVersion(String version)
    {
        singleEntryApiUrl = String.format(UrlConstants.API_URL, version) + UrlConstants.REGION_URL_SUFFIX;
        allEntriesApiUrl = String.format(UrlConstants.API_URL, version) + UrlConstants.REGION_IDS_URL;
    }


}
