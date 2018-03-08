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
package de.gerdiproject.harvest.harvester;


import de.gerdiproject.harvest.harvester.subHarvesters.MaricultureHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.TaxonHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.CountryHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.EezRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.FaoRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.FishingEntityRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.GlobalRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.HighSeasRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.LmeRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.RfmoRegionHarvester;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsParameterConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;

import java.util.LinkedList;
import java.util.List;


/**
 * The Main Harvester of SeaAroundUs. Has numerous sub-harvesters for each domain of SeaAroundUs data.
 *
 * @author Robin Weiss
 */
public class SeaAroundUsHarvester extends AbstractCompositeHarvester
{
    /**
     * Creates all sub-harvesters that harvest SeaAroundUs.
     *
     * @return all required sub-harvesters
     */
    private static List<AbstractHarvester> createSubHarvesters()
    {
        LinkedList<AbstractHarvester> newSubHarvesters = new LinkedList<>();

        newSubHarvesters.add(new TaxonHarvester());
        newSubHarvesters.add(new MaricultureHarvester());
        newSubHarvesters.add(new CountryHarvester());
        newSubHarvesters.add(new FishingEntityRegionHarvester());

        newSubHarvesters.add(new RfmoRegionHarvester());
        newSubHarvesters.add(new EezRegionHarvester());
        newSubHarvesters.add(new LmeRegionHarvester());
        newSubHarvesters.add(new HighSeasRegionHarvester());
        newSubHarvesters.add(new FaoRegionHarvester());

        newSubHarvesters.add(new GlobalRegionHarvester(SeaAroundUsRegionConstants.SUB_REGION_GLOBAL));
        newSubHarvesters.add(new GlobalRegionHarvester(SeaAroundUsRegionConstants.SUB_REGION_EEZS));
        newSubHarvesters.add(new GlobalRegionHarvester(SeaAroundUsRegionConstants.SUB_REGION_HIGH_SEAS));

        return newSubHarvesters;
    }


    /**
     * Default Constructor. Sets version to "v1".
     */
    public SeaAroundUsHarvester()
    {
        super(createSubHarvesters());
    }


    @Override
    public void setProperty(String key, String value)
    {
        super.setProperty(key, value);

        if (key.equals(SeaAroundUsParameterConstants.VERSION_KEY)) {
            SeaAroundUsDataCiteUtils.instance().setVersion(value);
            final String url = String.format(SeaAroundUsUrlConstants.API_URL, value);

            for (AbstractHarvester subHarvester : subHarvesters)
                subHarvester.setProperty(SeaAroundUsUrlConstants.PROPERTY_URL, url);
        }
    }
}
