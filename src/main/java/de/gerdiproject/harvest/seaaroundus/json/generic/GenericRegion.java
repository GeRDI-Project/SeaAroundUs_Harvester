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

import de.gerdiproject.json.geo.GeoJson;

/**
 * This class represents a generic SeaAroundUs region response.
 *
 * @author Robin Weiss
 */
public class GenericRegion
{
    private String title;
    private int id;
    private GeoJson geojson;
    private List<Metric> metrics;


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public int getId()
    {
        return id;
    }


    public void setId(int id)
    {
        this.id = id;
    }


    public GeoJson getGeojson()
    {
        return geojson;
    }


    public void setGeojson(GeoJson geojson)
    {
        this.geojson = geojson;
    }


    public List<Metric> getMetrics()
    {
        return metrics;
    }


    public void setMetrics(List<Metric> metrics)
    {
        this.metrics = metrics;
    }
}
