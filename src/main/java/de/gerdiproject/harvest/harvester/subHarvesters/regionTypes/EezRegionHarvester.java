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
public class EezRegionHarvester extends GenericRegionHarvester
{
    private static final String INTERNAL_FISHING_ACCESS_LABEL_PREFIX = "Foreign fishing access in the waters of ";
    private static final String INTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX = "/internal-fishing-access";
    private static final String INTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX = "/access-agreement-internal/";

    private static final String FISHERIES_SUBSIDIES_LABEL_PREFIX = "Fisheries Subsidies in ";
    private static final String FISHERIES_SUBSIDIES_VIEW_URL_PREFIX = "http://www.seaaroundus.org/data/#/subsidy/";
    private static final String FISHERIES_SUBSIDIES_DOWNLOAD_URL_SUFFIX = "/geo-entity/%d/subsidies/";

    private static final String OCEAN_HEALTH_INDEX_LABEL_PREFIX = "Ocean Health Index of ";

    private String fisheriesSubsidiesApiUrl;


    public EezRegionHarvester()
    {
        super(SeaAroundUsConst.REGION_EEZ,
              SeaAroundUsConst.DIMENSIONS_EEZ,
              SeaAroundUsConst.GENERIC_URL_VO,
              6 + SeaAroundUsConst.DIMENSIONS_EEZ.size() * MEASURES.length
             );
    }


    @Override
    public void setProperty(String key, String value)
    {
        if (SeaAroundUsConst.PROPERTY_URL.equals(key)) {
            downloadUrlPrefix = value + String.format(SeaAroundUsConst.REGION_IDS_URL, regionType.urlName);
            fisheriesSubsidiesApiUrl = value + FISHERIES_SUBSIDIES_DOWNLOAD_URL_SUFFIX;
        }
    }


    @Override
    protected List<IJsonObject> createDocuments(int regionId, IJsonObject regionObject, String regionName, IJsonArray geoData, List<String> defaultTags)
    {
        List<IJsonObject> documentList = super.createDocuments(regionId, regionObject, regionName, geoData, defaultTags);

        // Internal Fishing Access
        documentList.add(createInternalFishingAccessDocument(regionId, regionName, geoData, defaultTags));

        // Subsidies
        documentList.add(createFisheriesSubsidiesDocument(regionObject, regionName, geoData, defaultTags));

        // Ocean Health Index
        documentList.add(createOceanHealthIndexDocument(regionObject, regionName, geoData, defaultTags));

        return documentList;
    }


    private IJsonObject createFisheriesSubsidiesDocument(IJsonObject regionObject, String regionName, IJsonArray geoData, List<String> defaultTags)
    {
        int geoEntityId = regionObject.getInt(JsonConst.GEO_ENTITY_ID);

        String label = FISHERIES_SUBSIDIES_LABEL_PREFIX + regionName;
        String viewUrl = FISHERIES_SUBSIDIES_VIEW_URL_PREFIX + geoEntityId;
        String apiUrl = String.format(fisheriesSubsidiesApiUrl, geoEntityId);
        IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects(apiUrl);
        IJsonArray searchTags = jsonBuilder.createArrayFromLists(defaultTags);

        return searchIndexFactory.createSearchableDocument(
                   label, null, viewUrl, downloadUrls, SeaAroundUsConst.LOGO_URL, null, geoData, null, searchTags
               );
    }


    private IJsonObject createOceanHealthIndexDocument(IJsonObject regionObject, String regionName, IJsonArray geoData, List<String> defaultTags)
    {
        String viewUrl = regionObject.getString(JsonConst.OHI_LINK, null);

        if (viewUrl != null) {
            String label = OCEAN_HEALTH_INDEX_LABEL_PREFIX + regionName;
            IJsonArray searchTags = jsonBuilder.createArrayFromLists(defaultTags);

            return searchIndexFactory.createSearchableDocument(
                       label, null, viewUrl, null, SeaAroundUsConst.LOGO_URL, null, geoData, null, searchTags
                   );
        }

        return null;
    }


    private IJsonObject createInternalFishingAccessDocument(int regionId, String regionName, IJsonArray geoData, List<String> defaultTags)
    {
        String label = INTERNAL_FISHING_ACCESS_LABEL_PREFIX + regionName;
        String viewUrl = viewUrlPrefix + regionId + INTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX;
        String apiUrl = downloadUrlPrefix + regionId + INTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX;
        IJsonArray searchTags = jsonBuilder.createArrayFromLists(defaultTags);
        IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects(apiUrl);

        return searchIndexFactory.createSearchableDocument(
                   label, null, viewUrl, downloadUrls, SeaAroundUsConst.LOGO_URL, null, geoData, null, searchTags
               );
    }


    @Override
    protected List<String> getDefaultSearchTags(IJsonObject regionObject)
    {
        final List<String> tags = super.getDefaultSearchTags(regionObject);

        IJsonArray faoRfb = regionObject.getJsonArray(JsonConst.FAO_RFB);

        if (faoRfb != null) {
            for (Object attribute : faoRfb) {
                IJsonObject faoObj = (IJsonObject) attribute;

                // get name
                String tagName = faoObj.getString(JsonConst.NAME);

                if (tagName != null)
                    tags.add(tagName);

                // get acronym
                String acronym = faoObj.getString(JsonConst.ACRONYM);

                if (acronym != null)
                    tags.add(acronym);
            }
        }

        return tags;
    }

}
