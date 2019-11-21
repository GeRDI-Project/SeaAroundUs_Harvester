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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.etls.extractors.vos.TaxonVO;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxon;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.extension.generic.ResearchData;
import de.gerdiproject.json.datacite.extension.generic.WebLink;
import de.gerdiproject.json.datacite.extension.generic.enums.WebLinkType;

/**
 * A {@linkplain AbstractIteratorTransformer} implementation for transforming {@linkplain SauTaxon}
 * to {@linkplain DataCiteJson} objects.
 *
 * @author Robin Weiss
 */
public class TaxonTransformer extends AbstractIteratorTransformer<TaxonVO, DataCiteJson>
{
    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        // nothing to retrieve from the ETL
    }


    @Override
    protected DataCiteJson transformElement(final TaxonVO vo) throws TransformerException
    {
        final SauTaxon taxon = vo.getResponse().getData();
        final int taxonKey = taxon.getTaxonKey();
        final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                                  SeaAroundUsRegionConstants.TAXA_API_NAME,
                                  taxonKey);
        final String label = createTaxonLabel(taxon);

        final DataCiteJson document = new DataCiteJson(apiUrl);
        document.setVersion(vo.getResponse().getMetadata().getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PUBLISHER);
        document.addFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);
        document.addCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.addRights(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.addWebLinks(createWebLinks(apiUrl, taxonKey, label));
        document.addResearchData(createFiles(taxonKey, label));
        document.addGeoLocations(createGeoLocations(taxon));
        document.addTitles(createTitles(label));
        document.addSubjects(createSubjects(vo));

        return document;
    }


    /**
     * Creates a list of titles for a taxon.
     *
     * @param label a human readable name of the taxon
     *
     * @return a list of titles for a taxon
     */
    private List<Title> createTitles(final String label)
    {
        return Arrays.asList(new Title(label));
    }


    /**
     * Creates a list of {@linkplain File}s for downloading CSV catch files of
     * the taxon.
     *
     * @param taxonKey the unique taxon ID in SeaAroundUs
     * @param label a human readable name of the taxon
     *
     * @return a list of {@linkplain File}s for downloading CSV catch files of
     *         the taxon
     */
    private List<ResearchData> createFiles(final int taxonKey, final String label)
    {
        final List<ResearchData> files = new LinkedList<>();

        for (final EntryVO measure : SeaAroundUsRegionConstants.TAXON_MEASURES) {
            for (final EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {

                // add catch value web link and file
                final String catchValueLabel = String.format(
                                                   SeaAroundUsDataCiteConstants.TAXON_CATCHES_LABEL,
                                                   measure.getDisplayName(),
                                                   label,
                                                   dimension.getDisplayName());
                final String downloadUrl = SeaAroundUsDataCiteUtils.getCatchesUrl(
                                               SeaAroundUsRegionConstants.TAXA_API_NAME,
                                               taxonKey,
                                               measure,
                                               dimension)
                                           + SeaAroundUsUrlConstants.CSV_FORM;
                final ResearchData catchFile = new ResearchData(downloadUrl, catchValueLabel);
                catchFile.setType(SeaAroundUsDataCiteConstants.CSV_FORMAT);

                files.add(catchFile);
            }
        }

        return files;
    }


    /**
     * Creates a list of {@linkplain WebLink}s for viewing taxon catches.
     *
     * @param apiUrl the URL to retrieve the source JSON of the taxon
     * @param taxonKey the unique taxon ID in SeaAroundUs
     * @param label a human readable name of the taxon
     *
     * @return a list of {@linkplain WebLink}s for viewing taxon catches
     */
    private List<WebLink> createWebLinks(final String apiUrl, final int taxonKey, final String label)
    {
        final List<WebLink> links = new LinkedList<>();

        // add source link
        final WebLink sourceLink = SeaAroundUsDataCiteUtils.createSourceLink(apiUrl);
        links.add(sourceLink);

        // add view link
        final String viewUrl = String.format(SeaAroundUsUrlConstants.TAXON_PROFILE_VIEW_URL, taxonKey);
        final WebLink viewLink = new WebLink(viewUrl);
        viewLink.setName(SeaAroundUsDataCiteConstants.TAXON_VIEW_NAME);
        viewLink.setType(WebLinkType.ViewURL);
        links.add(viewLink);

        // add catch links
        for (final EntryVO measure : SeaAroundUsRegionConstants.TAXON_MEASURES) {
            for (final EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {

                // add catch value web link and file
                final String catchLabel = String.format(
                                              SeaAroundUsDataCiteConstants.TAXON_CATCHES_LABEL,
                                              measure.getDisplayName(),
                                              label,
                                              dimension.getDisplayName());
                final String catchUrl = String.format(
                                            SeaAroundUsUrlConstants.TAXON_CATCH_VIEW_URL,
                                            taxonKey,
                                            dimension.getUrlName(),
                                            measure.getUrlName());

                final WebLink catchLink = new WebLink(catchUrl);
                catchLink.setName(catchLabel);
                catchLink.setType(WebLinkType.ViewURL);

                links.add(catchLink);
            }
        }

        return links;
    }


    /**
     * Creates a label for the taxon containing both its common and scientific
     * name, iff applicable.
     *
     * @param taxon a JSON-object that contains taxon data
     *
     * @return a label for the taxon
     */
    private String createTaxonLabel(final SauTaxon taxon)
    {
        final String commonName = taxon.getCommonName();
        final String scientificName = taxon.getScientificName();
        String label;

        if (commonName == null)
            label = scientificName;
        else if (scientificName == null)
            label = commonName;
        else
            label = String.format(
                        SeaAroundUsDataCiteConstants.TAXON_LABEL,
                        commonName,
                        scientificName);


        return label;
    }


    /**
     * Creates a list of {@linkplain GeoLocation}s out of the southern and
     * northern latitudes of the taxon.
     *
     * @param taxon a JSON-object that contains taxon data
     *
     * @return a list of {@linkplain GeoLocation}s
     */
    private List<GeoLocation> createGeoLocations(final SauTaxon taxon)
    {
        List<GeoLocation> geoLocations = null;

        final Double latNorth = taxon.getLatNorth();
        final Double latSouth = taxon.getLatSouth();

        // check if geo data is available
        if (latNorth != null && latSouth != null) {
            geoLocations = new LinkedList<>();
            final GeoLocation geo = new GeoLocation();
            geo.setBox(-180.0, 180.0, latSouth, latNorth);

            geoLocations.add(geo);
        }

        return geoLocations;
    }


    /**
     * Creates an array of search tags for a taxon.
     *
     * @param vo the extracted value object
     *
     * @return a list of search tags
     */
    private List<Subject> createSubjects(final TaxonVO vo)
    {
        final List<Subject> subjects = new LinkedList<>();

        // add generic taxon fields
        final SauTaxon taxon = vo.getResponse().getData();
        subjects.add(new Subject(taxon.getCommonName()));
        subjects.add(new Subject(taxon.getScientificName()));
        subjects.add(new Subject(taxon.getFunctionalGroup()));
        subjects.add(new Subject(taxon.getCommercialGroup()));
        subjects.add(new Subject(taxon.getSlMaxCm() + SeaAroundUsDataCiteConstants.CENTIMETERS_SUFFIX));
        subjects.add(new Subject(vo.getTaxonGroupName()));
        subjects.add(new Subject(vo.getTaxonLevelName()));

        // add names of all habitats occupied by the taxon
        final Map<String, Double> habitatIndex = taxon.getHabitatIndex();

        if (habitatIndex != null) {
            habitatIndex.forEach((final String name, final Double value) -> {

                if (value != null && value > 0.0 && !name.equals("habitat_diversity_index"))
                    subjects.add(new Subject(name));
            });
        }

        return subjects;
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }
}
