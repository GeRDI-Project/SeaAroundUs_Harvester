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

import java.util.List;

/**
 * This class represents a generic SeaAroundUs JSON response.
 * @author Robin Weiss
 *
 * @param <T> the type of data, carried by the response
 */
public class FeatureCollection <T extends FeatureProperties>
{
    private List<Feature<T>> features;
    private String type;


    public List<Feature<T>> getFeatures()
    {
        return features;
    }


    public void setFeatures(List<Feature<T>> value)
    {
        this.features = value;
    }


    public String getType()
    {
        return type;
    }


    public void setType(String value)
    {
        this.type = value;
    }
}
