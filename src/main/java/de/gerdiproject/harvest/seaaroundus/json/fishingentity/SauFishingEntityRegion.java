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
package de.gerdiproject.harvest.seaaroundus.json.fishingentity;

import com.google.gson.annotations.SerializedName;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;

/**
 * This class represents a JSON object response to a Seaaroundus fishing-entity request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/fishing-entity/1
 *
 * @author Robin Weiss
 */
public class SauFishingEntityRegion extends GenericRegion
{
    @SerializedName("country_id")
    int countryId;

    @SerializedName("geo_entity_id")
    int geoEntityId;


    public int getCountryId()
    {
        return countryId;
    }


    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }


    public int getGeoEntityId()
    {
        return geoEntityId;
    }


    public void setGeoEntityId(int geoEntityId)
    {
        this.geoEntityId = geoEntityId;
    }
}