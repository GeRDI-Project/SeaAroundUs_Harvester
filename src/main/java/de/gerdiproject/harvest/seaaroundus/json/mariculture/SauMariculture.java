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
package de.gerdiproject.harvest.seaaroundus.json.mariculture;

import com.google.gson.annotations.SerializedName;

import de.gerdiproject.json.geo.GeoJson;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus mariculture request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/mariculture/57
 *
 * @author Robin Weiss
 */
public class SauMariculture
{
    private GeoJson geojson;
    private String title;

    @SerializedName("country_name")
    private String countryName;

    @SerializedName("point_geojson")
    private GeoJson pointGeojson;

    @SerializedName("entity_id")
    private int entityId;

    @SerializedName("region_id")
    private int regionId;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("total_production")
    private double totalProduction;


    public GeoJson getGeojson()
    {
        return geojson;
    }


    public void setGeojson(GeoJson value)
    {
        this.geojson = value;
    }


    public String getCountryName()
    {
        return countryName;
    }


    public void setCountryName(String value)
    {
        this.countryName = value;
    }


    public GeoJson getPointGeojson()
    {
        return pointGeojson;
    }


    public void setPointGeojson(GeoJson value)
    {
        this.pointGeojson = value;
    }


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String value)
    {
        this.title = value;
    }


    public int getEntityId()
    {
        return entityId;
    }


    public void setEntityId(int value)
    {
        this.entityId = value;
    }


    public int getRegionId()
    {
        return regionId;
    }


    public void setRegionId(int value)
    {
        this.regionId = value;
    }


    public int getCountryId()
    {
        return countryId;
    }


    public void setCountryId(int value)
    {
        this.countryId = value;
    }


    public double getTotalProduction()
    {
        return totalProduction;
    }


    public void setTotalProduction(double value)
    {
        this.totalProduction = value;
    }
}
