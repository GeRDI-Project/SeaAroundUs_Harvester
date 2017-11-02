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
package de.gerdiproject.harvest.seaaroundus.json.rfmo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonReduced;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus rfmo request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/rfmo/14
 *
 * @author Robin Weiss
 */
public class SauRfmoRegion extends GenericRegion
{
    @SerializedName("contracting_countries")
    private List<SauRfmoContractingCountry> contractingCountries;

    @SerializedName("secondary_taxa")
    private List<SauTaxonReduced> secondaryTaxa;

    @SerializedName("primary_taxa")
    private List<SauTaxonReduced> primaryTaxa;

    @SerializedName("long_title")
    private String longTitle;

    @SerializedName("profile_url")
    private String profileUrl;


    public List<SauRfmoContractingCountry> getContractingCountries()
    {
        return contractingCountries;
    }


    public void setContractingCountries(List<SauRfmoContractingCountry> value)
    {
        this.contractingCountries = value;
    }


    public List<SauTaxonReduced> getSecondaryTaxa()
    {
        return secondaryTaxa;
    }


    public void setSecondaryTaxa(List<SauTaxonReduced> value)
    {
        this.secondaryTaxa = value;
    }


    public List<SauTaxonReduced> getPrimaryTaxa()
    {
        return primaryTaxa;
    }


    public void setPrimaryTaxa(List<SauTaxonReduced> value)
    {
        this.primaryTaxa = value;
    }


    public String getLongTitle()
    {
        return longTitle;
    }


    public void setLongTitle(String value)
    {
        this.longTitle = value;
    }


    public String getProfileUrl()
    {
        return profileUrl;
    }


    public void setProfileUrl(String value)
    {
        this.profileUrl = value;
    }
}