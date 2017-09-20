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

import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauEezRegion;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauFaoRfb;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauReconstructionDocument;
import de.gerdiproject.harvest.seaaroundus.utils.DataCiteUtils;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

import java.util.List;

/**
 * This harvester harvests all EEZs of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/eez/
 *
 * @author Robin Weiss
 */
public class EezRegionHarvester extends GenericRegionHarvester<SauEezRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * EEZ parameters.
     */
    public EezRegionHarvester()
    {
        super(RegionConstants.EEZ_PARAMS);
    }


    @Override
    protected void enrichWebLinksAndFiles(List<WebLink> weblinks, List<File> files, String apiUrl, SauEezRegion regionObject)
    {
        super.enrichWebLinksAndFiles(weblinks, files, apiUrl, regionObject);

        // External Links
        enrichWebLinksByExternalLinks(weblinks, regionObject);

        // Fishery Subsidies
        enrichWebLinksAndFilesByFisheriesSubsidies(weblinks, files, regionObject);

        // Internal Fishing Access
        enrichWebLinksAndFilesByFishingAccess(weblinks, files, apiUrl, regionObject);
    }


    /**
     * Adds external {@linkplain WebLink}s to a harvested document.
     *
     * @param weblinks the weblinks list of the document
     * @param files the file list of the document
     * @param regionObject the region object source
     */
    private void enrichWebLinksByExternalLinks(List<WebLink> weblinks, SauEezRegion regionObject)
    {
        String countryName = regionObject.getCountryName();

        // Ocean Health Index
        if (regionObject.getOhiLink() != null) {
            WebLink ohiLink = new WebLink(regionObject.getOhiLink());
            ohiLink.setName(DataCiteConstants.OCEAN_HEALTH_INDEX_LABEL_PREFIX + countryName);
            ohiLink.setType(WebLinkType.Related);
            weblinks.add(ohiLink);
        }

        // Global Slavery Index
        if (regionObject.getGsiLink() != null) {
            WebLink gsiLink = new WebLink(regionObject.getGsiLink());
            gsiLink.setName(DataCiteConstants.GLOBAL_SLAVERY_INDEX_LABEL_PREFIX + countryName);
            gsiLink.setType(WebLinkType.Related);
            weblinks.add(gsiLink);
        }

        // FAO Profile
        if (regionObject.getFaoProfileUrl() != null) {
            WebLink gsiLink = new WebLink(regionObject.getFaoProfileUrl());
            gsiLink.setName(DataCiteConstants.FAO_COUNTRY_PROFILE_LINK_NAME);
            gsiLink.setType(WebLinkType.Related);
            weblinks.add(gsiLink);
        }

        // FAO RFB
        if (regionObject.getFaoRfb() != null) {
            regionObject.getFaoRfb().forEach((SauFaoRfb rfb) -> {
                WebLink rfbLink = new WebLink(rfb.getUrl());
                rfbLink.setName(rfb.getName());
                rfbLink.setType(WebLinkType.Related);
                weblinks.add(rfbLink);
            });
        }

        // Reconstruction Documents
        if (regionObject.getReconstructionDocuments() != null) {
            regionObject.getReconstructionDocuments().forEach((SauReconstructionDocument rd) -> {
                WebLink rdLink = new WebLink(rd.getUrl());
                rdLink.setName(rd.getName());
                rdLink.setType(WebLinkType.Related);
                weblinks.add(rdLink);
            });
        }
    }


    /**
     * Adds Fisheries Subsidies {@linkplain File}s and {@linkplain WebLink}s to a harvested document.
     *
     * @param weblinks the weblinks list of the document
     * @param files the file list of the document
     * @param regionObject the region object source
     */
    private void enrichWebLinksAndFilesByFisheriesSubsidies(List<WebLink> weblinks, List<File> files, SauEezRegion regionObject)
    {
        int geoEntityId = regionObject.getGeoEntityId();
        String fisherySubsidiesLabel = DataCiteConstants.FISHERIES_SUBSIDIES_LABEL_PREFIX + regionObject.getCountryName();

        WebLink fisherySubsidiesLink = new WebLink(DataCiteConstants.FISHERIES_SUBSIDIES_VIEW_URL_PREFIX + geoEntityId);
        fisherySubsidiesLink.setName(fisherySubsidiesLabel);
        fisherySubsidiesLink.setType(WebLinkType.Related);
        weblinks.add(fisherySubsidiesLink);

        File fisherySubsidiesFile = new File(
            DataCiteUtils.instance().getRegionEntryUrl(DataCiteConstants.FISHERIES_SUBSIDIES_REGION_NAME, geoEntityId) + DataCiteConstants.FISHERIES_SUBSIDIES_DOWNLOAD_URL_SUFFIX,
            fisherySubsidiesLabel);
        fisherySubsidiesFile.setType(DataCiteConstants.JSON_FORMAT);
        files.add(fisherySubsidiesFile);
    }


    /**
     * Adds Fishing Access {@linkplain File}s and {@linkplain WebLink}s to a harvested document.
     *
     * @param weblinks the weblinks list of the document
     * @param files the file list of the document
     * @param apiUrl a download URL prefix
     * @param regionObject the region object source
     */
    private void enrichWebLinksAndFilesByFishingAccess(List<WebLink> weblinks, List<File> files, String apiUrl, SauEezRegion regionObject)
    {
        String fishingAccessLabel = DataCiteConstants.INTERNAL_FISHING_ACCESS_LABEL_PREFIX + regionObject.getCountryName();

        WebLink fishingAccessLink = new WebLink(getViewUrl(regionObject.getId()) + DataCiteConstants.INTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX);
        fishingAccessLink.setName(fishingAccessLabel);
        fishingAccessLink.setType(WebLinkType.Related);
        weblinks.add(fishingAccessLink);

        File fishingAccessFile = new File(apiUrl + DataCiteConstants.INTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX, fishingAccessLabel);
        fishingAccessFile.setType(DataCiteConstants.JSON_FORMAT);
        files.add(fishingAccessFile);
    }
}
