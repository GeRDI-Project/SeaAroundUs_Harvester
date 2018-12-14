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

import lombok.Data;

/**
 * This class represents part of a JSON object response to a Seaaroundus taxa request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxa/
 *
 * @author Robin Weiss
 */
@Data
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
}
