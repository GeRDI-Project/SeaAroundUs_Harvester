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

import com.google.gson.annotations.SerializedName;

/**
 * This class represents a part of a JSON response to a Seaaroundus country request.
 * <br>e.g. http://api.seaaroundus.org/api/v1/country/120
 *
 * @author Robin Weiss
 */
public class SauNgo
{
    private int international;
    private String website;
    private String email;
    private String address;
    private String fax;

    @SerializedName("tel_number")
    private String telNumber;

    @SerializedName("country_ngo_id")
    private int countryNgoId;

    @SerializedName("ngo_name")
    private String ngoName;

    @SerializedName("country_name")
    private String countryName;

    @SerializedName("count_code")
    private String countCode;


    public int getInternational()
    {
        return international;
    }


    public void setInternational(int value)
    {
        this.international = value;
    }


    public String getWebsite()
    {
        return website;
    }


    public void setWebsite(String value)
    {
        this.website = value;
    }


    public String getTelNumber()
    {
        return telNumber;
    }


    public void setTelNumber(String value)
    {
        this.telNumber = value;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(String value)
    {
        this.email = value;
    }


    public int getCountryNgoId()
    {
        return countryNgoId;
    }


    public void setCountryNgoId(int value)
    {
        this.countryNgoId = value;
    }


    public String getNgoName()
    {
        return ngoName;
    }


    public void setNgoName(String value)
    {
        this.ngoName = value;
    }


    public String getCountryName()
    {
        return countryName;
    }


    public void setCountryName(String value)
    {
        this.countryName = value;
    }


    public String getCountCode()
    {
        return countCode;
    }


    public void setCountCode(String value)
    {
        this.countCode = value;
    }


    public String getAddress()
    {
        return address;
    }


    public void setAddress(String value)
    {
        this.address = value;
    }


    public String getFax()
    {
        return fax;
    }


    public void setFax(String value)
    {
        this.fax = value;
    }
}
