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
package de.gerdiproject.harvest.seaaroundus.json.catches;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus catches request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxa/tonnage/eez/?limit=10&sciname=&region_id=400631
 *
 * @author Robin Weiss
 */
public final class SauCatch
{
    private String key;
    private List<List<Double>> values;

    @SerializedName("entity_id")
    private int entityId;


    /**
     * Returns the year of the first value entry.
     *
     * @return the year of the earliest catch
     */
    public int getEarliestYear()
    {
        return values.get(0).get(0).intValue();
    }


    /**
     * Returns the year of the last value entry.
     *
     * @return the year of the latest catch
     */
    public int getLatestYear()
    {
        return values.get(values.size()).get(0).intValue();
    }


    public int getEntityId()
    {
        return entityId;
    }


    public void setEntityId(int value)
    {
        this.entityId = value;
    }


    public String getKey()
    {
        return key;
    }


    public void setKey(String value)
    {
        this.key = value;
    }


    public List<List<Double>> getValues()
    {
        return values;
    }


    public void setValues(List<List<Double>> value)
    {
        this.values = value;
    }
}