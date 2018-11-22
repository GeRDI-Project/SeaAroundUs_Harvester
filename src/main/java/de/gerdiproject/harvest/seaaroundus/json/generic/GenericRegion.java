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

import java.util.List;

import de.gerdiproject.json.geo.GeoJson;
import lombok.Data;

/**
 * This class represents a generic SeaAroundUs region response.
 *
 * @author Robin Weiss
 */
@Data
public class GenericRegion
{
    private transient Feature<FeatureProperties> feature;

    private String title;
    private int id;
    private GeoJson geojson;
    private List<Metric> metrics;
}
