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
package de.gerdiproject.harvest.seaaroundus.json;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import de.gerdiproject.harvest.seaaroundus.json.RfmoResponse.RfmoDetailed;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.harvest.seaaroundus.json.lists.AllTaxaResponse.Taxon;
import de.gerdiproject.json.geo.GeoJson;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus rfmo request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/rfmo/14
 *
 * @author Robin Weiss
 */
public final class RfmoResponse extends GenericResponse<RfmoDetailed>
{
    public static final class RfmoDetailed
    {
    	private GeoJson geojson;
    	private int id;
    	private List<Metric> metrics;
    	private String title;

    	@SerializedName("contracting_countries")
    	private List<ContractingCountry> contractingCountries;

    	@SerializedName("secondary_taxa")
    	private List<Taxon> secondaryTaxa;

    	@SerializedName("primary_taxa")
    	private List<Taxon> primaryTaxa;

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


    	public List<ContractingCountry> getContractingCountries()
    	{
    		return contractingCountries;
    	}


    	public void setContractingCountries(List<ContractingCountry> value)
    	{
    		this.contractingCountries = value;
    	}


    	public List<Taxon> getSecondaryTaxa()
    	{
    		return secondaryTaxa;
    	}


    	public void setSecondaryTaxa(List<Taxon> value)
    	{
    		this.secondaryTaxa = value;
    	}


    	public List<Taxon> getPrimaryTaxa()
    	{
    		return primaryTaxa;
    	}


    	public void setPrimaryTaxa(List<Taxon> value)
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
    
    public final static class ContractingCountry
    {
    	private String name;
    	private String iso3;

    	@SerializedName("facp_url")
    	private String facpUrl;


    	public String getName()
    	{
    		return name;
    	}


    	public void setName(String value)
    	{
    		this.name = value;
    	}


    	public String getIso3()
    	{
    		return iso3;
    	}


    	public void setIso3(String value)
    	{
    		this.iso3 = value;
    	}


    	public String getFacpUrl()
    	{
    		return facpUrl;
    	}


    	public void setFacpUrl(String value)
    	{
    		this.facpUrl = value;
    	}
    }
}