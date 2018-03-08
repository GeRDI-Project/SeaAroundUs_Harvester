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

import java.util.List;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.lme.SauLmeRegion;
import de.gerdiproject.json.datacite.extension.WebLink;
import de.gerdiproject.json.datacite.extension.enums.WebLinkType;

/**
 * This harvester harvests all LMEs of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/lme/
 *
 * @author Robin Weiss
 */
public class LmeRegionHarvester extends GenericRegionHarvester<SauLmeRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * LME parameters.
     */
    public LmeRegionHarvester()
    {
        super(new TypeToken<GenericResponse<SauLmeRegion>>() {}, SeaAroundUsRegionConstants.LME_PARAMS);
    }


    @Override
    protected void enrichWebLinks(List<WebLink> links, SauLmeRegion regionObject)
    {
        super.enrichWebLinks(links, regionObject);

        // add a link to fishbase
        if (regionObject.getFishbaseLink() != null) {
            WebLink fishBaseLink = new WebLink(regionObject.getFishbaseLink());
            fishBaseLink.setName(SeaAroundUsDataCiteConstants.FISHBASE_TAXA_LINK_NAME);
            fishBaseLink.setType(WebLinkType.Related);

            links.add(fishBaseLink);
        }

        // add a link to the LME website
        if (regionObject.getProfileUrl() != null) {
            WebLink lmeLink = new WebLink(regionObject.getProfileUrl());
            lmeLink.setName(SeaAroundUsDataCiteConstants.LME_NOAA_LINK_NAME);
            lmeLink.setType(WebLinkType.Related);

            links.add(lmeLink);
        }
    }
}
