/*
 *  Copyright © 2019 Robin Weiss (http://www.gerdi-project.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package de.gerdiproject.harvest.etls.extractors.vos;


import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntityReduced;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import lombok.Value;

/**
 * This value object contains extracted fishing entity data.
 *
 * @author Robin Weiss
 */
@Value
public class FishingEntityVO
{
    private final GenericResponse<SauFishingEntity> response;
    private final SauFishingEntityReduced baseInfo;
}
