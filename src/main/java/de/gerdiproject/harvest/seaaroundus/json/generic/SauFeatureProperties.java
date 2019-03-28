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
package de.gerdiproject.harvest.seaaroundus.json.generic;

import com.google.gson.annotations.SerializedName;

import de.gerdiproject.json.geo.Feature;
import lombok.Data;

/**
 * This class represents a JSON object of feature propeties that is part of every Seaaroundus {@linkplain Feature}.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/country/
 *
 * @author Robin Weiss
 */
@Data
public class SauFeatureProperties
{
    private String title;
    private String region;

    @SerializedName("region_id")
    private int regionId;

    @SerializedName("long_title")
    private String longTitle;
}