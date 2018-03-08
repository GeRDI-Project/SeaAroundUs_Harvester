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

import java.util.List;

import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.GenericRegionHarvester;


/**
 * This value object provides parameters that are required to set up a {@linkplain GenericRegionHarvester}.
 *
 * @author Robin Weiss
 */
public class RegionParametersVO
{
    private final EntryVO regionType;
    private final List<EntryVO> dimensions;
    private final List<EntryVO> measures;
    private final UrlVO urls;


    public RegionParametersVO(EntryVO regionType, List<EntryVO> dimensions, List<EntryVO> measures, UrlVO urls)
    {
        super();
        this.regionType = regionType;
        this.dimensions = dimensions;
        this.measures = measures;
        this.urls = urls;
    }


    public EntryVO getRegionType()
    {
        return regionType;
    }


    public List<EntryVO> getDimensions()
    {
        return dimensions;
    }


    public UrlVO getUrls()
    {
        return urls;
    }


    public List<EntryVO> getMeasures()
    {
        return measures;
    }

}
