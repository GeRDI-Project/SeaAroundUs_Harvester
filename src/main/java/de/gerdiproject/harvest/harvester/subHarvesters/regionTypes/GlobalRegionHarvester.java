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
package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;

import de.gerdiproject.harvest.harvester.structure.Entry;
import de.gerdiproject.harvest.harvester.structure.SeaAroundUsConst;
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.IJsonObject;

import java.util.List;

/**
 *
 * @author row
 */
public class GlobalRegionHarvester extends GenericRegionHarvester
{
    private final static String MARINE_TROPHIC_INDEX_LABEL_GLOBAL = "the Global Ocean ";

    private final Entry subRegion;


    public GlobalRegionHarvester( Entry subRegion )
    {
        super( SeaAroundUsConst.REGION_GLOBAL,
                SeaAroundUsConst.DIMENSIONS_GENERIC,
                SeaAroundUsConst.GLOBAL_OCEAN_URL_VO );

        this.subRegion = subRegion;
        this.name += " " + subRegion.displayName;
    }


    @Override
    protected IJsonArray getEntries()
    {
        IJsonObject globalRegion = httpRequester.getJsonObjectFromUrl( downloadUrlPrefix + "1" );
        IJsonArray regionArray = jsonBuilder.createArrayFromObjects( globalRegion );

        return regionArray;
    }


    @Override
    protected List<IJsonObject> harvestEntry( IJsonObject obj )
    {
        int subRegionId = Integer.parseInt( subRegion.urlName );

        // get name
        String subRegionName = subRegion.displayName;

        // get default search tags
        List<String> tags = getDefaultSearchTags( obj );

        // create and add documents
        return createDocuments( subRegionId, null, subRegionName, null, tags );
    }


    @Override
    protected IJsonObject createMarineTrophicIndexDocument( int subRegionId, String regionName, IJsonArray geoData, List<String> defaultTags )
    {
        final String regionNameExtended = MARINE_TROPHIC_INDEX_LABEL_GLOBAL + regionName;
        return super.createMarineTrophicIndexDocument( subRegionId, regionNameExtended, geoData, defaultTags );
    }


    @Override
    protected IJsonObject createCatchesByDimensionDocument( int regionId, String regionName, Entry dimension, Entry measure, IJsonArray geoData, List<String> defaultTags )
    {
        String apiUrl;

        // the url differs for the Global Ocean region
        if (regionId == 0)
        {
            apiUrl = SeaAroundUsConst.GENERIC_URL_VO.getCatchesDownloadUrl( downloadUrlPrefix, 1, dimension.urlName, measure.urlName );
        }
        else
        {
            apiUrl = urls.getCatchesDownloadUrl( downloadUrlPrefix, regionId, dimension.urlName, measure.urlName );
        }
        String label = String.format( CATCHES_LABEL, measure.displayName, dimension.displayName, regionType.displayName, regionName );
        String viewUrl = urls.getCatchesViewUrl( viewUrlPrefix, regionId, dimension.urlName, measure.urlName );

        return createDocument( label, apiUrl, viewUrl, geoData, defaultTags );
    }
    

    @Override
    protected IJsonArray getGeoData( IJsonObject regionObject )
    {
        return null;
    }
}
