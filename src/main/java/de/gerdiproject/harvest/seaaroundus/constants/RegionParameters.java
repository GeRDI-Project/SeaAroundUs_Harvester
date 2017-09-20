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
package de.gerdiproject.harvest.seaaroundus.constants;

import java.util.List;

import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.GenericRegionHarvester;


/**
 * This class provides parameters that are required to set up a {@linkplain GenericRegionHarvester}.
 *
 * @author Robin Weiss
 */
public class RegionParameters
{
    private final Entry regionType;
    private final List<Entry> dimensions;
    private final UrlVO urls;


    public RegionParameters(Entry regionType, List<Entry> dimensions, UrlVO urls)
    {
        super();
        this.regionType = regionType;
        this.dimensions = dimensions;
        this.urls = urls;
    }


    public Entry getRegionType()
    {
        return regionType;
    }


    public List<Entry> getDimensions()
    {
        return dimensions;
    }


    public UrlVO getUrls()
    {
        return urls;
    }
}
