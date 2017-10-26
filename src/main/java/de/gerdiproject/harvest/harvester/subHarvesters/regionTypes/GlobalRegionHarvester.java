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
import de.gerdiproject.harvest.seaaroundus.constants.RegionParameters;
import de.gerdiproject.harvest.seaaroundus.constants.SubRegionVO;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobalResponse;
import de.gerdiproject.harvest.seaaroundus.utils.DataCiteFactory;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.extension.ResearchData;
import de.gerdiproject.json.datacite.extension.WebLink;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This harvester harvests all sub-regions of the Global Seas of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/global/1
 *
 * @author Robin Weiss
 */
public class GlobalRegionHarvester extends AbstractListHarvester<SauGlobal>
{
    private final SubRegionVO subRegion;

    private String version;
    private String apiUrl;


    /**
     * Simple constructor.
     */
    public GlobalRegionHarvester(SubRegionVO subRegion)
    {
        super(1);

        this.subRegion = subRegion;

        if (subRegion.getId() != 0)
            this.name += subRegion.getLabelSuffix();
    }


    @Override
    protected Collection<SauGlobal> loadEntries()
    {
        // request all countries
        apiUrl = createApiUrl();
        SauGlobalResponse globalResponse = httpRequester.getObjectFromUrl(apiUrl, SauGlobalResponse.class);

        // get version from metadata
        version = globalResponse.getMetadata().getVersion();

        // return feature array
        return Arrays.asList(globalResponse.getData());
    }


    @Override
    protected List<IDocument> harvestEntry(SauGlobal entry)
    {
        String subRegionNameSuffix = subRegion.getLabelSuffix();
        int subRegionId = subRegion.getId();

        DataCiteJson document = new DataCiteJson();
        document.setVersion(version);
        document.setPublisher(DataCiteConstants.PROVIDER);
        document.setFormats(DataCiteConstants.CSV_FORMATS);
        document.setCreators(DataCiteConstants.SAU_CREATORS);
        document.setRightsList(DataCiteConstants.RIGHTS_LIST);
        document.setSources(DataCiteFactory.instance().createSource(apiUrl));
        document.setTitles(createTitles(subRegionNameSuffix));
        document.setSubjects(createSubjects(entry.getMetrics()));
        document.setWebLinks(createWebLinks(subRegionId, subRegionNameSuffix));
        document.setFiles(createFiles(subRegionId, subRegionNameSuffix));

        return Arrays.asList(document);
    }

    private String createApiUrl()
    {
        String url = DataCiteFactory.instance().getRegionEntryUrl(DataCiteConstants.GLOBAL_REGION_NAME, 1);

        // add sub-region suffix, if necessary
        int subRegionId = subRegion.getId();

        if (subRegionId != 0)
            url += String.format(UrlConstants.GLOBAL_SUB_REGION_API_SUFFIX, subRegionId);

        return url;
    }

    private List<Title> createTitles(String regionName)
    {
        String titleString = (DataCiteConstants.GLOBAL_OCEAN_TITLE + regionName).trim();
        Title mainTitle = new Title(titleString);
        return Arrays.asList(mainTitle);
    }


    private List<Subject> createSubjects(List<Metric> metrics)
    {
        List<Subject> subjects = new LinkedList<>();
        metrics.forEach((Metric m) -> {
            if (m.getValue() != 0.0)
                subjects.add(new Subject(m.getTitle()));
        });

        return subjects;
    }


    private List<ResearchData> createFiles(int subRegionId, String regionName)
    {
        RegionParameters params;

        // subregion 0 is a special case as it uses differen URLs
        if (subRegionId == 0) {
            subRegionId = 1;
            params = RegionConstants.GLOBAL_PARAMS;
        } else
            params = RegionConstants.GLOBAL_SUBREGION_PARAMS;

        List<ResearchData> files;
        files = DataCiteFactory.instance().createCatchFiles(params, subRegionId, regionName);
        files.add(DataCiteFactory.instance().createPrimaryProductionFile(params, subRegionId, regionName));
        files.add(DataCiteFactory.instance().createStockStatusFile(params, subRegionId, regionName));

        // marine trophic index
        ResearchData marineTrophicIndex = DataCiteFactory.instance().createMarineTrophicIndexFile(params, subRegionId, DataCiteConstants.GLOBAL_MARINE_TROPHIC_INDEX_LABEL);
        marineTrophicIndex.setLabel((marineTrophicIndex.getLabel() + params.getRegionType().displayName).trim());
        files.add(marineTrophicIndex);

        return files;
    }


    private List<WebLink> createWebLinks(int subRegionId, String regionName)
    {
        RegionParameters params = RegionConstants.GLOBAL_SUBREGION_PARAMS;

        List<WebLink> links;
        links = DataCiteFactory.instance().createCatchLinks(params, subRegionId, regionName);
        links.add(DataCiteConstants.LOGO_LINK);
        links.add(DataCiteFactory.instance().createPrimaryProductionLink(params, subRegionId, regionName));
        links.add(DataCiteFactory.instance().createStockStatusLink(params, subRegionId, regionName));

        // marine trophic index
        WebLink marineTrophicIndex = DataCiteFactory.instance().createMarineTrophicIndexLink(params, subRegionId, DataCiteConstants.GLOBAL_MARINE_TROPHIC_INDEX_LABEL);
        marineTrophicIndex.setName((marineTrophicIndex.getName() + params.getRegionType().displayName).trim());
        links.add(marineTrophicIndex);

        return links;
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
