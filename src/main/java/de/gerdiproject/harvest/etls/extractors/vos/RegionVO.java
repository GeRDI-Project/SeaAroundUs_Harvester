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


import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.SauFeatureProperties;
import de.gerdiproject.json.geo.Feature;
import lombok.Value;

/**
 * This value object contains extracted region data.
 *
 * @author Robin Weiss
 */
@Value
public class RegionVO<T>
{
    private final GenericResponse<T> response;
    private final Feature<SauFeatureProperties> feature;
}
