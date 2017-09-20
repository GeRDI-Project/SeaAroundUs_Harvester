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

import de.gerdiproject.json.datacite.Creator;
import de.gerdiproject.json.datacite.Rights;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

/**
 *  This static class contains fields and values of SeaAroundUs DataCite documents.
 *
 *  @author Robin Weiss
 */
public class DataCiteConstants
{
    public static final List<Rights> RIGHTS_LIST = createRightsList();
    public static final String PROVIDER = "Sea Around Us";
    public static final String PROVIDER_URI = "http://www.seaaroundus.org/";
    public static final WebLink LOGO_LINK = createLogoLink();
    public static final List<Creator> SAU_CREATORS = createSauCreatorList();
    public static final String JSON_FORMAT = "json";
    public static final List<String> JSON_FORMATS = Collections.<String>unmodifiableList(Arrays.asList(JSON_FORMAT));
    public static final String CSV_FORMAT = "csv";
    public static final List<String> CSV_FORMATS = Collections.<String>unmodifiableList(Arrays.asList(JSON_FORMAT, CSV_FORMAT));
    public static final String SAU_LANGUAGE = "en";

    // COUNTRY
    public static final String FAO_COUNTRY_PROFILE_LINK_NAME = "FAO Country Profile";
    public static final String COUNTRY_LABEL_PREFIX = "Country Profile: ";
    public static final String TREATIES_LABEL_PREFIX = "Treaties and Conventions to which %s (%s) is a Member";

    // MARICULTURE
    public static final String MARICULTURE_LABEL_PREFIX = "Mariculture Production in ";
    public static final String MARICULTURE_FILE_NAME = "Mariculture Production by %s in %s";
    public static final String MARICULTURE_SUBREGION_FILE_NAME = "Mariculture Production by %s in %s - %s";

    // CATCHES
    public static final String NAME_FORMAT = "%C%sHarvester";
    public static final String MARINE_TROPHIC_INDEX_LABEL = "Region-based Marine Trophic Index of the catch in the waters of %s";

    public static final String GENERIC_LABEL = "Catches %s %s";
    public static final String CATCHES_LABEL = "%s by %s %s %s";
    public static final String PRIMARY_PRODUCTION_LABEL = "Primary Production Required for catches %s %s";
    public static final String STOCK_STATUS_LABEL = "Stock status %s %s";

    // LME
    public static final String FISHBASE_TAXA_LINK_NAME = "FishBase Taxa";
    public static final String LME_NOAA_LINK_NAME = "LME profile (NOAA)";

    // RFMO
    public static final String CONTRACTING_COUNTRY_NAME = "Contracting Country: %s (%s)";

    // EEZ
    public static final String INTERNAL_FISHING_ACCESS_LABEL_PREFIX = "Foreign fishing access in the waters of ";
    public static final String INTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX = "/internal-fishing-access";
    public static final String INTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX = "/access-agreement-internal/";

    public static final String FISHERIES_SUBSIDIES_LABEL_PREFIX = "Fisheries Subsidies in ";
    public static final String FISHERIES_SUBSIDIES_VIEW_URL_PREFIX = "http://www.seaaroundus.org/data/#/subsidy/";
    public static final String FISHERIES_SUBSIDIES_DOWNLOAD_URL_SUFFIX = "subsidies/";
    public static final String FISHERIES_SUBSIDIES_REGION_NAME = "geo-entity";

    public static final String OCEAN_HEALTH_INDEX_LABEL_PREFIX = "Ocean Health Index of ";
    public static final String GLOBAL_SLAVERY_INDEX_LABEL_PREFIX = "Global Slavery Index of ";

    // TAXON
    public static final String CENTIMETERS_SUFFIX = " cm";
    public static final String TAXON_LABEL = "%s (%s)";
    public static final String TAXON_CATCHES_LABEL = "%s of %s by %s";

    public static final String TAXON_PROFILE_VIEW_URL = "http://www.seaaroundus.org/data/#/taxa/%d?showHabitatIndex=true";
    public static final String TAXON_CATCH_VIEW_URL = "http://www.seaaroundus.org/data/#/taxon/%d?chart=catch-chart&dimension=%s&measure=%s&limit=10";
    public static final String TAXON_VIEW_NAME = "View Taxon Profile";


    public static final Entry TAXON_MEASURE_VALUE = new Entry("value", "Real 2010 value (US$) of global catches");
    public static final Entry TAXON_MEASURE_TONNAGE = new Entry("tonnage", "Global catches");
    public static final List<Entry> TAXON_MEASURES = Collections.unmodifiableList(Arrays.asList(TAXON_MEASURE_VALUE, TAXON_MEASURE_TONNAGE));


    /**
     * Private constructor, because this is a static class.
     */
    private DataCiteConstants()
    {
    }


    private static List<Rights> createRightsList()
    {
        Rights termsOfUsage = new Rights();
        termsOfUsage.setValue("While these data are freely available for use, we ask that you please acknowledge Sea Around Us in your work.\n"
                              + "By downloading this data, you agree to provide attribution for any Sea Around Us data you use.");
        return Collections.unmodifiableList(Arrays.asList(termsOfUsage));
    }


    private static WebLink createLogoLink()
    {
        WebLink logoLink = new WebLink("http://www.seaaroundus.org/data/images/logo_saup.png");
        logoLink.setName("Logo");
        logoLink.setType(WebLinkType.ProviderLogoURL);
        return logoLink;
    }


    private static List<Creator> createSauCreatorList()
    {
        Creator sauCreator = new Creator(PROVIDER);
        return Collections.unmodifiableList(Arrays.asList(sauCreator));
    }
}
