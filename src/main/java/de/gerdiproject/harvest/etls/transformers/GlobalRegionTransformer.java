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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.extension.ResearchData;
import de.gerdiproject.json.datacite.extension.WebLink;


/**
 * A {@linkplain AbstractIteratorTransformer} implementation for transforming {@linkplain SauGlobal}
 * to {@linkplain DataCiteJson} objects.
 *
 * @author Robin Weiss
 */
public class GlobalRegionTransformer extends AbstractIteratorTransformer<SauGlobal, DataCiteJson>
{
    @Override
    protected DataCiteJson transformElement(SauGlobal entry) throws TransformerException
    {
        final String subRegionName = entry.getSubRegionNameSuffix();
        final int subRegionId = entry.getSubRegionId();

        final DataCiteJson document = new DataCiteJson(SauGlobal.class.getSimpleName() + subRegionId);
        document.setVersion(entry.getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.setResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.setFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);
        document.setCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.setRightsList(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.setTitles(createTitles(subRegionName));
        document.setSubjects(createSubjects(entry.getMetrics()));
        document.setWebLinks(createWebLinks(subRegionId, subRegionName));
        document.setResearchDataList(createFiles(subRegionId, subRegionName));

        return document;
    }


    /**
     * Creates global ocean document titles.
     *
     * @param regionName the name-suffix of the global ocean sub-area
     *
     * @return a list of global ocean document titles
     */
    private List<Title> createTitles(String regionName)
    {
        String titleString = (SeaAroundUsDataCiteConstants.GLOBAL_OCEAN_TITLE + regionName).trim(); // NOPMD - parentheses are needed for proper trimming
        Title mainTitle = new Title(titleString);
        return Arrays.asList(mainTitle);
    }


    /**
     * Creates global ocean document subjects.
     *
     * @param metrics global ocean sub-area metrics
     *
     * @return a list of global ocean document subjects
     */
    private List<Subject> createSubjects(List<Metric> metrics)
    {
        List<Subject> subjects = new LinkedList<>();
        metrics.forEach((Metric m) -> {
            if (m.getValue() != 0.0)
                subjects.add(new Subject(m.getTitle()));
        });

        return subjects;
    }


    /**
     * Creates global ocean document files.
     *
     * @param subRegionId the unique identifier of the global ocean sub-area
     * @param regionName the name-suffix of the global ocean sub-area
     *
     * @return a list of global ocean document files
     */
    private List<ResearchData> createFiles(int subRegionId, String regionName)
    {
        RegionParametersVO params;

        // subregion 0 is a special case as it uses differen URLs
        if (subRegionId == 0) {
            subRegionId = 1;
            params = SeaAroundUsRegionConstants.GLOBAL_PARAMS;
        } else
            params = SeaAroundUsRegionConstants.GLOBAL_SUBREGION_PARAMS;

        List<ResearchData> files;
        files = SeaAroundUsDataCiteUtils.createCatchResearchData(params, subRegionId, regionName);
        files.add(SeaAroundUsDataCiteUtils.createPrimaryProductionFile(params, subRegionId, regionName));
        files.add(SeaAroundUsDataCiteUtils.createStockStatusFile(params, subRegionId, regionName));

        // marine trophic index
        ResearchData marineTrophicIndex = SeaAroundUsDataCiteUtils.createMarineTrophicIndexFile(
                                              params,
                                              subRegionId,
                                              SeaAroundUsDataCiteConstants.GLOBAL_MARINE_TROPHIC_INDEX_LABEL);
        marineTrophicIndex.setLabel((marineTrophicIndex.getLabel() + params.getRegionType().displayName).trim()); // NOPMD - parentheses are needed for proper trimming
        files.add(marineTrophicIndex);

        return files;
    }


    /**
     * Creates global ocean document web links.
     *
     * @param subRegionId the unique identifier of the global ocean sub-area
     * @param regionName the name-suffix of the global ocean sub-area
     *
     * @return a list of global ocean document web links
     */
    private List<WebLink> createWebLinks(int subRegionId, String regionName)
    {
        RegionParametersVO params = SeaAroundUsRegionConstants.GLOBAL_SUBREGION_PARAMS;

        List<WebLink> links;
        links = SeaAroundUsDataCiteUtils.createCatchLinks(params, subRegionId, regionName);
        links.add(SeaAroundUsDataCiteConstants.LOGO_LINK);
        links.add(SeaAroundUsDataCiteUtils.createPrimaryProductionLink(params, subRegionId, regionName));
        links.add(SeaAroundUsDataCiteUtils.createStockStatusLink(params, subRegionId, regionName));
        links.add(SeaAroundUsDataCiteUtils.createSourceLink(getApiUrl(subRegionId)));

        // marine trophic index
        WebLink marineTrophicIndex = SeaAroundUsDataCiteUtils.createMarineTrophicIndexLink(
                                         params,
                                         subRegionId,
                                         SeaAroundUsDataCiteConstants.GLOBAL_MARINE_TROPHIC_INDEX_LABEL);
        marineTrophicIndex.setName((marineTrophicIndex.getName() + params.getRegionType().displayName).trim()); // NOPMD - parentheses are needed for proper trimming
        links.add(marineTrophicIndex);

        return links;
    }


    /**
     * Assembles the source URL for retrieving global ocean data.
     *
     * @param subRegionId the unique identifier of the global ocean sub-area
     *
     * @return the source URL for retrieving global ocean data
     */
    private String getApiUrl(int subRegionId)
    {
        String url = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                         SeaAroundUsDataCiteConstants.GLOBAL_REGION_NAME,
                         1);

        // add sub-region suffix, if necessary
        if (subRegionId != 0)
            url += String.format(SeaAroundUsUrlConstants.GLOBAL_SUB_REGION_API_SUFFIX, subRegionId);

        return url;
    }
}
