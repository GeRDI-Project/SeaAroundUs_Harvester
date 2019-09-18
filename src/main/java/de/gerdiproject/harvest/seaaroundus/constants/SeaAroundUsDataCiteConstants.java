/**
 * Copyright © 2017 Robin Weiss (http://www.gerdi-project.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.gerdiproject.harvest.seaaroundus.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.gerdiproject.json.datacite.Creator;
import de.gerdiproject.json.datacite.Rights;
import de.gerdiproject.json.datacite.extension.generic.AbstractResearch;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.constants.ResearchDisciplineConstants;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;
import de.gerdiproject.json.datacite.nested.Publisher;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *  This static class contains fields and values of SeaAroundUs DataCite documents.
 *
 *  @author Robin Weiss
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeaAroundUsDataCiteConstants
{
    public static final List<Rights> RIGHTS_LIST = createRightsList();
    public static final String REPOSITORY_ID = "Sea Around Us";
    public static final String PROVIDER_NAME = "Sea Around Us - Fisheries, Ecosystems & Biodiversity";
    public static final Publisher PUBLISHER = new Publisher(PROVIDER_NAME, "en");
    public static final WebLink LOGO_LINK = createLogoLink();
    public static final List<Creator> SAU_CREATORS = createSauCreatorList();
    public static final String JSON_FORMAT = "json";
    public static final List<String> JSON_FORMATS = Collections.<String>unmodifiableList(Arrays.asList(JSON_FORMAT));
    public static final String CSV_FORMAT = "csv";
    public static final List<String> CSV_FORMATS = Collections.<String>unmodifiableList(Arrays.asList(JSON_FORMAT, CSV_FORMAT));
    public static final String SAU_LANGUAGE = "en";
    public static final List<AbstractResearch> RESEARCH_DISCIPLINES = createResearchDisciplines();


    // COUNTRY
    public static final String FAO_COUNTRY_PROFILE_LINK_NAME = "FAO Country Profile";
    public static final String COUNTRY_LABEL = "Country Profile: %s";
    public static final String TREATIES_LABEL = "Treaties and Conventions to which %s (%s) is a Member";
    public static final String TREATIES_LABEL_SHORT = "Treaties and Conventions to which %s is a Member";

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
    public static final String FISHERIES_SUBSIDIES_DOWNLOAD_URL_SUFFIX = "subsidies/";
    public static final String FISHERIES_SUBSIDIES_REGION_NAME = "geo-entity";

    public static final String OCEAN_HEALTH_INDEX_LABEL_PREFIX = "Ocean Health Index of ";
    public static final String GLOBAL_SLAVERY_INDEX_LABEL_PREFIX = "Global Slavery Index of ";

    // TAXON
    public static final String CENTIMETERS_SUFFIX = " cm";
    public static final String TAXON_LABEL = "%s (%s)";
    public static final String TAXON_CATCHES_LABEL = "%s of %s by %s";
    public static final String TAXON_VIEW_NAME = "View Taxon Profile";

    // GLOBAL OCEAN
    public static final String GLOBAL_MARINE_TROPHIC_INDEX_LABEL = "the Global Ocean ";
    public static final String GLOBAL_OCEAN_TITLE = "The Global Ocean ";
    public static final String GLOBAL_REGION_NAME = "global";

    // FISHING-ENTITY
    public final static String EXTERNAL_FISHING_ACCESS_LABEL = "%s's foreign fishing access agreements by EEZ";
    public final static String EXTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX = "/external-fishing-access";

    public static final String CATCH_ALLOCATIONS_LABEL = "View catch allocations on a map for the fleets of %s";


    /**
     * Creates a list of Research Disciplines that fit SeaAroundUs.
     *
     * @return a list of Research Disciplines that fit SeaAroundUs
     */
    private static List<AbstractResearch> createResearchDisciplines()
    {
        return Collections.unmodifiableList(
                   Arrays.asList(
                       ResearchDisciplineConstants.OCEANOGRAPHY,
                       ResearchDisciplineConstants.STATISTICS_AND_ECONOMETRICS,
                       ResearchDisciplineConstants.ANIMAL_ECOLOGY
                   ));
    }


    /**
     * Creates a DataCite Rights object that lists the terms of agreement for downloading SeaAroundUs data.
     *
     * @return a DataCite Rights object
     */
    private static List<Rights> createRightsList()
    {
        final Rights termsOfUsage = new Rights(
            "While these data are freely available for use, we ask that you please acknowledge Sea Around Us in your work.\n"
            + "By downloading this data, you agree to provide attribution for any Sea Around Us data you use.");
        return Collections.unmodifiableList(Arrays.asList(termsOfUsage));
    }


    /**
     * Creates a DataCite web link that points to the SeaAroundUs logo.
     *
     * @return a DataCite web link
     */
    private static WebLink createLogoLink()
    {
        final WebLink logoLink = new WebLink("http://www.seaaroundus.org/data/images/logo_saup.png");
        logoLink.setName("Logo");
        logoLink.setType(WebLinkType.ProviderLogoURL);
        return logoLink;
    }


    /**
     * Creates a list with a single DataCite Creator, representing SeaAroundUs as a whole.
     *
     * @return a list with a single DataCite Creator, representing SeaAroundUs as a whole
     */
    private static List<Creator> createSauCreatorList()
    {
        final Creator sauCreator = new Creator(PROVIDER_NAME);
        return Collections.unmodifiableList(Arrays.asList(sauCreator));
    }
}
