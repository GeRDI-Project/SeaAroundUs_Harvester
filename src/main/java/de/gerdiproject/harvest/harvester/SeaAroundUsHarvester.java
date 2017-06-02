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
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.impl.JsonBuilder;

import java.util.Arrays;
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
    private final static List<String> VALID_PROPERTIES = Arrays.asList( PROPERTY_VERSION );


    public static SeaAroundUsHarvester createInstance()
    {
    	IJsonArray harvestedDocuments = new JsonBuilder().createArray();
        
        AbstractHarvester[] subHarvesters = new AbstractHarvester[]
        {
            new TaxonHarvester( harvestedDocuments ),
            new MaricultureHarvester( harvestedDocuments ),
            new CountryHarvester( harvestedDocuments ),
            new FishingEntityRegionHarvester( harvestedDocuments ),
            new RfmoRegionHarvester( harvestedDocuments ),
            new EezRegionHarvester( harvestedDocuments ),
            new GlobalRegionHarvester( harvestedDocuments, SeaAroundUsConst.SUB_REGION_GLOBAL),
            new GlobalRegionHarvester( harvestedDocuments, SeaAroundUsConst.SUB_REGION_EEZS),
            new GlobalRegionHarvester( harvestedDocuments, SeaAroundUsConst.SUB_REGION_HIGH_SEAS),
            new GenericRegionHarvester( harvestedDocuments, SeaAroundUsConst.REGION_LME, SeaAroundUsConst.DIMENSIONS_GENERIC, SeaAroundUsConst.GENERIC_URL_VO),
            new GenericRegionHarvester( harvestedDocuments, SeaAroundUsConst.REGION_FAO, SeaAroundUsConst.DIMENSIONS_FAO, SeaAroundUsConst.GENERIC_URL_VO ),
            new GenericRegionHarvester( harvestedDocuments, SeaAroundUsConst.REGION_HIGH_SEAS, SeaAroundUsConst.DIMENSIONS_GENERIC, SeaAroundUsConst.GENERIC_URL_VO)
        };
        
        return new SeaAroundUsHarvester(harvestedDocuments, subHarvesters);
    }
    
    /**
     * Default Constructor. Sets version to "v1".
     * @param harvestedDocuments
     * @param subHarvesters
     */
    public SeaAroundUsHarvester(IJsonArray harvestedDocuments, AbstractHarvester[] subHarvesters)
    {
        super( harvestedDocuments, subHarvesters);
        super.setProperty( PROPERTY_VERSION, DEFAULT_VERSION );
        
        // set sub-harvester URLs
        final String url = BASE_URL_PREFIX + DEFAULT_VERSION;
        for(AbstractHarvester subHarvester : subHarvesters)
        {
            subHarvester.setProperty( SeaAroundUsConst.PROPERTY_URL, url);
        }
    }
    
    


    @Override
    public void setProperty( String key, String value )
    {
        super.setProperty( key, value );
        
        if (key.equals( PROPERTY_VERSION ))
        {
            for(AbstractHarvester subHarvester : subHarvesters)
            {
                subHarvester.setProperty( SeaAroundUsConst.PROPERTY_URL, BASE_URL_PREFIX + value);
            }
            
            // re-initialize everything
            init( logger );
        }
    }


    @Override
    public List<String> getValidProperties()
    {
        return VALID_PROPERTIES;
    }
}
