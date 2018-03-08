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
package de.gerdiproject.harvest.seaaroundus.json.fishingentity;

import com.google.gson.annotations.SerializedName;


/**
 * This class represents a JSON array element of the response to a Seaaroundus fishing-entity request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/fishing-entity/
 *
 * @author Robin Weiss
 */
public class SauFishingEntityReduced
{
    int id;
    String title;

    @SerializedName("country_id")
    int countryId;


    public int getCountryId()
    {
        return countryId;
    }


    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }


    public int getId()
    {
        return id;
    }


    public void setId(int id)
    {
        this.id = id;
    }


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }
}
