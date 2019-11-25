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
package de.gerdiproject.harvest.seaaroundus.json.lme;


import com.google.gson.annotations.SerializedName;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This class represents the data object of a SeaAroundUs LME response.
 * <br> e.g. http://api.seaaroundus.org/api/v1/lme/12
 *
 * @author Robin Weiss
 */
@Data @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class SauLmeRegion extends GenericRegion
{
    @SerializedName("fishbase_link")
    private String fishbaseLink;

    @SerializedName("profile_url")
    private String profileUrl;
}
