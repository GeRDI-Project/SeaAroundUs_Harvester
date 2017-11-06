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

/**
 *  This static class contains collections of dimensions. Dimensions are filter
 *  criteria applied to SeaAroundUs datasets.
 *
 *  @author Robin Weiss
 */
public class SeaAroundUsDimensionConstants
{
    private final static EntryVO DIMENSION_TAXON = new EntryVO("taxon", "Taxon");
    private final static EntryVO DIMENSION_COMMERCIAL_GROUP = new EntryVO("commercialgroup", "Commercial Groups");
    private final static EntryVO DIMENSION_FUNCTIONAL_GROUP = new EntryVO("functionalgroup", "Functional Groups");
    private final static EntryVO DIMENSION_COUNTRY = new EntryVO("country", "Fishing Country");
    private final static EntryVO DIMENSION_SECTOR = new EntryVO("sector", "Fishing Sector");
    private final static EntryVO DIMENSION_CATCH_TYPE = new EntryVO("catchtype", "Type");
    private final static EntryVO DIMENSION_REPORTING_STATUS = new EntryVO("reporting-status", "Reporting Status");
    private final static EntryVO DIMENSION_EEZ = new EntryVO("eez", "EEZ");
    private final static EntryVO DIMENSION_LME = new EntryVO("lme", "LME");
    private final static EntryVO DIMENSION_HIGH_SEAS = new EntryVO("highseas", "High Seas");
    private final static EntryVO DIMENSION_LAYER = new EntryVO("layer", "Data Layer");


    public static final List<EntryVO> DIMENSIONS_GENERIC =
        Collections.unmodifiableList(Arrays.asList(
                                         SeaAroundUsDimensionConstants.DIMENSION_TAXON,
                                         SeaAroundUsDimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_COUNTRY,
                                         SeaAroundUsDimensionConstants.DIMENSION_SECTOR,
                                         SeaAroundUsDimensionConstants.DIMENSION_CATCH_TYPE,
                                         SeaAroundUsDimensionConstants.DIMENSION_REPORTING_STATUS));


    public static final List<EntryVO> DIMENSIONS_MARICULTURE =
        Collections.unmodifiableList(Arrays.asList(
                                         SeaAroundUsDimensionConstants.DIMENSION_TAXON,
                                         SeaAroundUsDimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_FUNCTIONAL_GROUP));


    public static final List<EntryVO> DIMENSIONS_FAO =
        Collections.unmodifiableList(Arrays.asList(
                                         SeaAroundUsDimensionConstants.DIMENSION_TAXON,
                                         SeaAroundUsDimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_COUNTRY,
                                         SeaAroundUsDimensionConstants.DIMENSION_SECTOR,
                                         SeaAroundUsDimensionConstants.DIMENSION_CATCH_TYPE,
                                         SeaAroundUsDimensionConstants.DIMENSION_REPORTING_STATUS,
                                         SeaAroundUsDimensionConstants.DIMENSION_EEZ,
                                         SeaAroundUsDimensionConstants.DIMENSION_HIGH_SEAS));


    public static final List<EntryVO> DIMENSIONS_EEZ =
        Collections.unmodifiableList(Arrays.asList(
                                         SeaAroundUsDimensionConstants.DIMENSION_TAXON,
                                         SeaAroundUsDimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_COUNTRY,
                                         SeaAroundUsDimensionConstants.DIMENSION_SECTOR,
                                         SeaAroundUsDimensionConstants.DIMENSION_CATCH_TYPE,
                                         SeaAroundUsDimensionConstants.DIMENSION_REPORTING_STATUS,
                                         SeaAroundUsDimensionConstants.DIMENSION_LAYER));


    public static final List<EntryVO> DIMENSIONS_TAXON =
        Collections.unmodifiableList(Arrays.asList(
                                         SeaAroundUsDimensionConstants.DIMENSION_EEZ,
                                         SeaAroundUsDimensionConstants.DIMENSION_LME,
                                         SeaAroundUsDimensionConstants.DIMENSION_HIGH_SEAS,
                                         SeaAroundUsDimensionConstants.DIMENSION_COUNTRY,
                                         SeaAroundUsDimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         SeaAroundUsDimensionConstants.DIMENSION_SECTOR,
                                         SeaAroundUsDimensionConstants.DIMENSION_CATCH_TYPE,
                                         SeaAroundUsDimensionConstants.DIMENSION_REPORTING_STATUS));

    /**
     * Private constructor, because this is a static class.
     */
    private SeaAroundUsDimensionConstants()
    {
    }
}
