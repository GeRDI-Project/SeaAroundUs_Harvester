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

import de.gerdiproject.harvest.IDocument;
import de.gerdiproject.harvest.harvester.AbstractListHarvester;
import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntityReduced;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntityRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.DataCiteFactory;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

/**
 * This harvester harvests all Fishing Entities of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/fishing-entity/
 *
 * @author Robin Weiss
 */
public class FishingEntityRegionHarvester extends AbstractListHarvester<SauFishingEntityReduced>
{
    private String version;
    private final static Type FISHING_ENTITY_RESPONSE_TYPE = new TypeToken<GenericResponse<SauFishingEntityRegion>>() {} .getType();


    /**
     * Simple constructor that initializes the super class with
     * Fishing Entity parameters.
     */
    public FishingEntityRegionHarvester()
    {
        super(1);
    }

    @Override
    protected Collection<SauFishingEntityReduced> loadEntries()
    {
        // request all countries
        String apiUrl = DataCiteFactory.instance().getAllRegionsUrl(RegionConstants.REGION_FISHING_ENTITY.urlName);
        TypeToken<GenericResponse<List<SauFishingEntityReduced>>> typeToken = new TypeToken<GenericResponse<List<SauFishingEntityReduced>>>() {};
        GenericResponse<List<SauFishingEntityReduced>> allFishingEntities = httpRequester.getObjectFromUrl(apiUrl, typeToken.getType());

        // get version from metadata
        version = allFishingEntities.getMetadata().getVersion();

        // return feature array
        return allFishingEntities.getData();
    }


    @Override
    protected List<IDocument> harvestEntry(SauFishingEntityReduced entry)
    {
        int regionId = entry.getId();
        String regionName = entry.getTitle();
        String regionApiName = RegionConstants.REGION_FISHING_ENTITY.urlName;
        String apiUrl = DataCiteFactory.instance().getRegionEntryUrl(regionApiName, regionId);

        DataCiteJson document = new DataCiteJson();
        document.setVersion(version);
        document.setPublisher(DataCiteConstants.PROVIDER);
        document.setFormats(DataCiteConstants.JSON_FORMATS);
        document.setCreators(DataCiteConstants.SAU_CREATORS);
        document.setRightsList(DataCiteConstants.RIGHTS_LIST);
        document.setSources(DataCiteFactory.instance().createSource(apiUrl));
        document.setTitles(createTitles(regionName));
        document.setFiles(createFiles(regionId, regionName));

        // add region details
        GenericResponse<SauFishingEntityRegion> regionObjectResponse = httpRequester.getObjectFromUrl(apiUrl, FISHING_ENTITY_RESPONSE_TYPE);
        SauFishingEntityRegion regionObject = regionObjectResponse.getData();

        document.setWebLinks(createWebLinks(regionObject));
        document.setGeoLocations(DataCiteFactory.instance().createBasicGeoLocations(
                                     regionObject.getGeojson(),
                                     regionName
                                 ));

        return Arrays.asList(document);
    }


    /**
     * Creates a list of {@linkplain Title}s for the region.
     *
     * @param regionName the name of the fishing-entity region
     *
     * @return a list of {@linkplain Title}s for the region
     */
    private List<Title> createTitles(String regionName)
    {
        String titleString = String.format(
                                 DataCiteConstants.GENERIC_LABEL,
                                 RegionConstants.REGION_FISHING_ENTITY.displayName,
                                 regionName);

        List<Title> titles = new LinkedList<>();
        titles.add(new Title(titleString));

        return titles;
    }


    /**
     * Creates a list of (related) {@linkplain WebLink}s of a fishing-entity region.
     *
     * @param regionObject an object describing the fishing-entity
     *
     * @return a list of (related) {@linkplain WebLink}s of a fishing-entity region
     */
    private List<WebLink> createWebLinks(SauFishingEntityRegion regionObject)
    {
        int regionId = regionObject.getId();
        int countryId = regionObject.getCountryId();
        String countryName = regionObject.getTitle();
        String regionApiName = RegionConstants.REGION_FISHING_ENTITY.urlName;

        // View URL & Logo URL
        List<WebLink> webLinks = DataCiteFactory.instance().createBasicWebLinks(regionApiName, regionId);

        // catches
        List<WebLink> catchLinks = DataCiteFactory.instance().createCatchLinks(RegionConstants.FISHING_ENTITY_PARAMS, regionId, countryName);
        webLinks.addAll(catchLinks);

        // Country Profile
        WebLink countryLink = new WebLink(String.format(UrlConstants.VIEW_URL, RegionConstants.COUNTRY_API_NAME, countryId));
        countryLink.setName(String.format(DataCiteConstants.COUNTRY_LABEL, countryName));
        countryLink.setType(WebLinkType.Related);
        webLinks.add(countryLink);

        // Fisheries Subsidies
        WebLink fisherySubsidiesLink = new WebLink(String.format(UrlConstants.FISHERIES_SUBSIDIES_VIEW_URL, regionObject.getGeoEntityId()));
        fisherySubsidiesLink.setName(DataCiteConstants.FISHERIES_SUBSIDIES_LABEL_PREFIX + countryName);
        fisherySubsidiesLink.setType(WebLinkType.Related);
        webLinks.add(fisherySubsidiesLink);

        // External Fishing Access
        String fishingAccessUrl = String.format(UrlConstants.VIEW_URL, regionApiName, regionId)
                                  + DataCiteConstants.EXTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX;

        WebLink fishingAccessLink = new WebLink(fishingAccessUrl);
        fishingAccessLink.setName(String.format(DataCiteConstants.EXTERNAL_FISHING_ACCESS_LABEL, countryName));
        fishingAccessLink.setType(WebLinkType.Related);
        webLinks.add(fishingAccessLink);

        // Treaties and Conventions
        String treatiesId = "" + countryId;

        if (countryId < 100)
            treatiesId = "0" + treatiesId;

        WebLink treatiesLink = new WebLink(String.format(UrlConstants.TREATIES_VIEW_URL, treatiesId));
        treatiesLink.setType(WebLinkType.Related);
        treatiesLink.setName(String.format(DataCiteConstants.TREATIES_LABEL_SHORT, countryName));
        webLinks.add(treatiesLink);

        // Catch Allocations
        WebLink catchAllocationsLink = new WebLink(String.format(UrlConstants.CATCH_ALLOCATIONS_URL, regionId));
        catchAllocationsLink.setType(WebLinkType.ViewURL);
        catchAllocationsLink.setName(String.format(DataCiteConstants.CATCH_ALLOCATIONS_LABEL, countryName));
        webLinks.add(catchAllocationsLink);

        return webLinks;
    }


    /**
     * Creates a list of {@linkplain File}s of a fishing-entity region.
     *
     * @param regionId a unique identifier of the fishing-entity
     * @param regionName the human readable name of the fishing-entity
     *
     * @return a list of {@linkplain File}s of a fishing-entity region
     */
    private List<File> createFiles(int regionId, String regionName)
    {
        List<File> files = DataCiteFactory.instance().createCatchFiles(RegionConstants.FISHING_ENTITY_PARAMS, regionId, regionName);
        return files;
    }
}
