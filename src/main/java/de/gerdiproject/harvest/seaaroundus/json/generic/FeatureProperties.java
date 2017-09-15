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
package de.gerdiproject.harvest.seaaroundus.json.generic;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents a JSON object of feature propeties that is part of every Seaaroundus {@linkplain Feature}.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/country/
 *
 * @author Robin Weiss
 */
public class FeatureProperties
{
    private String title;
    private String region;

    @SerializedName("region_id")
    private int regionId;

    @SerializedName("long_title")
    private String longTitle;


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String value)
    {
        this.title = value;
    }


    public String getRegion()
    {
        return region;
    }


    public void setRegion(String value)
    {
        this.region = value;
    }


    public int getRegionId()
    {
        return regionId;
    }


    public void setRegionId(int value)
    {
        this.regionId = value;
    }


    public String getLongTitle()
    {
        return longTitle;
    }


    public void setLongTitle(String value)
    {
        this.longTitle = value;
    }
}
