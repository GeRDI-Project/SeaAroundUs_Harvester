/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
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
package de.gerdiproject.harvest.harvester;


import de.gerdiproject.harvest.harvester.structure.SeaAroundUsConst;
import de.gerdiproject.harvest.harvester.subHarvesters.MaricultureHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.TaxonHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.CountryHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.EezRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.FishingEntityRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.GenericRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.GlobalRegionHarvester;
import de.gerdiproject.harvest.harvester.subHarvesters.regionTypes.RfmoRegionHarvester;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author row
 */
public class SeaAroundUsHarvester extends AbstractCompositeHarvester
{
    // URLs
    private final static String BASE_URL_PREFIX = "http://api.seaaroundus.org/api/";

    // properties
    private final static String PROPERTY_VERSION = "version";
    private final static String DEFAULT_VERSION = "v1";
    private final static List<String> VALID_PROPERTIES = Arrays.asList(PROPERTY_VERSION);


    /**
     * Creates all sub-harvesters that harvest SeaAroundUs.
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
        newSubHarvesters.add(new GlobalRegionHarvester(SeaAroundUsConst.SUB_REGION_GLOBAL));
        newSubHarvesters.add(new GlobalRegionHarvester(SeaAroundUsConst.SUB_REGION_EEZS));
        newSubHarvesters.add(new GlobalRegionHarvester(SeaAroundUsConst.SUB_REGION_HIGH_SEAS));
        newSubHarvesters.add(new GenericRegionHarvester(
                                 SeaAroundUsConst.REGION_LME,
                                 SeaAroundUsConst.DIMENSIONS_GENERIC,
                                 SeaAroundUsConst.GENERIC_URL_VO));
        newSubHarvesters.add(new GenericRegionHarvester(
                                 SeaAroundUsConst.REGION_FAO,
                                 SeaAroundUsConst.DIMENSIONS_FAO,
                                 SeaAroundUsConst.GENERIC_URL_VO));
        newSubHarvesters.add(new GenericRegionHarvester(
                                 SeaAroundUsConst.REGION_HIGH_SEAS,
                                 SeaAroundUsConst.DIMENSIONS_GENERIC,
                                 SeaAroundUsConst.GENERIC_URL_VO));

        return newSubHarvesters;
    }


    /**
     * Default Constructor. Sets version to "v1".
     */
    public SeaAroundUsHarvester()
    {
        super(createSubHarvesters());

        // set default version
        setProperty(PROPERTY_VERSION, DEFAULT_VERSION);
    }


    @Override
    public void setProperty(String key, String value)
    {
        super.setProperty(key, value);

        if (key.equals(PROPERTY_VERSION)) {
            final String url = BASE_URL_PREFIX + value;

            for (AbstractHarvester subHarvester : subHarvesters)
                subHarvester.setProperty(SeaAroundUsConst.PROPERTY_URL, url);
        }
    }


    @Override
    public List<String> getValidProperties()
    {
        return VALID_PROPERTIES;
    }
}
