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
package de.gerdiproject.harvest.harvester.subHarvesters;

import de.gerdiproject.harvest.harvester.AbstractJsonArrayHarvester;
import de.gerdiproject.harvest.harvester.structure.JsonConst;
import de.gerdiproject.harvest.harvester.structure.SeaAroundUsConst;
import de.gerdiproject.harvest.utils.SearchIndexFactory;
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.IJsonObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author row
 */
public class CountryHarvester extends AbstractJsonArrayHarvester
{
    private static final String COUNTRY = "country";
    private static final String COUNTRY_PROFILE_LABEL_PREFIX = "Country Profile: ";

    private static final String TREATIES_LABEL_PREFIX = "Treaties and Conventions to which %s (%s) is a Member";
    private static final String TREATIES_VIEW_URL_PREFIX = "http://www.fishbase.org/Country/CountryTreatyList.php?Country=";

    private final String viewUrlPrefix;
    private String downloadUrlPrefix;

    private final HashMap<Integer, IJsonObject> profileDocuments = new HashMap<>();
    private final HashMap<Integer, IJsonObject> treatiesDocuments = new HashMap<>();
    

    public CountryHarvester( IJsonArray harvestedDocuments )
    {
        super( harvestedDocuments, 2 );
        this.viewUrlPrefix = String.format( SeaAroundUsConst.VIEW_URL_PREFIX, COUNTRY );
    }


    @Override
    public void setProperty( String key, String value )
    {
        if (SeaAroundUsConst.PROPERTY_URL.equals( key ))
        {
            downloadUrlPrefix = value + String.format( SeaAroundUsConst.REGION_IDS_URL, COUNTRY );
        }
    }


    @Override
    protected IJsonArray getEntries()
    {
        IJsonObject countryData = httpRequester.getJsonObjectFromUrl( downloadUrlPrefix );
        IJsonArray countryArray = countryData.getJsonArray( JsonConst.FEATURES );

        return countryArray;
    }


    @Override
    protected boolean harvestInternal( int from, int to )
    {
        profileDocuments.clear();
        treatiesDocuments.clear();
        return super.harvestInternal( from, to );
    }

    @Override
    protected List<IJsonObject> harvestEntry( IJsonObject entry )
    {
        // read country number
        IJsonObject properties = entry.getJsonObject( JsonConst.PROPERTIES );
        int countryId = properties.getInt( JsonConst.COUNTRY_ID );

        // get geo data
        IJsonObject geoData = getGeoData( entry );
        
        // check if a document with the same countryId was already harvested
        if(profileDocuments.containsKey(countryId))
        {
        	return updateDocuments(countryId, properties, geoData);
        }
        else
        {
        	return createDocuments(countryId, properties, geoData);
        }
    }
    
    
    private List<IJsonObject> createDocuments(int countryId, IJsonObject properties, IJsonObject geoJson)
    {
    	// retrieve region info from URL
        IJsonObject countryObj = httpRequester.getJsonObjectFromUrl( downloadUrlPrefix + countryId );

        // get search tags
        IJsonArray searchTags = createSearchTags( countryObj, properties );
        
        // create documents
        IJsonObject countryProfileDoc = createCountryProfileDocument( countryObj, geoJson, searchTags );
        IJsonObject treatiesDoc = createTreatiesAndConventionsDocument( countryObj, geoJson, searchTags );
        
        // memorize document references
        profileDocuments.put( countryId, countryProfileDoc );
        treatiesDocuments.put( countryId, treatiesDoc );
        
        // return created documents
        List<IJsonObject> docs = new LinkedList<>();
        docs.add( countryProfileDoc);
        docs.add( treatiesDoc);
        
        return docs;
    }
    
