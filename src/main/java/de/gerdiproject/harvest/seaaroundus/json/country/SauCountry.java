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
package de.gerdiproject.harvest.seaaroundus.json.country;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;


/**
 * This class represents a part of a JSON response to a Seaaroundus country request.
 * <br>e.g. http://api.seaaroundus.org/api/v1/country/120
 *
 * @author Robin Weiss
 */
@Data
public class SauCountry
{
    private String cia;
    private int territory;
    private String admin;
    private String country;
    private int id;
    private List<SauNgo> ngo;

    @SerializedName("has_survey")
    private int survey;

    @SerializedName("is_active")
    private boolean active;

    @SerializedName("eez_ppr")
    private double eezPpr;

    @SerializedName("fao_profile_url_direct_link")
    private String faoProfileUrlDirectLink;

    @SerializedName("url_fish_mgt_plan")
    private String urlFishMgtPlan;

    @SerializedName("gov_protect_marine_env")
    private String govProtectMarineEnv;

    @SerializedName("url_major_law_plan")
    private String urlMajorLawPlan;

    @SerializedName("url_gov_protect_marine_env")
    private String urlGovProtectMarineEnv;

    @SerializedName("area_reef")
    private double areaReef;

    @SerializedName("fao_code")
    private String faoCode;

    @SerializedName("has_mpa")
    private int mpa;

    @SerializedName("fao_fisheries")
    private String faoFisheries;

    @SerializedName("fao_profile_url")
    private String faoProfileUrl;

    @SerializedName("per_reef")
    private double perReef;

    @SerializedName("a_code")
    private String aCode;

    @SerializedName("gov_marine_fish")
    private String govMarineFish;

    @SerializedName("un_name")
    private String unName;

    @SerializedName("shelf_area")
    private double shelfArea;

    @SerializedName("major_law_plan")
    private String majorLawPlan;

    @SerializedName("count_code")
    private String countCode;

    @SerializedName("sea_mount")
    private double seaMount;

    @SerializedName("fish_mgt_plan")
    private String fishMgtPlan;

    @SerializedName("avg_pprate")
    private double avgPprate;

    @SerializedName("c_number")
    private int cNumber;

    @SerializedName("fish_base")
    private String fishBase;

    @SerializedName("has_saup_profile")
    private int saupProfile;

    @SerializedName("admin_c_number")
    private int adminCNumber;

    @SerializedName("eez_area")
    private double eezArea;

    @SerializedName("has_estuary")
    private int estuary;

    @SerializedName("per_sea_mount")
    private double perSeaMount;

    @SerializedName("fao_profile_url_v1")
    private String faoProfileUrlV1;
}
