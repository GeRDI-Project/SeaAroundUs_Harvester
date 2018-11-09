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

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryProperties;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntityReduced;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollection;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.json.GsonUtils;

/**
 *  This static class contains descriptions of regions. Regions are filter
 *  criteria applied to SeaAroundUs datasets.
 *
 *  @author Robin Weiss
 */
public class SeaAroundUsRegionConstants
{
    public static final Type ALL_REGIONS_RESPONSE_TYPE = GsonUtils.<GenericResponse<FeatureCollection<FeatureProperties>>>createType();

    // COUNTRY
    public static final String COUNTRY_API_NAME = "country";
    public static final String COUNTRY_ETL_NAME = "CountryETL";
    public static final Type ALL_COUNTRIES_RESPONSE_TYPE = GsonUtils.<GenericResponse<FeatureCollection<SauCountryProperties>>>createType();
    public static final Type COUNTRY_RESPONSE_TYPE = GsonUtils.<GenericResponse<SauCountry>>createType();

    // MARICULTURE
    public static final String MARICULTURE_API_NAME = "mariculture";
    public static final String MARICULTURE_ETL_NAME = "MaricultureETL";

    private static final EntryVO MEASURE_VALUE = new EntryVO("value", "Real 2010 value (US$)");
    private static final EntryVO MEASURE_TONNAGE = new EntryVO("tonnage", "Catches");
    private static final List<EntryVO> GENERIC_MEASURES = Collections.unmodifiableList(Arrays.asList(MEASURE_VALUE, MEASURE_TONNAGE));

    // TAXON
    public static final String TAXA_API_NAME = "taxa";
    public static final String TAXON_ETL_NAME = "TaxonETL";
    public static final String TAXON_GROUP_API_NAME = "taxon-group";
    public static final String TAXON_LEVEL_API_NAME = "taxon-level";
    public static final EntryVO TAXON_MEASURE_VALUE = new EntryVO("value", "Real 2010 value (US$) of global catches");
    public static final EntryVO TAXON_MEASURE_TONNAGE = new EntryVO("tonnage", "Global catches");
    public static final List<EntryVO> TAXON_MEASURES = Collections.unmodifiableList(Arrays.asList(TAXON_MEASURE_VALUE, TAXON_MEASURE_TONNAGE));


    public static final RegionParametersVO EEZ_PARAMS = new RegionParametersVO(
        "EezETL",
        new EntryVO("eez", "in the Waters of"),
        SeaAroundUsDimensionConstants.DIMENSIONS_EEZ,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO LME_PARAMS = new RegionParametersVO(
        "LmeETL",
        new EntryVO("lme", "in the Waters of"),
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO RFMO_PARAMS = new RegionParametersVO(
        "RfmoETL",
        new EntryVO("rfmo", "in the"),
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO FAO_PARAMS = new RegionParametersVO(
        "FaoETL",
        new EntryVO("fao", "in the Waters of FAO Area"),
        SeaAroundUsDimensionConstants.DIMENSIONS_FAO,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final RegionParametersVO HIGH_SEAS_PARAMS = new RegionParametersVO(
        "HighSeasETL",
        new EntryVO("highseas", "in the Non-EEZ Waters of the"),
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public final static Type ALL_FISHING_ENTITIES_RESPONSE_TYPE = new TypeToken<GenericResponse<List<SauFishingEntityReduced>>>() {} .getType();
    public final static Type FISHING_ENTITY_RESPONSE_TYPE = new TypeToken<GenericResponse<SauFishingEntity>>() {} .getType();
    public static final RegionParametersVO FISHING_ENTITY_PARAMS = new RegionParametersVO(
        "FishingEntitiesETL",
        new EntryVO("fishing-entity", "by the Fleets of"),
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );

    public static final EntryVO REGION_GLOBAL = new EntryVO("global", "in the Global Ocean");
    public static final String GLOBAL_ETL_NAME = "GlobalOceanETL";
    public static final RegionParametersVO GLOBAL_PARAMS = new RegionParametersVO(
        "GlobalOceansETL",
        REGION_GLOBAL,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GENERIC_URL_VO
    );
    public static final RegionParametersVO GLOBAL_SUBREGION_PARAMS = new RegionParametersVO(
        null,
        REGION_GLOBAL,
        SeaAroundUsDimensionConstants.DIMENSIONS_GENERIC,
        GENERIC_MEASURES,
        SeaAroundUsUrlConstants.GLOBAL_OCEAN_URL_VO
    );

    public static final List<String> GLOBAL_SUB_REGION_SUFFIXES =
        Collections.unmodifiableList(Arrays.asList("", "- EEZs of the world", "- High Seas of the world"));


    //Entry regionType, List<Entry> dimensions, UrlVO urls,
    //Class<? extends GenericRegion> regionClass

    /**
     * Private constructor, because this is a static class.
     */
    private SeaAroundUsRegionConstants()
    {
    }
}
