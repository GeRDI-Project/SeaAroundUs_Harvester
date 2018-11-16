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

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.mariculture.SauMariculture;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.extension.ResearchData;
import de.gerdiproject.json.geo.GeoJson;

/**
 * This {@linkplain AbstractRegionTransformer} transforms all Maricultures of SeaAroundUs to documents.
 * <br>see http://api.seaaroundus.org/api/v1/mariculture/
 *
 * @author Robin Weiss
 */
public class MaricultureTransformer extends AbstractIteratorTransformer<GenericResponse<List<SauMariculture>>, DataCiteJson>
{
    @Override
    protected DataCiteJson transformElement(GenericResponse<List<SauMariculture>> source) throws TransformerException
    {
        final List<SauMariculture> subRegions = source.getData();
        final int regionId = subRegions.get(0).getEntityId();
        final String regionApiName = SeaAroundUsRegionConstants.MARICULTURE_API_NAME;

        DataCiteJson document = new DataCiteJson(regionApiName + regionId);
        document.setVersion(source.getMetadata().getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.addFormats(SeaAroundUsDataCiteConstants.JSON_FORMATS);
        document.addCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.addRights(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.addTitles(createTitles(subRegions));
        document.addWebLinks(SeaAroundUsDataCiteUtils.createBasicWebLinks(regionApiName, regionId));
        document.addSubjects(createSubjects(subRegions));
        document.addGeoLocations(createGeoLocations(subRegions));
        document.addResearchDataList(createResearchData(subRegions));
        document.addFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);

        return document;
    }


    /**
     * Creates a list of {@linkplain Title}s for the region.
     *
     * @param subRegions a list of relevant regions within the mariculture country
     *
     * @return a list of {@linkplain Title}s for the region
     */
    protected List<Title> createTitles(List<SauMariculture> subRegions)
    {
        final String shortTitle = subRegions.get(0).getCountryName();
        final Title mainTitle = new Title(SeaAroundUsDataCiteConstants.MARICULTURE_LABEL_PREFIX + shortTitle);

        List<Title> titles = new LinkedList<>();
        titles.add(mainTitle);

        return titles;
    }


    /**
     * Creates a list of downloadable {@linkplain ResearchData} of the mariculture datasets.
     *
     * @param subRegions a list of relevant regions within the mariculture country
     *
     * @return a list of downloadable {@linkplain ResearchData}
     */

    private List<ResearchData> createResearchData(List<SauMariculture> subRegions)
    {
        String countryName = subRegions.get(0).getCountryName();
        final int regionId = subRegions.get(0).getEntityId();
        final String apiUrl = SeaAroundUsDataCiteUtils.getAllRegionsUrl(SeaAroundUsRegionConstants.MARICULTURE_API_NAME);

        List<ResearchData> researchData = new LinkedList<>();

        for (EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_MARICULTURE) {
            // add download for combined sub-regions
            researchData.add(new ResearchData(
                                 String.format(SeaAroundUsUrlConstants.MARICULTURE_DOWNLOAD_ALL_URL, apiUrl, dimension.urlName, regionId),
                                 String.format(SeaAroundUsDataCiteConstants.MARICULTURE_FILE_NAME, dimension.displayName, countryName)));

            // add sub-region downloads
            subRegions.forEach((SauMariculture subRegion) -> {
                researchData.add(new ResearchData(
                                     String.format(SeaAroundUsUrlConstants.MARICULTURE_DOWNLOAD_SUBREGION_URL, apiUrl, dimension.urlName, regionId, subRegion.getRegionId()),
                                     String.format(SeaAroundUsDataCiteConstants.MARICULTURE_SUBREGION_FILE_NAME, dimension.displayName, countryName, subRegion.getTitle())));
            });
        }

        return researchData;
    }


    /**
     * Creates subjects for subregions of the mariculture.
     *
     * @param subRegions a list of relevant regions within the mariculture country
     *
     * @return subjects for subregions of the mariculture
     */
    private List<Subject> createSubjects(List<SauMariculture> subRegions)
    {
        final List<Subject> subjects = new LinkedList<>();

        // add titles of sub-regions
        subRegions.forEach((SauMariculture subRegion) -> {
            final Subject subRegionTitle = new Subject(subRegion.getTitle());
            subRegionTitle.setLang(SeaAroundUsDataCiteConstants.SAU_LANGUAGE);
            subjects.add(subRegionTitle);
        });

        return subjects;
    }


    /**
     * Parses and return a list of {@linkplain GeoLocation} of the subregions.
     *
     * @param subRegions a list of relevant regions within the mariculture country
     *
     * @return a list of {@linkplain GeoLocation} of the subregions
     */
    protected List<GeoLocation> createGeoLocations(List<SauMariculture> subRegions)
    {
        final List<GeoLocation> geoLocations = new LinkedList<GeoLocation>();

        subRegions.forEach((SauMariculture subRegion) -> {

            // only add location if it has geo json data
            if (subRegion.getGeojson() != null || subRegion.getPointGeojson() != null)
            {
                List<GeoJson>
                polys = new LinkedList<>();
                polys.add(subRegion.getGeojson());

                GeoLocation subLocation = new GeoLocation();
                subLocation.setPolygons(polys);
                subLocation.setPoint(subRegion.getPointGeojson());
                subLocation.setPlace(subRegion.getTitle());

                geoLocations.add(subLocation);
            }
        });

        return geoLocations;
    }
}
