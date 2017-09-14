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

import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonProperties;
import de.gerdiproject.json.geo.GeoJson;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus rfmo request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/rfmo/14
 *
 * @author Robin Weiss
 */
public class SauRfmo
{
    private GeoJson geojson;
    private int id;
    private List<Metric> metrics;
    private String title;

    @SerializedName("contracting_countries")
    private List<SauRfmoContractingCountry> contractingCountries;

    @SerializedName("secondary_taxa")
    private List<SauTaxonProperties> secondaryTaxa;

    @SerializedName("primary_taxa")
    private List<SauTaxonProperties> primaryTaxa;

    @SerializedName("long_title")
    private String longTitle;

    @SerializedName("profile_url")
    private String profileUrl;


    public GeoJson getGeojson()
    {
        return geojson;
    }


    public void setGeojson(GeoJson value)
    {
        this.geojson = value;
    }


    public int getId()
    {
        return id;
    }


    public void setId(int value)
    {
        this.id = value;
    }


    public List<SauRfmoContractingCountry> getContractingCountries()
    {
        return contractingCountries;
    }


    public void setContractingCountries(List<SauRfmoContractingCountry> value)
    {
        this.contractingCountries = value;
    }


    public List<SauTaxonProperties> getSecondaryTaxa()
    {
        return secondaryTaxa;
    }


    public void setSecondaryTaxa(List<SauTaxonProperties> value)
    {
        this.secondaryTaxa = value;
    }


    public List<SauTaxonProperties> getPrimaryTaxa()
    {
        return primaryTaxa;
    }


    public void setPrimaryTaxa(List<SauTaxonProperties> value)
    {
        this.primaryTaxa = value;
    }


    public List<Metric> getMetrics()
    {
        return metrics;
    }


    public void setMetrics(List<Metric> value)
    {
        this.metrics = value;
    }


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String value)
    {
        this.title = value;
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
