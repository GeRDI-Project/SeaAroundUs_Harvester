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
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollection;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteFactory;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.Title.TitleType;


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
        String apiUrl = SeaAroundUsDataCiteFactory.instance().getAllRegionsUrl(regionApiName);
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
        String apiUrl = SeaAroundUsDataCiteFactory.instance().getRegionEntryUrl(regionApiName, regionId);

        DataCiteJson document = new DataCiteJson();
        document.setVersion(version);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.setFormats(SeaAroundUsDataCiteConstants.JSON_FORMATS);
        document.setCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.setRightsList(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.setTitles(createBasicTitles(properties));
        document.setSources(SeaAroundUsDataCiteFactory.instance().createSource(apiUrl));
        document.setWebLinks(SeaAroundUsDataCiteFactory.instance().createBasicWebLinks(regionApiName, regionId));
        document.setSubjects(createBasicSubjects(entry.getProperties()));
        document.setGeoLocations(SeaAroundUsDataCiteFactory.instance().createBasicGeoLocations(
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
        return String.format(SeaAroundUsUrlConstants.VIEW_URL, regionApiName, regionId);
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


        String longTitle = properties.getLongTitle();

        if (longTitle != null) {
            Title lt = new Title(getMainTitleString(longTitle));
            titles.add(lt);
        }


        String shortTitle = properties.getTitle();

        if (shortTitle != null) {
            Title st = new Title(getMainTitleString(shortTitle));

            if (longTitle != null)
                st.setType(TitleType.AlternativeTitle);

            titles.add(st);
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
}
