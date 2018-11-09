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

import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;

/**
 * A value object, representing a global sub-region.
 *
 * @author Robin Weiss
 */
public class GlobalRegionVO
{
    private final int id;
    private final String nameSuffix;
    private final SauGlobal global;
    private final String version;


    public GlobalRegionVO(int id, String nameSuffix, SauGlobal global, String version)
    {
        this.id = id;
        this.nameSuffix = nameSuffix;
        this.global = global;
        this.version = version;
    }


    public int getId()
    {
        return id;
    }


    public SauGlobal getGlobal()
    {
        return global;
    }


    public String getNameSuffix()
    {
        return nameSuffix;
    }


    public String getVersion()
    {
        return version;
    }
}
