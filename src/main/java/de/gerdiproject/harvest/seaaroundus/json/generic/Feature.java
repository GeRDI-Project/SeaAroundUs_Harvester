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
package de.gerdiproject.harvest.seaaroundus.json.generic;

import de.gerdiproject.json.geo.GeoJson;

/**
 * This class represents a SeaAroundUs JSON response of a feature collection.
 * @author Robin Weiss
 *
 * @param <T> the type of {@linkplain FeatureProperties}, carried by the feature
 */
public class Feature<T extends FeatureProperties>
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
