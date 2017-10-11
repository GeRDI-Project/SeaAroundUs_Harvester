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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  This static class contains descriptions of regions. Regions are filter
 *  criteria applied to SeaAroundUs datasets.
 *
 *  @author Robin Weiss
 */
public class RegionConstants
{
    public static final String COUNTRY_API_NAME = "country";
    public static final String MARICULTURE_API_NAME = "mariculture";

    public static final Entry REGION_EEZ = new Entry("eez", "in the Waters of");
    public static final Entry REGION_LME = new Entry("lme", "in the Waters of");
    public static final Entry REGION_RFMO = new Entry("rfmo", "in the");
    public static final Entry REGION_FISHING_ENTITY = new Entry("fishing-entity", "by the Fleets of");
    public static final Entry REGION_FAO = new Entry("fao", "in the Waters of FAO Area");
    public static final Entry REGION_HIGH_SEAS = new Entry("highseas", "in the Non-EEZ Waters of the");
    public static final Entry REGION_GLOBAL = new Entry("global", "in the Global Ocean");

    public static final Entry SUB_REGION_GLOBAL = new Entry("0", "");
    public static final Entry SUB_REGION_EEZS = new Entry("1", "- EEZs of the world");
    public static final Entry SUB_REGION_HIGH_SEAS = new Entry("2", "- High Seas of the world");

    private static final Entry MEASURE_VALUE = new Entry("value", "Real 2010 value (US$)");
    private static final Entry MEASURE_TONNAGE = new Entry("tonnage", "Catches");
    private static final List<Entry> GENERIC_MEASURES = Collections.unmodifiableList(Arrays.asList(MEASURE_VALUE, MEASURE_TONNAGE));

    // TAXON
    public static final String TAXA_API_NAME = "taxa";
    public static final String TAXON_GROUP_API_NAME = "taxon-group";
    public static final String TAXON_LEVEL_API_NAME = "taxon-level";
    public static final Entry TAXON_MEASURE_VALUE = new Entry("value", "Real 2010 value (US$) of global catches");
    public static final Entry TAXON_MEASURE_TONNAGE = new Entry("tonnage", "Global catches");
    public static final List<Entry> TAXON_MEASURES = Collections.unmodifiableList(Arrays.asList(TAXON_MEASURE_VALUE, TAXON_MEASURE_TONNAGE));


    public static final RegionParameters EEZ_PARAMS = new RegionParameters(
        REGION_EEZ,
        DimensionConstants.DIMENSIONS_EEZ,
        GENERIC_MEASURES,
        UrlConstants.GENERIC_URL_VO
    );

    public static final RegionParameters LME_PARAMS = new RegionParameters(
        REGION_LME,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        UrlConstants.GENERIC_URL_VO
    );

    public static final RegionParameters RFMO_PARAMS = new RegionParameters(
        REGION_RFMO,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        UrlConstants.GENERIC_URL_VO
    );

    public static final RegionParameters FAO_PARAMS = new RegionParameters(
        REGION_FAO,
        DimensionConstants.DIMENSIONS_FAO,
        GENERIC_MEASURES,
        UrlConstants.GENERIC_URL_VO
    );

    public static final RegionParameters HIGH_SEAS_PARAMS = new RegionParameters(
        REGION_HIGH_SEAS,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        UrlConstants.GENERIC_URL_VO
    );

    public static final RegionParameters FISHING_ENTITY_PARAMS = new RegionParameters(
        REGION_FISHING_ENTITY,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        UrlConstants.GENERIC_URL_VO
    );

    public static final RegionParameters GLOBAL_OCEAN_PARAMS = new RegionParameters(
        SUB_REGION_GLOBAL,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        UrlConstants.GLOBAL_OCEAN_URL_VO
    );

    public static final RegionParameters GLOBAL_OCEAN_HIGH_SEAS_PARAMS = new RegionParameters(
        SUB_REGION_HIGH_SEAS,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        UrlConstants.GLOBAL_OCEAN_URL_VO
    );

    public static final RegionParameters GLOBAL_OCEAN_EEZ_PARAMS = new RegionParameters(
        SUB_REGION_EEZS,
        DimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
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
