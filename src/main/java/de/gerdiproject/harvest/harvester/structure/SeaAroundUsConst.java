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
package de.gerdiproject.harvest.harvester.structure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author row
 */
public class SeaAroundUsConst
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

    // PUBLIC
    public final static String PROPERTY_URL = "url";

    public final static String TERMS_OF_USAGE = "While these data are freely available for use, we ask that you please acknowledge Sea Around Us in your work.\n\n"
                                                + "By downloading this data, you agree to provide attribution for any Sea Around Us data you use.";

    public final static String VIEW_URL_PREFIX = "http://www.seaaroundus.org/data/#/%s/";
    public final static String REGION_IDS_URL = "/%s/";
    public final static String LOGO_URL = "http://www.seaaroundus.org/data/images/logo_saup.png";
    public final static String CSV_FORM = "&format=csv";

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

    public static final List<Entry> DIMENSIONS_GENERIC;
    static
    {
        final List<Entry> genericDims = new LinkedList<>();
        genericDims.add(SeaAroundUsConst.DIMENSION_TAXON);
        genericDims.add(SeaAroundUsConst.DIMENSION_COMMERCIAL_GROUP);
        genericDims.add(SeaAroundUsConst.DIMENSION_FUNCTIONAL_GROUP);
        genericDims.add(SeaAroundUsConst.DIMENSION_COUNTRY);
        genericDims.add(SeaAroundUsConst.DIMENSION_SECTOR);
        genericDims.add(SeaAroundUsConst.DIMENSION_CATCH_TYPE);
        genericDims.add(SeaAroundUsConst.DIMENSION_REPORTING_STATUS);

        // guarantee immutability
        DIMENSIONS_GENERIC = Collections.unmodifiableList(genericDims);
    }

    public static final List<Entry> DIMENSIONS_MARICULTURE;
    static
    {
        final List<Entry> maricultureDims = new LinkedList<>();
        maricultureDims.add(SeaAroundUsConst.DIMENSION_TAXON);
        maricultureDims.add(SeaAroundUsConst.DIMENSION_COMMERCIAL_GROUP);
        maricultureDims.add(SeaAroundUsConst.DIMENSION_FUNCTIONAL_GROUP);

        // guarantee immutability
        DIMENSIONS_MARICULTURE = Collections.unmodifiableList(maricultureDims);
    }

    public static final List<Entry> DIMENSIONS_FAO;
    static
    {
        final List<Entry> faoDims = new LinkedList<>();
        faoDims.add(SeaAroundUsConst.DIMENSION_TAXON);
        faoDims.add(SeaAroundUsConst.DIMENSION_COMMERCIAL_GROUP);
        faoDims.add(SeaAroundUsConst.DIMENSION_FUNCTIONAL_GROUP);
        faoDims.add(SeaAroundUsConst.DIMENSION_COUNTRY);
        faoDims.add(SeaAroundUsConst.DIMENSION_SECTOR);
        faoDims.add(SeaAroundUsConst.DIMENSION_CATCH_TYPE);
        faoDims.add(SeaAroundUsConst.DIMENSION_REPORTING_STATUS);
        faoDims.add(SeaAroundUsConst.DIMENSION_EEZ);
        faoDims.add(SeaAroundUsConst.DIMENSION_HIGH_SEAS);

        // guarantee immutability
        DIMENSIONS_FAO = Collections.unmodifiableList(faoDims);
    }

    public static final List<Entry> DIMENSIONS_EEZ;
    static
    {
        final List<Entry> eezDims = new LinkedList<>();
        eezDims.add(SeaAroundUsConst.DIMENSION_TAXON);
        eezDims.add(SeaAroundUsConst.DIMENSION_COMMERCIAL_GROUP);
        eezDims.add(SeaAroundUsConst.DIMENSION_FUNCTIONAL_GROUP);
        eezDims.add(SeaAroundUsConst.DIMENSION_COUNTRY);
        eezDims.add(SeaAroundUsConst.DIMENSION_SECTOR);
        eezDims.add(SeaAroundUsConst.DIMENSION_CATCH_TYPE);
        eezDims.add(SeaAroundUsConst.DIMENSION_REPORTING_STATUS);
        eezDims.add(SeaAroundUsConst.DIMENSION_LAYER);

        // guarantee immutability
        DIMENSIONS_EEZ = Collections.unmodifiableList(eezDims);
    }

    public static final List<Entry> DIMENSIONS_TAXON;
    static
    {
        final List<Entry> taxonDims = new LinkedList<>();
        taxonDims.add(SeaAroundUsConst.DIMENSION_EEZ);
        taxonDims.add(SeaAroundUsConst.DIMENSION_LME);
        taxonDims.add(SeaAroundUsConst.DIMENSION_HIGH_SEAS);
        taxonDims.add(SeaAroundUsConst.DIMENSION_COUNTRY);
        taxonDims.add(SeaAroundUsConst.DIMENSION_COMMERCIAL_GROUP);
        taxonDims.add(SeaAroundUsConst.DIMENSION_FUNCTIONAL_GROUP);
        taxonDims.add(SeaAroundUsConst.DIMENSION_SECTOR);
        taxonDims.add(SeaAroundUsConst.DIMENSION_CATCH_TYPE);
        taxonDims.add(SeaAroundUsConst.DIMENSION_REPORTING_STATUS);

        // guarantee immutability
        DIMENSIONS_TAXON = Collections.unmodifiableList(taxonDims);
    }

    public static final UrlVO GENERIC_URL_VO = new UrlVO(
        "%d?chart=multinational-footprint",
        "multinational-footprint/?region_id=%d",
        "%d/stock-status",
        "stock-status/?region_id=%d&sub_area_id=",
        "%d/marine-trophic-index",
        "marine-trophic-index/?region_id=%d",
        "%d?chart=catch-chart&dimension=%s&measure=%s&limit=10",
        "%3$s/%2$s/?limit=10&region_id=%1$d");

    public static final UrlVO GLOBAL_OCEAN_URL_VO = new UrlVO(
        "?chart=multinational-footprint&subRegion=%d",
        "multinational-footprint/?region_id=1&fao_id=%d",
        "stock-status?subRegion=%d",
        "stock-status/?region_id=1&sub_area_id=%d",
        "marine-trophic-index?subRegion=%d",
        "marine-trophic-index/?region_id=1&transfer_efficiency=0.1&sub_area_id=%d",
        "?chart=catch-chart&dimension=%2$s&measure=%3$s&limit=10&subRegion=%1$d",
        "%3$s/%2$s/?limit=10&sciname=&fao_id=%1$d&region_id=1");


    // PRIVATE

    private SeaAroundUsConst()
    {
    }
}
