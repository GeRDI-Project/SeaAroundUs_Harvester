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

import lombok.Data;

/**
 * This class represents a JSON object that is part of many Seaaroundus requests.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/rfmo/14
 *
 * @author Robin Weiss
 */
@Data
public final class Metric
{
    private String title;
    private double value;
    private String units;
}