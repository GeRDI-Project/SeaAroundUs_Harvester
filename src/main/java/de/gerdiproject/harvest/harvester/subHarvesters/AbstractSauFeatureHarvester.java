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

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.IDocument;
import de.gerdiproject.harvest.harvester.AbstractListHarvester;
import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.UrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollection;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.DataCiteUtils;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Source;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.Title.TitleType;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;
import de.gerdiproject.json.geo.GeoJson;


/**
 * An abstract SeaAroundUs harvester, that specifically harvests {@linkplain FeatureCollection}s.
 *
 * @param <R> the class of the generic feature collection response of harvesting all regions
 * @param <T> the class of the {@linkplain FeatureProperties}
 *
 * @author Robin Weiss
 */
public abstract class AbstractSauFeatureHarvester <R extends GenericResponse<FeatureCollection<T>>, T extends FeatureProperties> extends AbstractListHarvester<Feature<T>>
{
    private final String regionApiName;
    private String version;
    private final Class<R> responseClass;


    /**
     * Basic Constructor for harvesting one document per region.
     *
     * @param regionApiName the name of the region domain
     * @param responseClass the class of the {@linkplain FeatureCollectionResponse}
     */
    public AbstractSauFeatureHarvester(String regionApiName, Class<R> responseClass)
    {
        this(null, regionApiName, responseClass);
    }


    /**
     * Basic Constructor for harvesting one document per region.
     *
     * @param harvesterName a custom name for the harvester
     * @param regionApiName the name of the region domain
     * @param responseClass the class of the {@linkplain FeatureCollectionResponse}
     */
    public AbstractSauFeatureHarvester(String harvesterName, String regionApiName, Class<R> responseClass)
    {
        super(harvesterName, 1);
        this.regionApiName = regionApiName;
        this.responseClass = responseClass;
    }


    /**
     * This function adds region specific data to a harvested document.
     *
     * @param document the document that is to be enriched with data
     * @param apiUrl a URL that points to a region JSON object
     * @param entry a region specific feature
     */
    protected abstract void enrichDocument(DataCiteJson document, String apiUrl, Feature<T> entry);


    /**
     * Generates the main title of the document.
     *
     * @param regionName the name of the harvested region
     *
     * @return a human readable title
     */
    protected abstract String getMainTitleString(String regionName);


    /**
     * Retrieves the unique identifier of a region.
     *
     * @param properties fields concerning the region
     *
     * @return a unique identifier of a region
     */
    protected int getRegionId(T properties)
    {
        return properties.getRegionId();
    }


    @Override
    protected Collection<Feature<T>> loadEntries()
    {
        // request all countries
        String apiUrl = DataCiteUtils.instance().getAllRegionsUrl(regionApiName);
        R allCountries = httpRequester.getObjectFromUrl(apiUrl, responseClass);

        // get version from metadata
        version = allCountries.getMetadata().getVersion();

        // return feature array
        return allCountries.getData().getFeatures();
    }


    @Override
    protected List<IDocument> harvestEntry(Feature<T> entry)
    {
        T properties = entry.getProperties();
        int regionId = getRegionId(properties);
        String apiUrl = DataCiteUtils.instance().getRegionEntryUrl(regionApiName, regionId);

        DataCiteJson document = new DataCiteJson();
        document.setVersion(version);
        document.setPublisher(DataCiteConstants.PROVIDER);
        document.setFormats(DataCiteConstants.JSON_FORMATS);
        document.setCreators(DataCiteConstants.SAU_CREATORS);
        document.setRightsList(DataCiteConstants.RIGHTS_LIST);
        document.setTitles(createBasicTitles(properties));
        document.setSources(createSource(apiUrl));
        document.setWebLinks(createBasicWebLinks(regionId));
        document.setSubjects(createBasicSubjects(entry.getProperties()));
        document.setGeoLocations(createBasicGeoLocations(
                                     entry.getGeometry(),
                                     entry.getProperties().getTitle()
                                 ));

        // let extending classes add more data
        enrichDocument(document, apiUrl, entry);

        // nullify empty lists
        if (document.getSubjects().isEmpty())
            document.setSubjects(null);

        if (document.getGeoLocations().isEmpty())
            document.setGeoLocations(null);

        if (document.getTitles().isEmpty())
            document.setTitles(null);

        return Arrays.asList(document);
    }


