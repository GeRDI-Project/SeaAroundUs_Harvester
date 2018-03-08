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
package de.gerdiproject.harvest.seaaroundus.json.taxa;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus taxon-group request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxon-group/
 *
 * @author Robin Weiss
 */
public final class SauTaxonGroup
{
    private String name;

    @SerializedName("taxon_group_id")
    private int taxonGroupId;


    public String getName()
    {
        return name;
    }


    public void setName(String value)
    {
        this.name = value;
    }


    public int getTaxonGroupId()
    {
        return taxonGroupId;
    }


    public void setTaxonGroupId(int value)
    {
        this.taxonGroupId = value;
    }
}