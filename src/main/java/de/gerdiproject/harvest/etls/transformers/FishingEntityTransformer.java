/*
 *  Copyright © 2018 Robin Weiss (http://www.gerdi-project.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
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
package de.gerdiproject.harvest.etls.transformers;

import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.extension.generic.ResearchData;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;

/**
 * A {@linkplain AbstractIteratorTransformer} implementation for transforming {@linkplain SauFishingEntity}
 * to {@linkplain DataCiteJson} objects.
 *
 * @author Robin Weiss
 */
public class FishingEntityTransformer extends AbstractIteratorTransformer<GenericResponse<SauFishingEntity>, DataCiteJson>
{
    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        // nothing to retrieve from the ETL
    }


    @Override
    protected DataCiteJson transformElement(final GenericResponse<SauFishingEntity> response) throws TransformerException
    {
        final SauFishingEntity entry = response.getData();
        final int regionId = entry.getId();
        final String regionName = entry.getTitle();
        final String regionApiName = SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS.getRegionType().getUrlName();
        final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(regionApiName, regionId);

        final DataCiteJson document = new DataCiteJson(apiUrl);
        document.setVersion(response.getMetadata().getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.addFormats(SeaAroundUsDataCiteConstants.JSON_FORMATS);
        document.addCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.addRights(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.addTitles(createTitles(regionName));
        document.addResearchData(createResearchData(regionId, regionName));

        // add region details
        document.addWebLinks(createWebLinks(entry));
        document.addGeoLocations(
            SeaAroundUsDataCiteUtils.createBasicGeoLocations(
                entry.getGeojson(),
                regionName));

        return document;
    }


    /**
     * Creates a list of {@linkplain Title}s for the region.
     *
     * @param regionName the name of the fishing-entity region
     *
     * @return a list of {@linkplain Title}s for the region
     */
    private List<Title> createTitles(final String regionName)
    {
        final String titleString = String.format(
                                       SeaAroundUsDataCiteConstants.GENERIC_LABEL,
                                       SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS.getRegionType().getDisplayName(),
                                       regionName);

        final List<Title> titles = new LinkedList<>();
        titles.add(new Title(titleString));

        return titles;
    }


    /**
     * Creates a list of (related) {@linkplain WebLink}s of a fishing-entity
     * region.
     *
     * @param regionObject an object describing the fishing-entity
     *
     * @return a list of (related) {@linkplain WebLink}s of a fishing-entity
     *         region
     */
    private List<WebLink> createWebLinks(final SauFishingEntity regionObject)
    {
        final int regionId = regionObject.getId();
        final int countryId = regionObject.getCountryId();
        final String countryName = regionObject.getTitle();
        final String regionApiName = SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS.getRegionType().getUrlName();

        // View URL & Logo URL
        final List<WebLink> webLinks = SeaAroundUsDataCiteUtils.createBasicWebLinks(regionApiName, regionId);

        // catches
        final List<WebLink> catchLinks = SeaAroundUsDataCiteUtils.createCatchLinks(
                                             SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS,
                                             regionId,
                                             countryName);
        webLinks.addAll(catchLinks);

        // Country Profile
        final WebLink countryLink = new WebLink(
            String.format(
                SeaAroundUsUrlConstants.VIEW_URL,
                SeaAroundUsRegionConstants.COUNTRY_API_NAME,
                countryId));
        countryLink.setName(String.format(SeaAroundUsDataCiteConstants.COUNTRY_LABEL, countryName));
        countryLink.setType(WebLinkType.Related);
        webLinks.add(countryLink);

        // Fisheries Subsidies
        final WebLink fisherySubsidiesLink = new WebLink(
            String.format(SeaAroundUsUrlConstants.FISHERIES_SUBSIDIES_VIEW_URL, regionObject.getGeoEntityId()));
        fisherySubsidiesLink.setName(SeaAroundUsDataCiteConstants.FISHERIES_SUBSIDIES_LABEL_PREFIX + countryName);
        fisherySubsidiesLink.setType(WebLinkType.Related);
        webLinks.add(fisherySubsidiesLink);

        // External Fishing Access
        final String fishingAccessUrl = String.format(SeaAroundUsUrlConstants.VIEW_URL, regionApiName, regionId)
                                        + SeaAroundUsDataCiteConstants.EXTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX;

        final WebLink fishingAccessLink = new WebLink(fishingAccessUrl);
        fishingAccessLink.setName(
            String.format(SeaAroundUsDataCiteConstants.EXTERNAL_FISHING_ACCESS_LABEL, countryName));
        fishingAccessLink.setType(WebLinkType.Related);
        webLinks.add(fishingAccessLink);

        // Treaties and Conventions
        String treatiesId = "" + countryId;

        if (countryId < 100)
            treatiesId = "0" + treatiesId;

        final WebLink treatiesLink = new WebLink(String.format(SeaAroundUsUrlConstants.TREATIES_VIEW_URL, treatiesId));
        treatiesLink.setType(WebLinkType.Related);
        treatiesLink.setName(String.format(SeaAroundUsDataCiteConstants.TREATIES_LABEL_SHORT, countryName));
        webLinks.add(treatiesLink);

        // Catch Allocations
        final WebLink catchAllocationsLink =
            new WebLink(String.format(SeaAroundUsUrlConstants.CATCH_ALLOCATIONS_URL, regionId));
        catchAllocationsLink.setType(WebLinkType.ViewURL);
        catchAllocationsLink.setName(String.format(SeaAroundUsDataCiteConstants.CATCH_ALLOCATIONS_LABEL, countryName));
        webLinks.add(catchAllocationsLink);

        return webLinks;
    }


    /**
     * Creates a list of {@linkplain ResearchData} of a fishing-entity region.
     *
     * @param regionId a unique identifier of the fishing-entity
     * @param regionName the human readable name of the fishing-entity
     *
     * @return a list of {@linkplain ResearchData} of a fishing-entity region
     */
    private List<ResearchData> createResearchData(final int regionId, final String regionName)
    {
        final List<ResearchData> files = SeaAroundUsDataCiteUtils.createCatchResearchData(
                                             SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS,
                                             regionId,
                                             regionName);
        return files;
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }
}
