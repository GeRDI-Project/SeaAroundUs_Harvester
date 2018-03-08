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
package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;

/**
 * This harvester harvests all HighSeas of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/highseas/
 *
 * @author Robin Weiss
 */
public class HighSeasRegionHarvester extends GenericRegionHarvester<GenericRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * High Seas parameters.
     */
    public HighSeasRegionHarvester()
    {
        super(new TypeToken<GenericResponse<GenericRegion>>() {}, SeaAroundUsRegionConstants.HIGH_SEAS_PARAMS);
    }
}
