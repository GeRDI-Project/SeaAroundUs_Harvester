package de.gerdiproject.harvest.seaaroundus.json.generic;

import de.gerdiproject.json.geo.GeoJson;

/**
 * This class represents a SeaAroundUs JSON response of a feature collection.
 * @author Robin Weiss
 *
 * @param <T> the type of properties, carried by the feature
 */
public class Feature<T>
{
    private GeoJson geometry;
    private String type;
    private T properties;


    public GeoJson getGeometry()
    {
        return geometry;
    }


    public void setGeometry(GeoJson value)
    {
        this.geometry = value;
    }


    public String getType()
    {
        return type;
    }


    public void setType(String value)
    {
        this.type = value;
    }


    public T getProperties()
    {
        return properties;
    }


    public void setProperties(T value)
    {
        this.properties = value;
    }
}
