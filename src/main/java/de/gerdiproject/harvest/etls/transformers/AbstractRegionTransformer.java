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

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.SauFeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.Metric;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.RegionParametersVO;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.enums.TitleType;
import de.gerdiproject.json.datacite.extension.generic.ResearchData;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.geo.GeoJson;

/**
 * This transformer deals with creating documents for a generic SeaAroundUs region type, such as EEZ, LME, or RFMO.
 *
 * @param <T> the type of the region that is to be transformed
 *
 * @author Robin Weiss
 */
public abstract class AbstractRegionTransformer <T extends GenericRegion> extends AbstractIteratorTransformer<GenericResponse<T>, DataCiteJson>
{
    protected final RegionParametersVO params;


    /**
     * Constructor that requires region specific parameters.
     *
     * @param params region specific parameters
     */
    public AbstractRegionTransformer(RegionParametersVO params)
    {
        super();
        this.params = params;
    }


    @Override
    protected DataCiteJson transformElement(GenericResponse<T> source) throws TransformerException
    {
        final T region = source.getData();
        final SauFeatureProperties properties = region.getFeature().getProperties();
        final int regionId = properties.getRegionId();

        DataCiteJson document = new DataCiteJson(region.getClass().getSimpleName() + regionId);
        document.setVersion(source.getMetadata().getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.addFormats(SeaAroundUsDataCiteConstants.JSON_FORMATS);
        document.addCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.addRights(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.addTitles(createTitles(properties));
        document.addWebLinks(createWebLinks(region));
        document.addSubjects(createSubjects(region));
        document.addGeoLocations(createGeoLocations(region));
        document.addResearchData(createResearchData(region));

        document.addFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);

        return document;
    }


    /**
     * Returns the region parameters that define the harvested
     * SeaAroundUs region.
     *
     * @return the region parameters that define the harvested
     * SeaAroundUs region.
     */
    public RegionParametersVO getRegionParameters()
    {
        return params;
    }


    /**
     * Returns the main title of the region.
     *
     * @param regionName a human readable name of the region
     *
     * @return a well-formatted region title
     */
    protected String getMainTitleString(String regionName)
    {
        return String.format(
                   SeaAroundUsDataCiteConstants.GENERIC_LABEL,
                   params.getRegionType().getDisplayName(),
                   regionName);
    }


    /**
     * Parses a region object and adds relevant weblinks to a harvested document.
     *
     * @param regionObject the region object source
     *
     * @return a list of relevant weblinks
     */
    protected List<WebLink> createWebLinks(T regionObject)
    {
        int regionId = regionObject.getId();
        String regionName = regionObject.getTitle();

        final List<WebLink> links = SeaAroundUsDataCiteUtils.createBasicWebLinks(params.getRegionType().getUrlName(), regionId);

        links.add(SeaAroundUsDataCiteUtils.createMarineTrophicIndexLink(params, regionId, regionName));
        links.add(SeaAroundUsDataCiteUtils.createPrimaryProductionLink(params, regionId, regionName));
        links.add(SeaAroundUsDataCiteUtils.createStockStatusLink(params, regionId, regionName));

        // add catches
        links.addAll(SeaAroundUsDataCiteUtils.createCatchLinks(params, regionId, regionName));

        return links;
    }


    /**
     * Parses a region object and adds relevant files to a harvested document.
     *
     * @param regionObject the region object source
     *
     * @return a list of {@linkplain File}s from the harvested document
     */
    protected List<ResearchData> createResearchData(T regionObject)
    {
        final int regionId = regionObject.getId();
        final String regionName = regionObject.getTitle();

        final List<ResearchData> files = new LinkedList<>();

        files.add(SeaAroundUsDataCiteUtils.createMarineTrophicIndexFile(params, regionId, regionName));
        files.add(SeaAroundUsDataCiteUtils.createPrimaryProductionFile(params, regionId, regionName));
        files.add(SeaAroundUsDataCiteUtils.createStockStatusFile(params, regionId, regionName));

        // add catches
        files.addAll(SeaAroundUsDataCiteUtils.createCatchResearchData(params, regionId, regionName));

        return files;
    }


    /**
     * Retrieves the {@linkplain GeoJson} from the base region object and adds it to a list of {@linkplain GeoLocation}s.
     *
     * @param regionObject the region object source
     *
     * @return a list of {@linkplain GeoLocation}s from the harvested document
     */
    protected List<GeoLocation> createGeoLocations(T regionObject)
    {
        final List<GeoLocation> geoLocations = SeaAroundUsDataCiteUtils.createBasicGeoLocations(
                                                   regionObject.getFeature().getGeometry(),
                                                   regionObject.getFeature().getProperties().getTitle());

        final List<GeoJson> polys = new LinkedList<>();
        polys.add(regionObject.getGeojson());

        final GeoLocation geoLocation = new GeoLocation();
        geoLocation.setPolygons(polys);

        geoLocations.add(geoLocation);

        return geoLocations;
    }


    /**
     * Assembles the URL that leads to the SeaAroundUs website of the region.
     *
     * @return the view URL of the region
     */
    protected String getViewUrl(int regionId)
    {
        return String.format(SeaAroundUsUrlConstants.VIEW_URL, params.getRegionType().getUrlName(), regionId);
    }


    /**
     * Creates a list of {@linkplain Title}s for the region.
     *
     * @param properties fields concerning the region
     *
     * @return a list of {@linkplain Title}s for the region
     */
    protected List<Title> createTitles(SauFeatureProperties properties)
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
    protected List<Subject> createSubjects(T regionObject)
    {
        final SauFeatureProperties properties = regionObject.getFeature().getProperties();
        final List<Subject> subjects = new LinkedList<>();

        final String region = properties.getRegion();

        if (region != null)
            subjects.add(new Subject(region));

        regionObject.getMetrics().forEach((Metric m) -> {
            if (m.getValue() != 0)
                subjects.add(new Subject(m.getTitle()));
        });

        return subjects;
    }
}