    /**
     * Assembles the URL that leads to the SeaAroundUs website of the region.
     *
     * @return the view URL of the region
     */
    protected String getViewUrl(int regionId)
    {
        return String.format(UrlConstants.VIEW_URL, regionApiName, regionId);
    }


    /**
     * Creates a list of region related {@linkplain WebLink}s.
     *
     * @param regionId a unique ID of a region within its domain
     *
     * @return the ViewURL and LogoURL
     */
    protected List<WebLink> createBasicWebLinks(int regionId)
    {
        List<WebLink> webLinks = new LinkedList<>();
        webLinks.add(DataCiteConstants.LOGO_LINK);

        WebLink viewLink = new WebLink(String.format(UrlConstants.VIEW_URL, regionApiName, regionId));
        viewLink.setType(WebLinkType.ViewURL);
        webLinks.add(viewLink);

        return webLinks;
    }


    /**
     * Creates the {@linkplain Source} URL object for a SeaAroundUs region.
     *
     * @param regionApiUrl the SeaAroundUs API URL of a single region
     *
     * @return the source URL object for a region
     */
    private Source createSource(String regionApiUrl)
    {
        Source source = new Source(
            regionApiUrl,
            DataCiteConstants.PROVIDER);
        source.setProviderURI(DataCiteConstants.PROVIDER_URI);
        return source;
    }


    /**
     * Creates a {@linkplain GeoLocation} object for the region.
     *
     * @param regionBorders a {@linkplain GeoJson} describing the region border or null
     * @param regionName a descriptive name of the region
     *
     * @return a {@linkplain GeoLocation} describing the region border or null
     *      if no {@linkplain GeoJson} data exists for the region feature
     */
    protected List<GeoLocation> createBasicGeoLocations(GeoJson regionBorders, String regionName)
    {
        List<GeoLocation> geoLocations = new LinkedList<>();

        if (regionBorders != null) {
            GeoLocation g = new GeoLocation();
            g.setPlace(regionName);
            g.setPolygon(regionBorders);
            geoLocations.add(g);
        }

        return geoLocations;
    }


    /**
     * Creates a list of {@linkplain Title}s for the region.
     *
     * @param properties fields concerning the region
     *
     * @return a list of {@linkplain Title}s for the region
     */
    protected List<Title> createBasicTitles(T properties)
    {
        List<Title> titles = new LinkedList<>();

        String shortTitle = properties.getTitle();

        if (shortTitle != null)
            titles.add(new Title(getMainTitleString(shortTitle)));

        String longTitle = properties.getLongTitle();

        if (longTitle != null) {
            Title lt = new Title(getMainTitleString(longTitle));
            lt.setType(TitleType.AlternativeTitle);
            titles.add(lt);
        }

        return titles;
    }


    /**
     * Creates a list of {@linkplain Subject}s for a SeaAroundUs region.
     *
     * @param properties fields concerning the region
     *
     * @return a list of {@linkplain Subject}s for a SeaAroundUs region
     */
    private List<Subject> createBasicSubjects(T properties)
    {
        List<Subject> subjects = new LinkedList<>();

        String title = properties.getTitle();

        if (title != null)
            subjects.add(new Subject(title));


        String longTitle = properties.getLongTitle();

        if (longTitle != null && !longTitle.equals(title))
            subjects.add(new Subject(longTitle));

        String region = properties.getRegion();

        if (region != null)
            subjects.add(new Subject(region));

        return subjects;
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
