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
import de.gerdiproject.harvest.harvester.structure.Entry;
import de.gerdiproject.harvest.harvester.structure.JsonConst;
import de.gerdiproject.harvest.harvester.structure.SeaAroundUsConst;
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.IJsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author row
 */
public class TaxonHarvester extends AbstractJsonArrayHarvester
{
    private static final String API_URL_SUFFIX = "/taxa/";
    private static final String TAXON_GROUP_URL_SUFFIX = "/taxon-group/";
    private static final String TAXON_LEVEL_URL_SUFFIX = "/taxon-level/";

    private static final String CENTIMETERS_SUFFIX = " cm";
    private static final String TAXON_LABEL = "%s (%s)";
    private static final String CATCH_LABEL = "%s of %s (%s) by %s";

    private static final String TAXON_PROFILE_VIEW_URL = "http://www.seaaroundus.org/data/#/taxa/%d?showHabitatIndex=true";
    private static final String TAXON_CATCH_VIEW_URL = "http://www.seaaroundus.org/data/#/taxon/%d?chart=catch-chart&dimension=%s&measure=%s&limit=10";
    private static final String TAXON_CATCH_API_URL_SUFFIX = "%s/%s/?limit=10&sciname=&region_id=%d";

    private static final Entry MEASURE_VALUE = new Entry("value", "Real 2010 value (US$) of global catches");
    private static final Entry MEASURE_TONNAGE = new Entry("tonnage", "Global catches");
    private static final Entry[] MEASURES = {
        MEASURE_VALUE, MEASURE_TONNAGE
    };

    private Map<Integer, String> taxonGroups;
    private Map<Integer, String> taxonLevels;
    private String taxaUrl;


    /**
     * @param harvestedDocuments the list to which harvested documents are added
     */
    public TaxonHarvester()
    {
        super(1 + MEASURES.length * SeaAroundUsConst.DIMENSIONS_TAXON.size());
    }


    @Override
    public void setProperty(String key, String value)
    {
        if (SeaAroundUsConst.PROPERTY_URL.equals(key)) {
            taxaUrl = value + API_URL_SUFFIX;
            taxonGroups = getTaxonGroups(value);
            taxonLevels = getTaxonLevels(value);
        }
    }


    @Override
    protected IJsonArray getJsonArray()
    {
        IJsonArray taxonList = httpRequester.getJsonArrayFromUrl(taxaUrl);
        return taxonList;
    }


    @Override
    protected List<IJsonObject> harvestJsonArrayEntry(IJsonObject taxon)
    {
        final int taxonKey = taxon.getInt(JsonConst.TAXON_KEY);

        // get names
        String commonName = taxon.getString(JsonConst.COMMON_NAME);
        String scientificName = taxon.getString(JsonConst.SCIENTIFIC_NAME);

        List<IJsonObject> documentList = new ArrayList<>(numberOfDocumentsPerEntry);

        // harvest Taxon Catches
        for (Entry measure : MEASURES) {
            for (Entry dimension : SeaAroundUsConst.DIMENSIONS_TAXON)
                documentList.add(createTaxonCatchDocument(taxonKey, commonName, scientificName, measure, dimension));
        }

        // harvest Taxon Profile
        documentList.add(createTaxonProfileDocument(taxonKey, commonName, scientificName));

        return documentList;
    }


    /**
     * Gets a list of taxon group names.
     *
     * @param baseUrl the Sea Around Us API base url
     * @return a ist of taxon group names
     */
    private Map<Integer, String> getTaxonGroups(String baseUrl)
    {
        Map<Integer, String> taxonGroupMap = new HashMap<>();

        String url = baseUrl + TAXON_GROUP_URL_SUFFIX;
        IJsonArray taxonGroupList = httpRequester.getJsonArrayFromUrl(url);

        for (Object attribute : taxonGroupList) {
            IJsonObject obj = (IJsonObject) attribute;

            int key = obj.getInt(JsonConst.TAXON_GROUP_ID, 0);
            String value = obj.getString(JsonConst.NAME, null);

            taxonGroupMap.put(key, value);
        }

        return taxonGroupMap;
    }


    /**
     * Gets a list of taxon level names.
     *
     * @param baseUrl the Sea Around Us API base url
     * @return a ist of taxon level names
     */
    private Map<Integer, String> getTaxonLevels(String baseUrl)
    {
        Map<Integer, String> taxonLevelMap = new HashMap<>();

        String url = baseUrl + TAXON_LEVEL_URL_SUFFIX;
        IJsonArray taxonGroupList = httpRequester.getJsonArrayFromUrl(url);

        for (Object attribute : taxonGroupList) {
            IJsonObject obj = (IJsonObject) attribute;

            int key = obj.getInt(JsonConst.TAXON_LEVEL_ID, 0);
            String value = obj.getString(JsonConst.NAME, null);

            taxonLevelMap.put(key, value);
        }

        return taxonLevelMap;
    }


