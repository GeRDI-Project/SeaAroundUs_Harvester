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

import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.Data;


/**
 * This class represents a JSON object that is part of the response to a Seaaroundus taxa request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxa/600972
 *
 * @author Robin Weiss
 */
@Data
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
}
