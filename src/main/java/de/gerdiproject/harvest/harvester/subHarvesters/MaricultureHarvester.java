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
import de.gerdiproject.json.utils.JsonHelper;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author row
 */
public class MaricultureHarvester extends AbstractJsonArrayHarvester
{
    // URLs
    private static final String LABEL_PREFIX = "Mariculture Production in ";
    private static final String VIEW_URL = "http://www.seaaroundus.org/data/#/mariculture/%d?chart=mariculture-chart";
    private final static String API_URL_SUFFIX = "/mariculture/";
    private final static String DOWNLOAD_SUBREGION_URL_SUFFIX = "%s/%d?limit=20&sub_unit_id=%d" + SeaAroundUsConst.CSV_FORM;
    private final static String DOWNLOAD_ALL_URL_SUFFIX = "%s/%d?limit=20" + SeaAroundUsConst.CSV_FORM;

    private String apiUrl;
    //"http://api.seaaroundus.org/api/v1/mariculture/commercialgroup/57?format=csv&limit=10&sciname=&sub_unit_id=413"

    /**
     * @param harvestedDocuments the list to which harvested documents are added
     */
    public MaricultureHarvester()
    {
        super(1);
    }


    @Override
    public void setProperty(String key, String value)
    {
        if (SeaAroundUsConst.PROPERTY_URL.equals(key))
            apiUrl = value + API_URL_SUFFIX;
    }


    @Override
    protected IJsonArray getJsonArray()
    {
        IJsonObject maricultureData = httpRequester.getJsonObjectFromUrl(apiUrl);
        IJsonArray maricultureRegions = maricultureData.getJsonArray(JsonConst.FEATURES);

        return maricultureRegions;
    }


    /**
     * Harvests a mariculture entry, generating a single document.
     *
     * @param entry a mariculture overview JSON object entry
     * @return the harvested documents
     */
    @Override
    protected List<IJsonObject> harvestJsonArrayEntry(IJsonObject mariculture)
    {
        // get properties
        IJsonObject properties = mariculture.getJsonObject(JsonConst.PROPERTIES);
        int regionId = properties.getInt(JsonConst.REGION_ID, -1);
        IJsonArray subRegions = httpRequester.getJsonArrayFromUrl(apiUrl + regionId);

        // set up view url
        String viewUrl = String.format(VIEW_URL, regionId);


        // set up download urls
        IJsonArray downloadUrls = getDownloadUrls(regionId, subRegions);

        // create search tags
        IJsonArray tags = createSearchTags(subRegions);

        // assemble label
        String label = LABEL_PREFIX + properties.getString(JsonConst.TITLE, null);

        // get shape geo coordinates
        IJsonArray geoData = getGeoData(mariculture);

        // create document
        IJsonObject document = searchIndexFactory.createSearchableDocument(label, null, viewUrl, downloadUrls, SeaAroundUsConst.LOGO_URL, null, geoData, null, tags);

        // return list of documents
        List<IJsonObject> documentList = new LinkedList<>();
        documentList.add(document);
        return documentList;
    }

    private IJsonArray getDownloadUrls(int regionId, IJsonArray subRegions)
    {
        IJsonArray urls = jsonBuilder.createArray();

        for (Entry dimension : SeaAroundUsConst.DIMENSIONS_MARICULTURE) {
            // add download for all sub regions
            urls.add(apiUrl + String.format(DOWNLOAD_ALL_URL_SUFFIX, dimension.urlName, regionId));

            // add downloads filtered by sub region
            subRegions.forEach((Object o) -> {
                int subRegionId = ((IJsonObject) o).getInt(JsonConst.REGION_ID);
                urls.add(apiUrl + String.format(DOWNLOAD_SUBREGION_URL_SUFFIX, dimension.urlName, regionId, subRegionId));
            });
        }


        return urls;
    }


    private IJsonArray createSearchTags(IJsonArray subRegions)
    {
        List<String> titles = JsonHelper.arrayToStringList(subRegions, JsonConst.TITLE);
        return jsonBuilder.createArrayFromLists(titles);
    }


    protected IJsonArray getGeoData(IJsonObject entry)
    {
        IJsonObject geojson = entry.getJsonObject(JsonConst.GEOJSON);
        IJsonObject geoPoint = entry.getJsonObject(JsonConst.POINT_GEOJSON);

        // check if the geojson exists and add it to an array, if so
        if (geojson != null || geoPoint != null) {
            IJsonArray geoObjects = jsonBuilder.createArray();
            geoObjects.addNotNull(geojson);
            geoObjects.addNotNull(geoPoint);
            return geoObjects;
        }

        return null;
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
