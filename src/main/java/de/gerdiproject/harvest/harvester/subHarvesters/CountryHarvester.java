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
import de.gerdiproject.harvest.harvester.AbstractListHarvester;
import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.country.SauAllCountriesResponse;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryProperties;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Source;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * This harvester crawls through all SeaAroundUs countries and generates one document per entry.
 *
 * @author Robin Weiss
 */
public class CountryHarvester extends AbstractListHarvester<Feature<SauCountryProperties>>
{
    private String downloadUrlPrefix;
    private String version = null;
    private final Map<Integer, DataCiteJson> profileDocuments = new HashMap<>();


    /**
     * Constructor that defines one document to be created per harvested entry.
     */
    public CountryHarvester()
    {
        super(1);
    }


    /**
     * Changes a property. If the URL property is changed, the download URL prefix changes as well.
     */
    @Override
    public void setProperty(String key, String value)
    {
        super.setProperty(key, value);

        if (UrlConstants.PROPERTY_URL.equals(key))
            downloadUrlPrefix = value + UrlConstants.COUNTRY_DOWNLOAD_URL_PREFIX;
    }


    @Override
    protected Collection<Feature<SauCountryProperties>> loadEntries()
    {
        // request all countries
        SauAllCountriesResponse allCountries = httpRequester.getObjectFromUrl(downloadUrlPrefix, SauAllCountriesResponse.class);

        // get version from metadata
        version = allCountries.getMetadata().getVersion();

        // return feature array
        return allCountries.getData().getFeatures();
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
        // read country number
        SauCountryProperties properties = entry.getProperties();
        int countryId = properties.getCNumber();

        // create GeoLocation
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setPlace(properties.getTitle());
        geoLocation.setPolygon(entry.getGeometry());

        // check if a document with the same countryId was already harvested
        if (profileDocuments.containsKey(countryId))
            return updateDocument(countryId, geoLocation);
        else
            return createDocument(countryId, properties, geoLocation);
    }


    private List<IDocument> createDocument(int countryId, SauCountryProperties properties, GeoLocation geoLocation)
    {
        // retrieve region info from URL
        SauCountry country = httpRequester.getObjectFromUrl(downloadUrlPrefix + countryId, SauCountryResponse.class).getData();
        DataCiteJson document = null;

        if (countryId != 0) {
            document = new DataCiteJson();
            document.setVersion(version);
            document.setPublisher(DataCiteConstants.PROVIDER);
            document.setFormats(DataCiteConstants.JSON_FORMATS);
            document.setCreators(DataCiteConstants.SAU_CREATORS);
            document.setRightsList(DataCiteConstants.RIGHTS_LIST);

            // add title
            Title title = new Title(DataCiteConstants.COUNTRY_PROFILE_LABEL_PREFIX + country.getCountry());
            document.setTitles(Arrays.asList(title));

            // add source
            Source source = new Source(downloadUrlPrefix + countryId, DataCiteConstants.PROVIDER);
            source.setProviderURI(DataCiteConstants.PROVIDER_URI);
            document.setSources(source);

            // add links
            document.setWebLinks(createWebLinks(country));

            // add subjects
            List<Subject> subjects = createSubjects(country, properties);
            document.setSubjects(subjects);

            // add geo location
            List<GeoLocation> locations = new LinkedList<>();
            locations.add(geoLocation);
            document.setGeoLocations(locations);

            // memorize document references
            profileDocuments.put(countryId, document);
        }

        // return created documents
        return Arrays.asList(document);
    }


    private List<IDocument> updateDocument(int countryId, GeoLocation geoLocation)
    {
        // retrieve region info from URL
        SauCountry countryObj = httpRequester.getObjectFromUrl(downloadUrlPrefix + countryId, SauCountryResponse.class).getData();

        // retrieve existing documents
        DataCiteJson profileDoc = profileDocuments.get(countryId);

        // add title to the search tags
        Subject title = new Subject(countryObj.getCountry());
        profileDoc.getSubjects().add(title);

        // add geolocation to geo array
        profileDoc.getGeoLocations().add(geoLocation);

        // return no documents to increase the harvesting progress
        return null;
    }


    private List<WebLink> createWebLinks(SauCountry country)
    {
        List<WebLink> webLinks = new LinkedList<>();
        webLinks.add(DataCiteConstants.LOGO_LINK);

        WebLink viewLink = new WebLink(UrlConstants.COUNTRY_VIEW_URL_PREFIX + country.getId());
        viewLink.setType(WebLinkType.ViewURL);
        webLinks.add(viewLink);

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

        return webLinks;
    }


    /**
     * Creates search tags for a country object
     * @param country
     * @param properties
     * @return
     */
    private List<Subject> createSubjects(SauCountry country, SauCountryProperties properties)
    {
        List<Subject> tags = new LinkedList<>();

        // the title does not have to be the country, it can also be an island name
        String title = properties.getTitle();

        if (title != null)
            tags.add(new Subject(title));

        String countryName = country.getCountry();

        if (countryName != null)
            tags.add(new Subject(countryName));

        String unName = country.getUnName();

        if (unName != null)
            tags.add(new Subject(unName));

        String govMarineFish = country.getGovMarineFish();

        if (govMarineFish != null)
            tags.add(new Subject(govMarineFish));

        String govProtect = country.getGovProtectMarineEnv();

        if (govProtect != null)
            tags.add(new Subject(govProtect));

        String fishManagementPlan = country.getFishMgtPlan();

        if (fishManagementPlan != null)
            tags.add(new Subject(fishManagementPlan));

        String faoCode = country.getFaoCode();

        if (faoCode != null)
            tags.add(new Subject(faoCode));

        return tags;
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
