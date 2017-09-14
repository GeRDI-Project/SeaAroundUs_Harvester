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
package de.gerdiproject.harvest.seaaroundus.json.country;

import java.util.List;

import com.google.gson.annotations.SerializedName;


/**
 * This class represents a part of a JSON response to a Seaaroundus country request.
 * <br>e.g. http://api.seaaroundus.org/api/v1/country/120
 *
 * @author Robin Weiss
 */
public class SauCountry
{
    private String cia;
    private int territory;
    private String admin;
    private String country;
    private int id;
    private List<SauNgo> ngo;

    @SerializedName("has_survey")
    private int hasSurvey;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("eez_ppr")
    private int eezPpr;

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
    private int hasMpa;

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
    private int shelfArea;

    @SerializedName("major_law_plan")
    private String majorLawPlan;

    @SerializedName("count_code")
    private String countCode;

    @SerializedName("sea_mount")
    private int seaMount;

    @SerializedName("fish_mgt_plan")
    private String fishMgtPlan;

    @SerializedName("avg_pprate")
    private int avgPprate;

    @SerializedName("c_number")
    private int cNumber;

    @SerializedName("fish_base")
    private String fishBase;

    @SerializedName("has_saup_profile")
    private int hasSaupProfile;

    @SerializedName("admin_c_number")
    private int adminCNumber;

    @SerializedName("eez_area")
    private int eezArea;

    @SerializedName("has_estuary")
    private int hasEstuary;

    @SerializedName("per_sea_mount")
    private int perSeaMount;

    @SerializedName("fao_profile_url_v1")
    private String faoProfileUrlV1;


    public int getHasSurvey()
    {
        return hasSurvey;
    }


    public void setHasSurvey(int value)
    {
        this.hasSurvey = value;
    }


    public boolean getIsActive()
    {
        return isActive;
    }


    public void setIsActive(boolean value)
    {
        this.isActive = value;
    }


    public String getCia()
    {
        return cia;
    }


    public void setCia(String value)
    {
        this.cia = value;
    }


    public int getEezPpr()
    {
        return eezPpr;
    }


    public void setEezPpr(int value)
    {
        this.eezPpr = value;
    }


    public String getFaoProfileUrlDirectLink()
    {
        return faoProfileUrlDirectLink;
    }


    public void setFaoProfileUrlDirectLink(String value)
    {
        this.faoProfileUrlDirectLink = value;
    }


    public String getUrlFishMgtPlan()
    {
        return urlFishMgtPlan;
    }


    public void setUrlFishMgtPlan(String value)
    {
        this.urlFishMgtPlan = value;
    }


    public String getGovProtectMarineEnv()
    {
        return govProtectMarineEnv;
    }


    public void setGovProtectMarineEnv(String value)
    {
        this.govProtectMarineEnv = value;
    }


    public String getUrlMajorLawPlan()
    {
        return urlMajorLawPlan;
    }


    public void setUrlMajorLawPlan(String value)
    {
        this.urlMajorLawPlan = value;
    }


    public String getUrlGovProtectMarineEnv()
    {
        return urlGovProtectMarineEnv;
    }


    public void setUrlGovProtectMarineEnv(String value)
    {
        this.urlGovProtectMarineEnv = value;
    }


    public double getAreaReef()
    {
        return areaReef;
    }


    public void setAreaReef(double value)
    {
        this.areaReef = value;
    }


    public String getFaoCode()
    {
        return faoCode;
    }


    public void setFaoCode(String value)
    {
        this.faoCode = value;
    }


    public int getHasMpa()
    {
        return hasMpa;
    }


    public void setHasMpa(int value)
    {
        this.hasMpa = value;
    }


    public String getFaoFisheries()
    {
        return faoFisheries;
    }


    public void setFaoFisheries(String value)
    {
        this.faoFisheries = value;
    }


    public String getFaoProfileUrl()
    {
        return faoProfileUrl;
    }


    public void setFaoProfileUrl(String value)
    {
        this.faoProfileUrl = value;
    }


    public double getPerReef()
    {
        return perReef;
    }


    public void setPerReef(double value)
    {
        this.perReef = value;
    }


    public int getTerritory()
    {
        return territory;
    }


    public void setTerritory(int value)
    {
        this.territory = value;
    }


    public String getACode()
    {
        return aCode;
    }


    public void setACode(String value)
    {
        this.aCode = value;
    }


    public String getGovMarineFish()
    {
        return govMarineFish;
    }


    public void setGovMarineFish(String value)
    {
        this.govMarineFish = value;
    }


    public String getUnName()
    {
        return unName;
    }


    public void setUnName(String value)
    {
        this.unName = value;
    }


    public int getShelfArea()
    {
        return shelfArea;
    }


    public void setShelfArea(int value)
    {
        this.shelfArea = value;
    }


    public String getMajorLawPlan()
    {
        return majorLawPlan;
    }


    public void setMajorLawPlan(String value)
    {
        this.majorLawPlan = value;
    }


    public String getAdmin()
    {
        return admin;
    }


    public void setAdmin(String value)
    {
        this.admin = value;
    }


    public String getCountry()
    {
        return country;
    }


    public void setCountry(String value)
    {
        this.country = value;
    }


    public String getCountCode()
    {
        return countCode;
    }


    public void setCountCode(String value)
    {
        this.countCode = value;
    }


    public int getSeaMount()
    {
        return seaMount;
    }


    public void setSeaMount(int value)
    {
        this.seaMount = value;
    }


    public String getFishMgtPlan()
    {
        return fishMgtPlan;
    }


    public void setFishMgtPlan(String value)
    {
        this.fishMgtPlan = value;
    }


    public int getAvgPprate()
    {
        return avgPprate;
    }


    public void setAvgPprate(int value)
    {
        this.avgPprate = value;
    }


    public int getCNumber()
    {
        return cNumber;
    }


    public void setCNumber(int value)
    {
        this.cNumber = value;
    }


    public int getId()
    {
        return id;
    }


    public void setId(int value)
    {
        this.id = value;
    }


    public String getFishBase()
    {
        return fishBase;
    }


    public void setFishBase(String value)
    {
        this.fishBase = value;
    }


    public int getHasSaupProfile()
    {
        return hasSaupProfile;
    }


    public void setHasSaupProfile(int value)
    {
        this.hasSaupProfile = value;
    }


    public List<SauNgo> getNgo()
    {
        return ngo;
    }


    public void setNgo(List<SauNgo> value)
    {
        this.ngo = value;
    }


    public int getAdminCNumber()
    {
        return adminCNumber;
    }


    public void setAdminCNumber(int value)
    {
        this.adminCNumber = value;
    }


    public int getEezArea()
    {
        return eezArea;
    }


    public void setEezArea(int value)
    {
        this.eezArea = value;
    }


    public int getHasEstuary()
    {
        return hasEstuary;
    }


    public void setHasEstuary(int value)
    {
        this.hasEstuary = value;
    }


    public int getPerSeaMount()
    {
        return perSeaMount;
    }


    public void setPerSeaMount(int value)
    {
        this.perSeaMount = value;
    }


    public String getFaoProfileUrlV1()
    {
        return faoProfileUrlV1;
    }


    public void setFaoProfileUrlV1(String value)
    {
        this.faoProfileUrlV1 = value;
    }
}
