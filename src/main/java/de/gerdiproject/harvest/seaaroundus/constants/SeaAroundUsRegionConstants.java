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

import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.harvest.seaaroundus.vos.SubRegionVO;

/**
 *  This static class contains descriptions of regions. Regions are filter
 *  criteria applied to SeaAroundUs datasets.
 *
 *  @author Robin Weiss
 */
public class SeaAroundUsRegionConstants
{
    public static final String COUNTRY_API_NAME = "country";
    public static final String MARICULTURE_API_NAME = "mariculture";

    public static final EntryVO REGION_EEZ = new EntryVO("eez", "in the Waters of");
    public static final EntryVO REGION_LME = new EntryVO("lme", "in the Waters of");
    public static final EntryVO REGION_RFMO = new EntryVO("rfmo", "in the");
    public static final EntryVO REGION_FISHING_ENTITY = new EntryVO("fishing-entity", "by the Fleets of");
    public static final EntryVO REGION_FAO = new EntryVO("fao", "in the Waters of FAO Area");
    public static final EntryVO REGION_HIGH_SEAS = new EntryVO("highseas", "in the Non-EEZ Waters of the");
    public static final EntryVO REGION_GLOBAL = new EntryVO("global", "in the Global Ocean");

    private static final EntryVO MEASURE_VALUE = new EntryVO("value", "Real 2010 value (US$)");
    private static final EntryVO MEASURE_TONNAGE = new EntryVO("tonnage", "Catches");
    private static final List<EntryVO> GENERIC_MEASURES = Collections.unmodifiableList(Arrays.asList(MEASURE_VALUE, MEASURE_TONNAGE));

    // TAXON
    public static final String TAXA_API_NAME = "taxa";
    public static final String TAXON_GROUP_API_NAME = "taxon-group";
    public static final String TAXON_LEVEL_API_NAME = "taxon-level";
    public static final EntryVO TAXON_MEASURE_VALUE = new EntryVO("value", "Real 2010 value (US$) of global catches");
    public static final EntryVO TAXON_MEASURE_TONNAGE = new EntryVO("tonnage", "Global catches");
    public static final List<EntryVO> TAXON_MEASURES = Collections.unmodifiableList(Arrays.asList(TAXON_MEASURE_VALUE, TAXON_MEASURE_TONNAGE));


    public static final RegionParametersVO EEZ_PARAMS = new RegionParametersVO(
        REGION_EEZ,
        SeaAroundUsDimensionConstants.DIMENSIONS_EEZ,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO LME_PARAMS = new RegionParametersVO(
        REGION_LME,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO RFMO_PARAMS = new RegionParametersVO(
        REGION_RFMO,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO FAO_PARAMS = new RegionParametersVO(
        REGION_FAO,
        SeaAroundUsDimensionConstants.DIMENSIONS_FAO,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO HIGH_SEAS_PARAMS = new RegionParametersVO(
        REGION_HIGH_SEAS,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO FISHING_ENTITY_PARAMS = new RegionParametersVO(
        REGION_FISHING_ENTITY,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO GLOBAL_PARAMS = new RegionParametersVO(
        REGION_GLOBAL,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO GLOBAL_SUBREGION_PARAMS = new RegionParametersVO(
        REGION_GLOBAL,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GLOBAL_OCEAN_URL_VO
    );


    public static final SubRegionVO SUB_REGION_GLOBAL = new SubRegionVO(0, "");
    public static final SubRegionVO SUB_REGION_EEZS = new SubRegionVO(1, "- EEZs of the world");
    public static final SubRegionVO SUB_REGION_HIGH_SEAS = new SubRegionVO(2, "- High Seas of the world");

    //Entry regionType, List<Entry> dimensions, UrlVO urls,
    //Class<? extends GenericRegion> regionClass

    /**
     * Private constructor, because this is a static class.
     */
    private SeaAroundUsRegionConstants()
    {
    }
}
