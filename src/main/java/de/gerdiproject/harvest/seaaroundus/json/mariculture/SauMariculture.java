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
package de.gerdiproject.harvest.seaaroundus.json.mariculture;

import com.google.gson.annotations.SerializedName;
import com.vividsolutions.jts.geom.Geometry;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a JSON object that is part of the response to a Seaaroundus mariculture request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/mariculture/57
 *
 * @author Robin Weiss
 */
@Data @EqualsAndHashCode(callSuper = true)
public class SauMariculture extends GenericRegion
{
    @SerializedName("country_name")
    private String countryName;

    @SerializedName("point_geojson")
    private Geometry pointGeojson;

    @SerializedName("entity_id")
    private int entityId;

    @SerializedName("region_id")
    private int regionId;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("total_production")
    private double totalProduction;
}
