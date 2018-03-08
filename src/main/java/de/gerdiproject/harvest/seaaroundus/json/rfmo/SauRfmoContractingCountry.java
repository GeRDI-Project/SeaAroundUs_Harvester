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
package de.gerdiproject.harvest.seaaroundus.json.rfmo;


import com.google.gson.annotations.SerializedName;


/**
 * This class represents a JSON object that is part of the response to a Seaaroundus rfmo request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/rfmo/14
 *
 * @author Robin Weiss
 */
public class SauRfmoContractingCountry
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
