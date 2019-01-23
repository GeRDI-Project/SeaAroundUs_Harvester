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
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.enums.TitleType;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;
import de.gerdiproject.json.geo.Feature;

/**
 * This {@linkplain AbstractRegionTransformer} transforms all Countries of SeaAroundUs to documents.
 * <br>see http://api.seaaroundus.org/api/v1/country/
 *
 * @author Robin Weiss
 */
public class CountryTransformer extends AbstractIteratorTransformer<GenericResponse<SauCountry>, DataCiteJson>
{
    @Override
    protected DataCiteJson transformElement(GenericResponse<SauCountry> source) throws TransformerException
    {
        final SauCountry country = source.getData();
        final int regionId = country.getCNumber();
        final String regionApiName = SeaAroundUsRegionConstants.COUNTRY_API_NAME;

        DataCiteJson document = new DataCiteJson(regionApiName + regionId);
        document.setVersion(source.getMetadata().getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.addFormats(SeaAroundUsDataCiteConstants.JSON_FORMATS);
        document.addCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.addRights(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.addTitles(createTitles(country));
        document.addWebLinks(createWebLinks(country));
        document.addSubjects(createSubjects(country));
        document.addGeoLocations(createGeoLocations(country));
        document.addFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);

        return document;
    }


    /**
     * Creates a list of {@linkplain Title}s for the country.
     *
     * @param country the country that is to be transformed
     *
     * @return a list of {@linkplain Title}s for the region
     */
    private List<Title> createTitles(SauCountry country)
    {
        final List<Title> titles = new LinkedList<>();

        final Title mainTitle = new Title(String.format(SeaAroundUsDataCiteConstants.COUNTRY_LABEL, country.getCountry()));

        // add subRegion titles
        for (Feature<SauCountryProperties> subRegion : country.getSubRegions()) {
            final Title subRegionTitle = new Title(String.format(
                                                       SeaAroundUsDataCiteConstants.COUNTRY_LABEL,
                                                       subRegion.getProperties().getTitle()));

            subRegionTitle.setLang(SeaAroundUsDataCiteConstants.SAU_LANGUAGE);
            subRegionTitle.setType(TitleType.AlternativeTitle);
            titles.add(subRegionTitle);
        }

        titles.add(mainTitle);

        return titles;
    }


    /**
     * Creates a list of country related {@linkplain WebLink}s.
     *
     * @param country the country that is to be transformed
     */
    private List<WebLink> createWebLinks(SauCountry country)
    {
        final String faoProfileUrl = country.getFaoProfileUrl();

        final List<WebLink> webLinks = SeaAroundUsDataCiteUtils.createBasicWebLinks(
                                           SeaAroundUsRegionConstants.COUNTRY_API_NAME,
                                           country.getCNumber());

        if (faoProfileUrl != null) {
            final WebLink relatedLink = new WebLink(faoProfileUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(SeaAroundUsDataCiteConstants.FAO_COUNTRY_PROFILE_LINK_NAME);
            webLinks.add(relatedLink);
        }

        final String govProtectUrl = country.getUrlGovProtectMarineEnv();

        if (govProtectUrl != null) {
            final WebLink relatedLink = new WebLink(govProtectUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(country.getGovProtectMarineEnv());
            webLinks.add(relatedLink);
        }

        final String fishMgtUrl = country.getUrlFishMgtPlan();

        if (fishMgtUrl != null) {
            final WebLink relatedLink = new WebLink(fishMgtUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(country.getFishMgtPlan());
            webLinks.add(relatedLink);
        }

        final String majorLawPlanUrl = country.getUrlMajorLawPlan();

        if (majorLawPlanUrl != null) {
            final WebLink relatedLink = new WebLink(majorLawPlanUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(country.getMajorLawPlan());
            webLinks.add(relatedLink);
        }

        final  String fishBaseId = country.getFishBase();

        if (fishBaseId != null) {
            String faoCode = country.getFaoCode();
            faoCode = faoCode == null ? "" : faoCode;

            final WebLink relatedLink = new WebLink(String.format(SeaAroundUsUrlConstants.TREATIES_VIEW_URL, fishBaseId));
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(String.format(SeaAroundUsDataCiteConstants.TREATIES_LABEL, country.getCountry(), faoCode));
            webLinks.add(relatedLink);
        }

        return webLinks;
    }


    /**
     * Creates a list of {@linkplain Subject}s for a SeaAroundUs country profile.
     *
     * @param country the country that is to be transformed
     */
    private List<Subject> createSubjects(SauCountry country)
    {
        final List<Subject> subjects = new LinkedList<>();

        final String[] rawTags = {
            country.getCountry(),
            country.getUnName(),
            country.getGovMarineFish(),
            country.getGovProtectMarineEnv(),
            country.getFishMgtPlan(),
            country.getFaoCode()
        };

        for (String tag : rawTags) {

            if (tag != null) {
                Subject s = new Subject(tag);
                s.setLang(SeaAroundUsDataCiteConstants.SAU_LANGUAGE);
                subjects.add(s);
            }
        }

        // add subRegion subjects
        for (Feature<SauCountryProperties> subRegion : country.getSubRegions()) {
            final String isoCode = subRegion.getProperties().getCIsoCode();

            if (!isoCode.equals("-99"))
                subjects.add(new Subject(isoCode));


            final String region = subRegion.getProperties().getRegion();

            if (region != null)
                subjects.add(new Subject(region));
        }

        return subjects;
    }


    /**
     * Parses and return a list of {@linkplain GeoLocation}s for a SeaAroundUs country profile.
     *
     * @param country the country that is to be transformed
     *
     * @return a list of {@linkplain GeoLocation} of the country
     */
    private List<GeoLocation> createGeoLocations(SauCountry country)
    {
        final List<GeoLocation> geoLocations = new LinkedList<GeoLocation>();

        // add geo locations of subRegion
        for (Feature<SauCountryProperties> subRegion : country.getSubRegions()) {
            geoLocations.addAll(SeaAroundUsDataCiteUtils.createBasicGeoLocations(
                                    subRegion.getGeometry(),
                                    subRegion.getProperties().getTitle()));
        }

        return geoLocations;
    }
}
