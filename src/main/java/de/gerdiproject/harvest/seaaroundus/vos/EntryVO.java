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
package de.gerdiproject.harvest.seaaroundus.vos;

import lombok.Data;

/**
 * An entry value object in this context is a typical filtering option for SeaAroundUs
 * datasets.
 *
 * @author Robin Weiss
 */
@Data
public class EntryVO
{
    /**
     * The string as it is used in HTTP requests.
     */
    private final String urlName;

    /**
     * The string as it is displayed to the user.
     */
    private final String displayName;
}
