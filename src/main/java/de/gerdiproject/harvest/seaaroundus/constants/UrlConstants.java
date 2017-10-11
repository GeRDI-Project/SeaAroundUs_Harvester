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
 *  This static class provides SeaAroundUs URL related constants.
 *
 *  @author Robin Weiss
 */
public class UrlConstants
{
    public static final String PROVIDER_URI = "http://www.seaaroundus.org/";
    public final static String API_URL = "http://api.seaaroundus.org/api/%s";
    public final static String PROPERTY_URL = "url";

    public final static String VIEW_URL_PREFIX = "http://www.seaaroundus.org/data/#/%s/";
    public final static String VIEW_URL = VIEW_URL_PREFIX + "%d";
    public final static String REGION_IDS_URL = "/%s/";
    public final static String REGION_URL_SUFFIX = "/%s/%d";
    public final static String CSV_FORM = "&format=csv";

    public final static String CATCHES_URL = "/%s/%s/%s/?limit=10&sciname=&region_id=%d";


    public static final String FISHERIES_SUBSIDIES_VIEW_URL = "http://www.seaaroundus.org/data/#/subsidy/%d";

    // COUNTRY
    public static final String TREATIES_VIEW_URL = "http://www.fishbase.org/Country/CountryTreatyList.php?Country=%s";

    // MARICULTURE
    public static final String MARICULTURE_DOWNLOAD_SUBREGION_URL = "%s%s/%d?limit=20&sub_unit_id=%d" + CSV_FORM;
    public static final String MARICULTURE_DOWNLOAD_ALL_URL = "%s%s/%d?limit=20" + CSV_FORM;

    // FISHING-ENTITY
    public static final String CATCH_ALLOCATIONS_URL = "http://www.seaaroundus.org/data/#/spatial-catch?entities=%d";

    // TAXA
    public static final String TAXON_PROFILE_VIEW_URL = "http://www.seaaroundus.org/data/#/taxa/%d?showHabitatIndex=true";
    public static final String TAXON_CATCH_VIEW_URL = "http://www.seaaroundus.org/data/#/taxon/%d?chart=catch-chart&dimension=%s&measure=%s&limit=10";


    public static final UrlVO GENERIC_URL_VO = new UrlVO(
        "%d?chart=multinational-footprint",
        "multinational-footprint/?region_id=%d",
        "%d/stock-status",
        "stock-status/?region_id=%d&sub_area_id=",
        "%d/marine-trophic-index",
        "marine-trophic-index/?region_id=%d",
        "%d?chart=catch-chart&dimension=%s&measure=%s&limit=10",
        "%3$s/%2$s/?limit=20&region_id=%1$d");


    public static final UrlVO GLOBAL_OCEAN_URL_VO = new UrlVO(
        "?chart=multinational-footprint&subRegion=%d",
        "multinational-footprint/?region_id=1&fao_id=%d",
        "stock-status?subRegion=%d",
        "stock-status/?region_id=1&sub_area_id=%d",
        "marine-trophic-index?subRegion=%d",
        "marine-trophic-index/?region_id=1&transfer_efficiency=0.1&sub_area_id=%d",
        "?chart=catch-chart&dimension=%2$s&measure=%3$s&limit=10&subRegion=%1$d",
        "%3$s/%2$s/?limit=10&sciname=&fao_id=%1$d&region_id=1");


    /**
     * Private constructor, because this is a static class.
     */
    private UrlConstants()
    {
    }
}
