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

import de.gerdiproject.harvest.harvester.structure.JsonConst;
import de.gerdiproject.harvest.harvester.structure.SeaAroundUsConst;
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.IJsonObject;

import java.util.List;

/**
 *
 * @author row
 */
public class RfmoRegionHarvester extends GenericRegionHarvester
{
    public RfmoRegionHarvester()
    {
        super( SeaAroundUsConst.REGION_RFMO, SeaAroundUsConst.DIMENSIONS_GENERIC, SeaAroundUsConst.GENERIC_URL_VO );
    }


    @Override
    protected List<String> getDefaultSearchTags( IJsonObject regionObject )
    {
        List<String> tags = super.getDefaultSearchTags( regionObject );

        tags = addTaxa( tags, regionObject.getJsonArray( JsonConst.PRIMARY_TAXA ) );
        tags = addTaxa( tags, regionObject.getJsonArray( JsonConst.SECONDARY_TAXA ) );
        tags = addContractingCountries( tags, regionObject.getJsonArray( JsonConst.CONTRACTING_COUNTRIES ) );

        return tags;
    }


    private List<String> addTaxa( List<String> tags, IJsonArray taxa )
    {
        if (taxa != null)
        {
            for (Object attribute : taxa)
            {
            	IJsonObject metric = (IJsonObject) attribute;

                String commonName = metric.getString( JsonConst.COMMON_NAME, null );
                if (commonName != null)
                {
                    tags.add( commonName );
                }

                String scientificName = metric.getString( JsonConst.SCIENTIFIC_NAME, null );
                if (scientificName != null)
                {
                    tags.add( scientificName );
                }
            }
        }
        return tags;
    }


    private List<String> addContractingCountries( List<String> tags, IJsonArray countries )
    {
        if (countries != null)
        {
            for (Object c : countries)
            {
            	IJsonObject metric = (IJsonObject) c;

                String iso3 = metric.getString( JsonConst.ISO3, null );
                if (iso3 != null)
                {
                    tags.add( iso3 );
                }

                String name = metric.getString( JsonConst.NAME, null );
                if (name != null)
                {
                    tags.add( name );
                }
            }
        }
        return tags;
    }
}
