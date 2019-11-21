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

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * This class represents a JSON object response to a Seaaroundus fishing-entity request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/fishing-entity/1
 *
 * @author Robin Weiss
 */
@Value @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class SauFishingEntity extends GenericRegion
{
    @SerializedName("country_id")
    private int countryId;

    @SerializedName("geo_entity_id")
    private int geoEntityId;
}
