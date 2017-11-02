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

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauEezRegion;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauFaoRfb;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauReconstructionDocument;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteFactory;
import de.gerdiproject.json.datacite.File;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

import java.util.List;

import com.google.gson.reflect.TypeToken;

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
        super(new TypeToken<GenericResponse<SauEezRegion>>() {}, SeaAroundUsRegionConstants.EEZ_PARAMS);
    }


    @Override
    protected void enrichWebLinks(List<WebLink> weblinks, SauEezRegion regionObject)
    {
        super.enrichWebLinks(weblinks, regionObject);

        String countryName = regionObject.getCountryName();

        // Ocean Health Index
        if (regionObject.getOhiLink() != null) {
            WebLink ohiLink = new WebLink(regionObject.getOhiLink());
            ohiLink.setName(SeaAroundUsDataCiteConstants.OCEAN_HEALTH_INDEX_LABEL_PREFIX + countryName);
            ohiLink.setType(WebLinkType.Related);
            weblinks.add(ohiLink);
        }

        // Global Slavery Index
        if (regionObject.getGsiLink() != null) {
            WebLink gsiLink = new WebLink(regionObject.getGsiLink());
            gsiLink.setName(SeaAroundUsDataCiteConstants.GLOBAL_SLAVERY_INDEX_LABEL_PREFIX + countryName);
            gsiLink.setType(WebLinkType.Related);
            weblinks.add(gsiLink);
        }

        // FAO Profile
        if (regionObject.getFaoProfileUrl() != null) {
            WebLink gsiLink = new WebLink(regionObject.getFaoProfileUrl());
            gsiLink.setName(SeaAroundUsDataCiteConstants.FAO_COUNTRY_PROFILE_LINK_NAME);
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

        // Fisheries Subsidies
        WebLink fisherySubsidiesLink = new WebLink(String.format(SeaAroundUsUrlConstants.FISHERIES_SUBSIDIES_VIEW_URL, regionObject.getGeoEntityId()));
        fisherySubsidiesLink.setName(SeaAroundUsDataCiteConstants.FISHERIES_SUBSIDIES_LABEL_PREFIX + regionObject.getCountryName());
        fisherySubsidiesLink.setType(WebLinkType.Related);
        weblinks.add(fisherySubsidiesLink);

        // Internal Fishing Access
        WebLink fishingAccessLink = new WebLink(getViewUrl(regionObject.getId()) + SeaAroundUsDataCiteConstants.INTERNAL_FISHING_ACCESS_VIEW_URL_SUFFIX);
        fishingAccessLink.setName(SeaAroundUsDataCiteConstants.INTERNAL_FISHING_ACCESS_LABEL_PREFIX + regionObject.getCountryName());
        fishingAccessLink.setType(WebLinkType.Related);
        weblinks.add(fishingAccessLink);
    }


    @Override
    protected void enrichFiles(List<File> files, SauEezRegion regionObject)
    {
        super.enrichFiles(files, regionObject);

        // Fisheries Subsidies
        File fisherySubsidiesFile = new File(
            SeaAroundUsDataCiteFactory.instance().getRegionEntryUrl(SeaAroundUsDataCiteConstants.FISHERIES_SUBSIDIES_REGION_NAME, regionObject.getGeoEntityId()) + SeaAroundUsDataCiteConstants.FISHERIES_SUBSIDIES_DOWNLOAD_URL_SUFFIX,
            SeaAroundUsDataCiteConstants.FISHERIES_SUBSIDIES_LABEL_PREFIX + regionObject.getCountryName());
        fisherySubsidiesFile.setType(SeaAroundUsDataCiteConstants.JSON_FORMAT);
        files.add(fisherySubsidiesFile);

        // Internal Fishing Access
        String faUrl = SeaAroundUsDataCiteFactory.instance().getRegionEntryUrl(params.getRegionType().urlName, regionObject.getId())
                       + SeaAroundUsDataCiteConstants.INTERNAL_FISHING_ACCESS_DOWNLOAD_URL_SUFFIX;

        File fishingAccessFile = new File(faUrl, SeaAroundUsDataCiteConstants.INTERNAL_FISHING_ACCESS_LABEL_PREFIX + regionObject.getCountryName());
        fishingAccessFile.setType(SeaAroundUsDataCiteConstants.JSON_FORMAT);
        files.add(fishingAccessFile);
    }
}
