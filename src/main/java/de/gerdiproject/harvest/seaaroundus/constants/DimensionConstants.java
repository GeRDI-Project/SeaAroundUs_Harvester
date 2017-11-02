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
 *  This static class contains collections of dimensions. Dimensions are filter
 *  criteria applied to SeaAroundUs datasets.
 *
 *  @author Robin Weiss
 */
public class DimensionConstants
{
    private final static Entry DIMENSION_TAXON = new Entry("taxon", "Taxon");
    private final static Entry DIMENSION_COMMERCIAL_GROUP = new Entry("commercialgroup", "Commercial Groups");
    private final static Entry DIMENSION_FUNCTIONAL_GROUP = new Entry("functionalgroup", "Functional Groups");
    private final static Entry DIMENSION_COUNTRY = new Entry("country", "Fishing Country");
    private final static Entry DIMENSION_SECTOR = new Entry("sector", "Fishing Sector");
    private final static Entry DIMENSION_CATCH_TYPE = new Entry("catchtype", "Type");
    private final static Entry DIMENSION_REPORTING_STATUS = new Entry("reporting-status", "Reporting Status");
    private final static Entry DIMENSION_EEZ = new Entry("eez", "EEZ");
    private final static Entry DIMENSION_LME = new Entry("lme", "LME");
    private final static Entry DIMENSION_HIGH_SEAS = new Entry("highseas", "High Seas");
    private final static Entry DIMENSION_LAYER = new Entry("layer", "Data Layer");


    public static final List<Entry> DIMENSIONS_GENERIC =
        Collections.unmodifiableList(Arrays.asList(
                                         DimensionConstants.DIMENSION_TAXON,
                                         DimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         DimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         DimensionConstants.DIMENSION_COUNTRY,
                                         DimensionConstants.DIMENSION_SECTOR,
                                         DimensionConstants.DIMENSION_CATCH_TYPE,
                                         DimensionConstants.DIMENSION_REPORTING_STATUS));


    public static final List<Entry> DIMENSIONS_MARICULTURE =
        Collections.unmodifiableList(Arrays.asList(
                                         DimensionConstants.DIMENSION_TAXON,
                                         DimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         DimensionConstants.DIMENSION_FUNCTIONAL_GROUP));


    public static final List<Entry> DIMENSIONS_FAO =
        Collections.unmodifiableList(Arrays.asList(
                                         DimensionConstants.DIMENSION_TAXON,
                                         DimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         DimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         DimensionConstants.DIMENSION_COUNTRY,
                                         DimensionConstants.DIMENSION_SECTOR,
                                         DimensionConstants.DIMENSION_CATCH_TYPE,
                                         DimensionConstants.DIMENSION_REPORTING_STATUS,
                                         DimensionConstants.DIMENSION_EEZ,
                                         DimensionConstants.DIMENSION_HIGH_SEAS));


    public static final List<Entry> DIMENSIONS_EEZ =
        Collections.unmodifiableList(Arrays.asList(
                                         DimensionConstants.DIMENSION_TAXON,
                                         DimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         DimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         DimensionConstants.DIMENSION_COUNTRY,
                                         DimensionConstants.DIMENSION_SECTOR,
                                         DimensionConstants.DIMENSION_CATCH_TYPE,
                                         DimensionConstants.DIMENSION_REPORTING_STATUS,
                                         DimensionConstants.DIMENSION_LAYER));


    public static final List<Entry> DIMENSIONS_TAXON =
        Collections.unmodifiableList(Arrays.asList(
                                         DimensionConstants.DIMENSION_EEZ,
                                         DimensionConstants.DIMENSION_LME,
                                         DimensionConstants.DIMENSION_HIGH_SEAS,
                                         DimensionConstants.DIMENSION_COUNTRY,
                                         DimensionConstants.DIMENSION_COMMERCIAL_GROUP,
                                         DimensionConstants.DIMENSION_FUNCTIONAL_GROUP,
                                         DimensionConstants.DIMENSION_SECTOR,
                                         DimensionConstants.DIMENSION_CATCH_TYPE,
                                         DimensionConstants.DIMENSION_REPORTING_STATUS));

    /**
     * Private constructor, because this is a static class.
     */
    private DimensionConstants()
    {
    }
}
