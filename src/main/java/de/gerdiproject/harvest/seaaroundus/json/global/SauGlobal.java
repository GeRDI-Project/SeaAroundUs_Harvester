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
package de.gerdiproject.harvest.seaaroundus.json.global;

import java.util.List;

import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import lombok.Data;

/**
 * This class represents a JSON object response to a Seaaroundus global request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/global/1
 *
 * @author Robin Weiss
 */
@Data
public class SauGlobal
{
    private transient int subRegionId;
    private transient String subRegionNameSuffix;
    private transient String version;

    private List<Metric> metrics;
}
