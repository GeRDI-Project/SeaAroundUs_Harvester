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
package de.gerdiproject.harvest.seaaroundus.json.taxa;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents part of a JSON object response to a Seaaroundus taxa request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxa/
 *
 * @author Robin Weiss
 */
public class SauTaxonReduced
{
    @SerializedName("common_name")
    private String commonName;

    @SerializedName("functional_group")
    private int functionalGroup;

    @SerializedName("is_taxon_distribution_backfilled")
    private boolean isTaxonDistributionBackfilled;

    @SerializedName("commercial_group")
    private int commercialGroup;

    @SerializedName("scientific_name")
    private String scientificName;

    @SerializedName("taxon_key")
    private int taxonKey;


    public String getCommonName()
    {
        return commonName;
    }


    public void setCommonName(String value)
    {
        this.commonName = value;
    }


    public int getFunctionalGroup()
    {
        return functionalGroup;
    }


    public void setFunctionalGroup(int value)
    {
        this.functionalGroup = value;
    }


    public boolean getIsTaxonDistributionBackfilled()
    {
        return isTaxonDistributionBackfilled;
    }


    public void setIsTaxonDistributionBackfilled(boolean value)
    {
        this.isTaxonDistributionBackfilled = value;
    }


    public int getCommercialGroup()
    {
        return commercialGroup;
    }


    public void setCommercialGroup(int value)
    {
        this.commercialGroup = value;
    }


    public String getScientificName()
    {
        return scientificName;
    }


    public void setScientificName(String value)
    {
        this.scientificName = value;
    }


    public int getTaxonKey()
    {
        return taxonKey;
    }


    public void setTaxonKey(int value)
    {
        this.taxonKey = value;
    }
}
