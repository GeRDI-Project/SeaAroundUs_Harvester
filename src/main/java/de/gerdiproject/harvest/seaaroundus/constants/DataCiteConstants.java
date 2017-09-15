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
    public static final List<String> JSON_FORMATS = Collections.<String>unmodifiableList(Arrays.asList("json"));
    public static final String SAU_LANGUAGE = "en";

    // COUNTRY
    public static final String FAO_COUNTRY_PROFILE_LINK_NAME = "FAO Country Profile";
    public static final String COUNTRY_LABEL_PREFIX = "Country Profile: ";
    public static final String TREATIES_LABEL_PREFIX = "Treaties and Conventions to which %s (%s) is a Member";

    // MARICULTURE
    public static final String MARICULTURE_LABEL_PREFIX = "Mariculture Production in ";
    public static final String MARICULTURE_FILE_NAME = "Mariculture Production by %s in %s";
    public static final String MARICULTURE_SUBREGION_FILE_NAME = "Mariculture Production by %s in %s - %s";


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
