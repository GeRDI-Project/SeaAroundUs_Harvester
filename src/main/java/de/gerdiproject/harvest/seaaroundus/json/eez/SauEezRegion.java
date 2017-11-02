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
package de.gerdiproject.harvest.seaaroundus.json.eez;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;

/**
 * This class represents a JSON object response to a Seaaroundus eez request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/eez/12
 *
 * @author Robin Weiss
 */
public class SauEezRegion extends GenericRegion
{
    @SerializedName("gsi_link")
    private String gsiLink;

    @SerializedName("intersecting_fao_area_id")
    private List<Integer> intersectingFaoAreaId;

    @SerializedName("fishbase_id")
    private String fishbaseId;

    @SerializedName("c_code")
    private String cCode;

    @SerializedName("ohi_link")
    private String ohiLink;

    @SerializedName("geo_entity_id")
    private int geoEntityId;

    @SerializedName("fao_profile_url")
    private String faoProfileUrl;

    @SerializedName("fao_rfb")
    private List<SauFaoRfb> faoRfb;

    @SerializedName("year_started_eez_at")
    private int yearStartedEezAt;

    @SerializedName("reconstruction_documents")
    private List<SauReconstructionDocument> reconstructionDocuments;

    @SerializedName("year_allowed_to_fish_high_seas")
    private int yearAllowedToFishHighSeas;

    @SerializedName("declaration_year")
    private int declarationYear;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("country_name")
    private String countryName;

    @SerializedName("estuary_count")
    private int estuaryCount;

    @SerializedName("year_allowed_to_fish_other_eezs")
    private int yearAllowedToFishOtherEezs;


    public String getGsiLink()
    {
        return gsiLink;
    }


    public void setGsiLink(String value)
    {
        this.gsiLink = value;
    }


    public List<Integer> getIntersectingFaoAreaId()
    {
        return intersectingFaoAreaId;
    }


    public void setIntersectingFaoAreaId(List<Integer> value)
    {
        this.intersectingFaoAreaId = value;
    }


    public String getFishbaseId()
    {
        return fishbaseId;
    }


    public void setFishbaseId(String value)
    {
        this.fishbaseId = value;
    }


    public String getCCode()
    {
        return cCode;
    }


    public void setCCode(String value)
    {
        this.cCode = value;
    }


    public String getOhiLink()
    {
        return ohiLink;
    }


    public void setOhiLink(String value)
    {
        this.ohiLink = value;
    }


    public int getGeoEntityId()
    {
        return geoEntityId;
    }


    public void setGeoEntityId(int value)
    {
        this.geoEntityId = value;
    }


    public String getFaoProfileUrl()
    {
        return faoProfileUrl;
    }


    public void setFaoProfileUrl(String value)
    {
        this.faoProfileUrl = value;
    }


    public List<SauFaoRfb> getFaoRfb()
    {
        return faoRfb;
    }


    public void setFaoRfb(List<SauFaoRfb> value)
    {
        this.faoRfb = value;
    }


    public int getYearStartedEezAt()
    {
        return yearStartedEezAt;
    }


    public void setYearStartedEezAt(int value)
    {
        this.yearStartedEezAt = value;
    }


    public List<SauReconstructionDocument> getReconstructionDocuments()
    {
        return reconstructionDocuments;
    }


    public void setReconstructionDocuments(List<SauReconstructionDocument> value)
    {
        this.reconstructionDocuments = value;
    }


    public int getYearAllowedToFishHighSeas()
    {
        return yearAllowedToFishHighSeas;
    }


    public void setYearAllowedToFishHighSeas(int value)
    {
        this.yearAllowedToFishHighSeas = value;
    }


    public int getDeclarationYear()
    {
        return declarationYear;
    }


    public void setDeclarationYear(int value)
    {
        this.declarationYear = value;
    }


    public int getCountryId()
    {
        return countryId;
    }


    public void setCountryId(int value)
    {
        this.countryId = value;
    }


    public String getCountryName()
    {
        return countryName;
    }


    public void setCountryName(String value)
    {
        this.countryName = value;
    }


    public int getEstuaryCount()
    {
        return estuaryCount;
    }


    public void setEstuaryCount(int value)
    {
        this.estuaryCount = value;
    }


    public int getYearAllowedToFishOtherEezs()
    {
        return yearAllowedToFishOtherEezs;
    }


    public void setYearAllowedToFishOtherEezs(int value)
    {
        this.yearAllowedToFishOtherEezs = value;
    }
}
