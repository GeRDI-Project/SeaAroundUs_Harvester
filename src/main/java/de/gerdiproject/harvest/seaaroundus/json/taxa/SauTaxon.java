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

import java.util.Map;

import com.google.gson.annotations.SerializedName;


/**
 * This class represents a JSON object that is part of the response to a Seaaroundus taxa request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxa/600972
 *
 * @author Robin Weiss
 */
public class SauTaxon
{
    private double k;
    private double tl;
    private double woo;
    private double loo;

    @SerializedName("common_name")
    private String commonName;

    @SerializedName("y_max")
    private int yMax;

    @SerializedName("commercial_group_id")
    private int commercialGroupId;

    @SerializedName("commercial_group")
    private String commercialGroup;

    @SerializedName("x_max")
    private int xMax;

    @SerializedName("isscaap_id")
    private int isscaapId;

    @SerializedName("taxon_group_id")
    private int taxonGroupId;

    @SerializedName("taxon_level_id")
    private int taxonLevelId;

    @SerializedName("y_min")
    private int yMin;

    @SerializedName("has_habitat_index")
    private boolean hasHabitatIndex;

    @SerializedName("habitat_index")
    private Map<String, Double> habitatIndex;

    @SerializedName("is_baltic_only")
    private boolean isBalticOnly;

    @SerializedName("x_min")
    private int xMin;

    @SerializedName("functional_group_id")
    private int functionalGroupId;

    @SerializedName("lat_north")
    private Double latNorth;

    @SerializedName("is_taxon_distribution_backfilled")
    private boolean isTaxonDistributionBackfilled;

    @SerializedName("taxon_key")
    private int taxonKey;

    @SerializedName("has_map")
    private boolean hasMap;

    @SerializedName("functional_group")
    private String functionalGroup;

    @SerializedName("min_depth")
    private int minDepth;

    @SerializedName("sl_max_cm")
    private double slMaxCm;

    @SerializedName("scientific_name")
    private String scientificName;

    @SerializedName("lat_south")
    private Double latSouth;

    @SerializedName("max_depth")
    private int maxDepth;


    public String getCommonName()
    {
        return commonName;
    }


    public void setCommonName(String value)
    {
        this.commonName = value;
    }


    public int getYMax()
    {
        return yMax;
    }


    public void setYMax(int value)
    {
        this.yMax = value;
    }


    public int getCommercialGroupId()
    {
        return commercialGroupId;
    }


    public void setCommercialGroupId(int value)
    {
        this.commercialGroupId = value;
    }


    public String getCommercialGroup()
    {
        return commercialGroup;
    }


    public void setCommercialGroup(String value)
    {
        this.commercialGroup = value;
    }


    public int getXMax()
    {
        return xMax;
    }


    public void setXMax(int value)
    {
        this.xMax = value;
    }


    public double getK()
    {
        return k;
    }


    public void setK(double value)
    {
        this.k = value;
    }


    public int getIsscaapId()
    {
        return isscaapId;
    }


    public void setIsscaapId(int value)
    {
        this.isscaapId = value;
    }


    public int getTaxonGroupId()
    {
        return taxonGroupId;
    }


    public void setTaxonGroupId(int value)
    {
        this.taxonGroupId = value;
    }


    public double getTl()
    {
        return tl;
    }


    public void setTl(double value)
    {
        this.tl = value;
    }


    public double getWoo()
    {
        return woo;
    }


    public void setWoo(double value)
    {
        this.woo = value;
    }


    public int getTaxonLevelId()
    {
        return taxonLevelId;
    }


    public void setTaxonLevelId(int value)
    {
        this.taxonLevelId = value;
    }


    public int getYMin()
    {
        return yMin;
    }


    public void setYMin(int value)
    {
        this.yMin = value;
    }


    public boolean getHasHabitatIndex()
    {
        return hasHabitatIndex;
    }


    public void setHasHabitatIndex(boolean value)
    {
        this.hasHabitatIndex = value;
    }


    public Map<String, Double> getHabitatIndex()
    {
        return habitatIndex;
    }


    public void setHabitatIndex(Map<String, Double> value)
    {
        this.habitatIndex = value;
    }


    public boolean getIsBalticOnly()
    {
        return isBalticOnly;
    }


    public void setIsBalticOnly(boolean value)
    {
        this.isBalticOnly = value;
    }


    public int getXMin()
    {
        return xMin;
    }


    public void setXMin(int value)
    {
        this.xMin = value;
    }


    public double getLoo()
    {
        return loo;
    }


    public void setLoo(double value)
    {
        this.loo = value;
    }


    public int getFunctionalGroupId()
    {
        return functionalGroupId;
    }


    public void setFunctionalGroupId(int value)
    {
        this.functionalGroupId = value;
    }


    public Double getLatNorth()
    {
        return latNorth;
    }


    public void setLatNorth(Double value)
    {
        this.latNorth = value;
    }


    public boolean getIsTaxonDistributionBackfilled()
    {
        return isTaxonDistributionBackfilled;
    }


    public void setIsTaxonDistributionBackfilled(boolean value)
    {
        this.isTaxonDistributionBackfilled = value;
    }


    public int getTaxonKey()
    {
        return taxonKey;
    }


    public void setTaxonKey(int value)
    {
        this.taxonKey = value;
    }


    public boolean getHasMap()
    {
        return hasMap;
    }


    public void setHasMap(boolean value)
    {
        this.hasMap = value;
    }


    public String getFunctionalGroup()
    {
        return functionalGroup;
    }


    public void setFunctionalGroup(String value)
    {
        this.functionalGroup = value;
    }


    public int getMinDepth()
    {
        return minDepth;
    }


    public void setMinDepth(int value)
    {
        this.minDepth = value;
    }


    public double getSlMaxCm()
    {
        return slMaxCm;
    }


    public void setSlMaxCm(double value)
    {
        this.slMaxCm = value;
    }


    public String getScientificName()
    {
        return scientificName;
    }


    public void setScientificName(String value)
    {
        this.scientificName = value;
    }


    public Double getLatSouth()
    {
        return latSouth;
    }


    public void setLatSouth(Double value)
    {
        this.latSouth = value;
    }


    public int getMaxDepth()
    {
        return maxDepth;
    }


    public void setMaxDepth(int value)
    {
        this.maxDepth = value;
    }
}
