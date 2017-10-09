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
package de.gerdiproject.harvest.harvester.subHarvesters;

import de.gerdiproject.harvest.IDocument;
import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.country.SauAllCountriesResponse;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryProperties;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.Title.TitleType;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This harvester crawls through all SeaAroundUs countries and generates one document per entry.
 * <br><br>
 * All Countries:   http://api.seaaroundus.org/api/v1/country/<br>
 * Example Country: http://api.seaaroundus.org/api/v1/country/120
 *
 * @author Robin Weiss
 */
public class CountryHarvester extends AbstractSauFeatureHarvester<SauAllCountriesResponse, SauCountryProperties>
{
    private final Map<Integer, DataCiteJson> profileDocuments = new HashMap<>();


    /**
     * Creates a harvester for harvesting all countries.
     */
    public CountryHarvester()
    {
        super("country", SauAllCountriesResponse.class);
    }


    @Override
    protected int getRegionId(SauCountryProperties properties)
    {
        return properties.getCNumber();
    }


    @Override
    protected String getMainTitleString(String regionName)
    {
        return DataCiteConstants.COUNTRY_LABEL_PREFIX + regionName;
    }


    @Override
    protected void enrichDocument(DataCiteJson document, String apiUrl, Feature<SauCountryProperties> entry)
    {
        // get additional info about the country
        SauCountry country = httpRequester.getObjectFromUrl(apiUrl, SauCountryResponse.class).getData();

        // enrich the document
        enrichSubjects(document.getSubjects(), country);
        enrichWebLinks(document.getWebLinks(), country);

        // remember the document
        profileDocuments.put(getRegionId(entry.getProperties()), document);
    }


    @Override
    protected boolean harvestInternal(int from, int to) throws Exception // NOPMD - see explanation in AbstractHarvester
    {
        profileDocuments.clear();
        return super.harvestInternal(from, to);
    }


    @Override
    protected List<IDocument> harvestEntry(Feature<SauCountryProperties> entry)
    {
        int countryId = getRegionId(entry.getProperties());

        // check if a document with this id was already harvested
        if (profileDocuments.containsKey(countryId)) {

            // enrich document if the same countryId was already harvested
            updateDocument(entry);

            // add no additional document to the search index
            return null;
        } else
            // create the document
            return super.harvestEntry(entry);
    }


    /**
     * Adds another GeoLocation, Title, and some subjects to an already harvested country document.
     *
     * @param countryId a SeaAroundUs unique identifier of the country
     * @param geoLocation the (multi-) polygon location that describes the country border
     * @param countryName the name of the country
     * @param isoCode the iso code for the country or "-99" if it does not exist
     */
    private void updateDocument(Feature<SauCountryProperties> entry)
    {
        SauCountryProperties properties = entry.getProperties();
        String countryName = properties.getTitle();

        // retrieve existing documents
        DataCiteJson updatedDoc = profileDocuments.get(getRegionId(properties));

        // add country name to the titles
        Title countryTitle = new Title(DataCiteConstants.COUNTRY_LABEL_PREFIX + countryName);
        countryTitle.setLang(DataCiteConstants.SAU_LANGUAGE);
        countryTitle.setType(TitleType.AlternativeTitle);
        updatedDoc.getTitles().add(countryTitle);

        // add country name to the search tags
        updatedDoc.getSubjects().add(new Subject(countryName));

        // add iso code tag, if it is not a dummy value
        String isoCode = properties.getCIsoCode();

        if (!isoCode.equals("-99"))
            updatedDoc.getSubjects().add(new Subject(isoCode));

        // add geolocations to geo array
        List<GeoLocation> geoLocations = createBasicGeoLocations(entry.getGeometry(), countryName);

        if (!geoLocations.isEmpty()) {
            if (updatedDoc.getGeoLocations() == null)
                updatedDoc.setGeoLocations(geoLocations);
            else
                updatedDoc.getGeoLocations().addAll(geoLocations);
        }
    }


    /**
     * Enriches a list of country related {@linkplain WebLink}s.
     *
     * @param country the country JSON object
     */
    private void enrichWebLinks(List<WebLink> webLinks, SauCountry country)
    {
        webLinks.add(DataCiteConstants.LOGO_LINK);

        String faoProfileUrl = country.getFaoProfileUrl();

        if (faoProfileUrl != null) {
            WebLink relatedLink = new WebLink(faoProfileUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(DataCiteConstants.FAO_COUNTRY_PROFILE_LINK_NAME);
            webLinks.add(relatedLink);
        }

        String govProtectUrl = country.getUrlGovProtectMarineEnv();

        if (govProtectUrl != null) {
            WebLink relatedLink = new WebLink(govProtectUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(country.getGovProtectMarineEnv());
            webLinks.add(relatedLink);
        }

        String fishMgtUrl = country.getUrlFishMgtPlan();

        if (fishMgtUrl != null) {
            WebLink relatedLink = new WebLink(fishMgtUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(country.getFishMgtPlan());
            webLinks.add(relatedLink);
        }

        String majorLawPlanUrl = country.getUrlMajorLawPlan();

        if (majorLawPlanUrl != null) {
            WebLink relatedLink = new WebLink(majorLawPlanUrl);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(country.getMajorLawPlan());
            webLinks.add(relatedLink);
        }

        String fishBaseId = country.getFishBase();

        if (fishBaseId != null) {
            String faoCode = country.getFaoCode();
            faoCode = faoCode == null ? "" : faoCode;

            WebLink relatedLink = new WebLink(UrlConstants.TREATIES_VIEW_URL_PREFIX + fishBaseId);
            relatedLink.setType(WebLinkType.Related);
            relatedLink.setName(String.format(DataCiteConstants.TREATIES_LABEL_PREFIX, country.getCountry(), faoCode));
            webLinks.add(relatedLink);
        }
    }


    /**
     * Enriches a list of {@linkplain Subject}s for a SeaAroundUs country profile.
     *
     * @param subjects the subjects that are to be enriched
     * @param country the country JSON object
     */
    private void enrichSubjects(List<Subject> subjects, SauCountry country)
    {
        String[] rawTags = {
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
                s.setLang(DataCiteConstants.SAU_LANGUAGE);
                subjects.add(s);
            }
        }
    }
}