    private IJsonObject createTaxonCatchDocument(int taxonKey, String commonName, String scientificName, Entry measure, Entry dimension)
    {
        // get catch data from URL
        String apiUrl = taxaUrl + String.format(TAXON_CATCH_API_URL_SUFFIX, measure.urlName, dimension.urlName, taxonKey);
        IJsonArray catchRegionData = httpRequester.getJsonArrayFromUrl(apiUrl);

        IJsonObject document = null;

        // only add a taxon document for which sufficient catch data exists
        if (catchRegionData != null) {
            // create search tags and years
            IJsonArray searchTags = getTaxonCatchSearchTags(catchRegionData);
            IJsonArray years = getTaxonCatchYears(catchRegionData);

            String label = String.format(CATCH_LABEL, measure.displayName, commonName, scientificName, dimension.displayName);
            String viewUrl = String.format(TAXON_CATCH_VIEW_URL, taxonKey, dimension.urlName, measure.urlName);
            IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects(apiUrl + SeaAroundUsConst.CSV_FORM);

            // add document to index
            document = searchIndexFactory.createSearchableDocument(
                           label,
                           null,
                           viewUrl,
                           downloadUrls,
                           SeaAroundUsConst.LOGO_URL,
                           null,
                           null,
                           years,
                           searchTags
                       );
        }

        return document;
    }


    private IJsonObject createTaxonProfileDocument(int taxonKey, String commonName, String scientificName)
    {
        String label = createTaxonLabel(commonName, scientificName);
        String viewUrl = String.format(TAXON_PROFILE_VIEW_URL, taxonKey);
        String apiUrl = taxaUrl + taxonKey;

        IJsonObject profileData = httpRequester.getJsonObjectFromUrl(apiUrl);
        IJsonArray tags = getTaxonProfileSearchTags(profileData);
        IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects(apiUrl);
        IJsonArray geoData = getTaxonGeoData(profileData);

        return searchIndexFactory.createSearchableDocument(
                   label,
                   null,
                   viewUrl,
                   downloadUrls,
                   SeaAroundUsConst.LOGO_URL,
                   null,
                   geoData,
                   null,
                   tags
               );
    }


    private String createTaxonLabel(String commonName, String scientificName)
    {

        String label;

        if (commonName != null && scientificName != null)
            label = String.format(TAXON_LABEL, commonName, scientificName);
        else if (commonName != null)
            label = commonName;
        else
            label = scientificName;

        return label;
    }


    private IJsonArray getTaxonCatchSearchTags(IJsonArray catchRegionData)
    {
        final IJsonArray tags = jsonBuilder.createArray();

        catchRegionData.forEach((region) ->
                                tags.add(((IJsonObject) region).getString(JsonConst.KEY, null))
                               );
        return tags;
    }


    private IJsonArray getTaxonCatchYears(IJsonArray catchRegionData)
    {
        final IJsonArray years = jsonBuilder.createArray();
        final IJsonObject firstRegion = catchRegionData.getJsonObject(0);

        if (firstRegion != null) {
            IJsonArray catchValues = firstRegion.getJsonArray(JsonConst.VALUES);

            if (catchValues != null) {
                catchValues.forEach((catchValue) -> {
                    years.add(((IJsonArray) catchValue).getInt(0));
                });
            }
        }

        return years;
    }


    private IJsonArray getTaxonGeoData(IJsonObject taxonObj)
    {
        // check if geo data is available
        if (taxonObj.isNull(JsonConst.LATITUDE_NORTH) || taxonObj.isNull(JsonConst.LATITUDE_SOUTH))
            return null;

        // retrieve geo data
        Double latitudeNorth = taxonObj.getDouble(JsonConst.LATITUDE_NORTH);
        Double latitudeSouth = taxonObj.getDouble(JsonConst.LATITUDE_SOUTH);

        IJsonObject geoJson = jsonBuilder.geoBuilder().createHorizontalRing(latitudeNorth, latitudeSouth);

        return jsonBuilder.createArrayFromObjects(geoJson);
    }


    /**
     * Creates an array of search tags for a taxon.
     *
     * @param taxonObj a JSON-object that contains taxon data
     * @return a list of search tags
     */
    private IJsonArray getTaxonProfileSearchTags(IJsonObject taxonObj)
    {
        IJsonArray taxonProfile = jsonBuilder.createArrayFromObjects(

                                      // functional group name
                                      taxonObj.getString(JsonConst.FUNCTIONAL_GROUP),

                                      // commercial group name
                                      taxonObj.getString(JsonConst.COMMERCIAL_GROUP),

                                      // taxon maximum length
                                      taxonObj.getInt(JsonConst.TAXON_LENGTH) + CENTIMETERS_SUFFIX,

                                      // taxon group name
                                      taxonGroups.get(taxonObj.getInt(JsonConst.TAXON_GROUP_ID)),

                                      // taxon level name
                                      taxonLevels.get(taxonObj.getInt(JsonConst.TAXON_LEVEL_ID))
                                  );

        // habitat data
        IJsonObject habitatObject = taxonObj.getJsonObject(JsonConst.HABITAT_INDEX);

        if (habitatObject != null) {
            // get names of all habitats occupied by the taxon
            habitatObject.forEach((Map.Entry<String, Object> entry) -> {
                Object value = entry.getValue();

                if (value instanceof Double)
                {
                    double d = (Double)
                    value;

                    if (d > 0.0 && d <= 1.0)
                        taxonProfile.add(entry.getKey());
                }
            }
                                 );
        }

        return taxonProfile;
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