    private List<IJsonObject> updateDocuments(int countryId, IJsonObject properties, IJsonObject geoJson)
    {
    	// retrieve region info from URL
        IJsonObject countryObj = httpRequester.getJsonObjectFromUrl( downloadUrlPrefix + countryId );

        // retrieve existing documents
        IJsonObject profileDoc = profileDocuments.get( countryId );
        IJsonObject treatiesDoc = treatiesDocuments.get( countryId );
        
        // add title to the search tags
        String title = countryObj.getString( JsonConst.TITLE, null );
        profileDoc.getJsonArray( SearchIndexFactory.TAGS_JSON ).add( title );
        treatiesDoc.getJsonArray( SearchIndexFactory.TAGS_JSON ).add( title );
        
        // add geoJson to geo array
        profileDoc.getJsonArray( SearchIndexFactory.GEO_JSON ).add( geoJson );
        treatiesDoc.getJsonArray( SearchIndexFactory.GEO_JSON ).add( geoJson );
        
        // create empty documents to increase the harvesting progress
        List<IJsonObject> docs = new LinkedList<>();
        docs.add( null );
        docs.add( null);
        
        return docs;
    }

    private IJsonObject createCountryProfileDocument( IJsonObject countryObject, IJsonObject geoJson, IJsonArray searchTags )
    {
        int countryId = countryObject.getInt( JsonConst.ID, -1 );
        if (countryId == -1)
        {
            return null;
        }

        String countryName = countryObject.getString( JsonConst.COUNTRY );

        String label = COUNTRY_PROFILE_LABEL_PREFIX + countryName;
        String viewUrl = viewUrlPrefix + countryId;
        IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects( downloadUrlPrefix + countryId );
        IJsonArray geoArray = jsonBuilder.createArrayFromObjects( geoJson );

        return searchIndexFactory.createSearchableDocument(
                label, null, viewUrl, downloadUrls, SeaAroundUsConst.LOGO_URL, null, geoArray, null, searchTags
        );
    }


    private IJsonObject createTreatiesAndConventionsDocument( IJsonObject regionObject, IJsonObject geoJson, IJsonArray searchTags )
    {
        String fishBaseId = regionObject.getString( JsonConst.FISH_BASE, null );
        if (fishBaseId == null)
        {
            return null;
        }
        String faoCode = regionObject.getString( JsonConst.FAO_CODE, "" );
        String regionName = regionObject.getString( JsonConst.COUNTRY );

        String label = String.format( TREATIES_LABEL_PREFIX, regionName, faoCode );
        String viewUrl = TREATIES_VIEW_URL_PREFIX + fishBaseId;
        IJsonArray geoArray = jsonBuilder.createArrayFromObjects( geoJson );

        return searchIndexFactory.createSearchableDocument(
                label, null, viewUrl, null, SeaAroundUsConst.LOGO_URL, null, geoArray, null, searchTags
        );
    }


    private IJsonArray createSearchTags( IJsonObject countryObject, IJsonObject regionProperties )
    {
        IJsonArray tags = jsonBuilder.createArray();

        // the title does not have to be the country, it can also be an island name
        String title = regionProperties.getString( JsonConst.TITLE, null );
        tags.addNotNull( title );

        String countryName = countryObject.getString( JsonConst.COUNTRY, null );
        tags.addNotNull( countryName );

        String unName = countryObject.getString( JsonConst.UN_NAME, null );
        tags.addNotNull( unName );

        String govMarineFish = countryObject.getString( JsonConst.GOV_MARINE_FISH, null );
        tags.addNotNull( govMarineFish );

        String govProtect = countryObject.getString( JsonConst.GOV_PROTECT_MARINE_ENV, null );
        tags.addNotNull( govProtect );

        String fishManagementPlan = countryObject.getString( JsonConst.FISH_MGT_PLAN, null );
        tags.addNotNull( fishManagementPlan );

        String faoCode = countryObject.getString( JsonConst.FAO_CODE, null );
        tags.addNotNull( faoCode );

        return tags;
    }


    private IJsonObject getGeoData( IJsonObject regionObject )
    {
        return regionObject.getJsonObject( JsonConst.GEOMETRY );
    }


    /**
     * Not required, because this list is not visible via REST.
     *
     * @return null
     */
    @Override
    public List<String> getValidProperties()
    {
        return null;
    }
}
