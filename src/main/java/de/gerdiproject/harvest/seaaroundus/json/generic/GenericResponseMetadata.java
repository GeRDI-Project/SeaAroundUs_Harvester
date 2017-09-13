package de.gerdiproject.harvest.seaaroundus.json.generic;

/**
 * This JSON object represents metadata that is returned as part of every SeaAroundUs API request.
 * <br> e.g. the "meta" object of http://api.seaaroundus.org/api/v1/country/12
 *
 * @author Robin Weiss
 */
public class GenericResponseMetadata
{
    private String title;
    private String date;
    private String version;


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getDate()
    {
        return date;
    }


    public void setDate(String date)
    {
        this.date = date;
    }


    public String getVersion()
    {
        return version;
    }


    public void setVersion(String version)
    {
        this.version = version;
    }
}
