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

import de.gerdiproject.harvest.seaaroundus.constants.DimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.Entry;
import de.gerdiproject.harvest.seaaroundus.constants.JsonConst;
import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.IJsonObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Robin Weiss
 */
public class FishingEntityRegionHarvester extends GenericRegionHarvester
{
    private final static String FUNCTIONAL_GROUP_SEPERATOR = "; ";

    private final static String EXTERNAL_FISHING_ACCESS_LABEL = "'s foreign fishing access agreements by EEZ";
    private final static String EXTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX = "/external-fishing-access";
    private final static String EXTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX = "/access-agreement-external/";


    public FishingEntityRegionHarvester()
    {
        super(RegionConstants.REGION_FISHING_ENTITY,
              DimensionConstants.DIMENSIONS_FAO,
              UrlConstants.GENERIC_URL_VO,
              1 + DimensionConstants.DIMENSIONS_FAO.size() * MEASURES.length);
    }


    @Override
    protected IJsonArray getJsonArray()
    {
        return httpRequester.getJsonArrayFromUrl(downloadUrlPrefix);
    }


    @Override
    protected List<IJsonObject> createDocuments(int regionId, IJsonObject regionObject, String regionName, IJsonArray geoData, List<String> defaultTags)
    {
        final List<IJsonObject> documentList = new ArrayList<>(numberOfDocumentsPerEntry);

        // Catches and Values
        for (Entry measure : MEASURES) {
            for (Entry dimension : dimensions)
                documentList.add(createCatchesByDimensionDocument(regionId, regionName, dimension, measure, geoData, defaultTags));
        }

        // External Fishing Access
        documentList.add(createExternalFishingAccessDocument(regionId, regionName, geoData, defaultTags));

        return documentList;
    }


    private IJsonObject createExternalFishingAccessDocument(int regionId, String regionName, IJsonArray geoData, List<String> defaultTags)
    {
        IJsonObject document = null;

        String apiUrl = downloadUrlPrefix + regionId + EXTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX;
        IJsonArray accessAgreements = httpRequester.getJsonArrayFromUrl(apiUrl);

        // skip this document if no data is available
        if (accessAgreements != null) {
            // get years
            IJsonArray years = getAccessAgreementStartYears(accessAgreements);

            // get and combine search tags
            List<String> aaSearchTags = getAccessAgreementSearchTags(accessAgreements);
            IJsonArray searchTags = jsonBuilder.createArrayFromLists(defaultTags, aaSearchTags);

            String label = regionName + EXTERNAL_FISHING_ACCESS_LABEL;
            String viewUrl = viewUrlPrefix + regionId + EXTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX;
            IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects(apiUrl);

            document = searchIndexFactory.createSearchableDocument(
                           label, null, viewUrl, downloadUrls, UrlConstants.LOGO_URL, null, geoData, years, searchTags
                       );
        }

        return document;
    }


    private IJsonArray getAccessAgreementStartYears(IJsonArray accessAgreements)
    {
        IJsonArray years = jsonBuilder.createArray();
        accessAgreements.forEach((Object a) -> ((IJsonObject) a).getInt(JsonConst.START_YEAR));
        return years;
    }


    private List<String> getAccessAgreementSearchTags(IJsonArray accessAgreements)
    {
        List<String> aaTags = new LinkedList<>();

        for (Object attribute : accessAgreements) {
            IJsonObject obj = (IJsonObject) attribute;
            String fishingAccess = obj.getString(JsonConst.FISHING_ACCESS, null);

            if (fishingAccess != null)
                aaTags.add(fishingAccess);

            String title = obj.getString(JsonConst.TITLE, null);

            if (title != null)
                aaTags.add(title);

            String eez = obj.getString(JsonConst.EEZ_NAME, null);

            if (eez != null)
                aaTags.add(eez);

            String functionalGroupDetails = obj.getString(JsonConst.FUNCTIONAL_GROUP_DETAILS, null);

            if (functionalGroupDetails != null) {
                String[] functionalGroups = functionalGroupDetails.split(FUNCTIONAL_GROUP_SEPERATOR);

                for (String fg : functionalGroups)
                    aaTags.add(fg);
            }
        }

        return aaTags;
    }


    @Override
    protected int getRegionId(IJsonObject region)
    {
        return region.getInt(JsonConst.ID);
    }
}
