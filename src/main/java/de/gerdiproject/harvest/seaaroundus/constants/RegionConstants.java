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



/**
 *  This static class contains descriptions of regions. Regions are filter
 *  criteria applied to SeaAroundUs datasets.
 *
 *  @author Robin Weiss
 */
public class RegionConstants
{
    public final static Entry REGION_EEZ = new Entry("eez", "in the Waters of");
    public final static Entry REGION_LME = new Entry("lme", "in the Waters of");
    public final static Entry REGION_RFMO = new Entry("rfmo", "in the");
    public final static Entry REGION_FISHING_ENTITY = new Entry("fishing-entity", "by the Fleets of");
    public final static Entry REGION_FAO = new Entry("fao", "in the Waters of FAO Area");
    public final static Entry REGION_HIGH_SEAS = new Entry("highseas", "in the Non-EEZ Waters of the");
    public final static Entry REGION_GLOBAL = new Entry("global", "in the Global Ocean");

    public final static Entry SUB_REGION_GLOBAL = new Entry("0", "");
    public final static Entry SUB_REGION_EEZS = new Entry("1", "- EEZs of the world");
    public final static Entry SUB_REGION_HIGH_SEAS = new Entry("2", "- High Seas of the world");

    public final static RegionParameters EEZ_PARAMS = new RegionParameters(
        REGION_EEZ,
        DimensionConstants.DIMENSIONS_EEZ,
        UrlConstants.GENERIC_URL_VO
    );

    public final static RegionParameters LME_PARAMS = new RegionParameters(
        REGION_LME,
        DimensionConstants.DIMENSIONS_GENERIC,
        UrlConstants.GENERIC_URL_VO
    );

    public final static RegionParameters RFMO_PARAMS = new RegionParameters(
        REGION_RFMO,
        DimensionConstants.DIMENSIONS_GENERIC,
        UrlConstants.GENERIC_URL_VO
    );

    public final static RegionParameters FAO_PARAMS = new RegionParameters(
        REGION_FAO,
        DimensionConstants.DIMENSIONS_FAO,
        UrlConstants.GENERIC_URL_VO
    );

    public final static RegionParameters HIGH_SEAS_PARAMS = new RegionParameters(
        REGION_HIGH_SEAS,
        DimensionConstants.DIMENSIONS_GENERIC,
        UrlConstants.GENERIC_URL_VO
    );

    public final static RegionParameters FISHING_ENTITY_PARAMS = new RegionParameters(
        REGION_FISHING_ENTITY,
        DimensionConstants.DIMENSIONS_GENERIC,
        UrlConstants.GENERIC_URL_VO
    );

    public final static RegionParameters GLOBAL_OCEAN_PARAMS = new RegionParameters(
        REGION_GLOBAL,
        DimensionConstants.DIMENSIONS_GENERIC,
        UrlConstants.GLOBAL_OCEAN_URL_VO
    );

    //Entry regionType, List<Entry> dimensions, UrlVO urls,
    //Class<? extends GenericRegion> regionClass

    /**
     * Private constructor, because this is a static class.
     */
    private RegionConstants()
    {
    }
}
