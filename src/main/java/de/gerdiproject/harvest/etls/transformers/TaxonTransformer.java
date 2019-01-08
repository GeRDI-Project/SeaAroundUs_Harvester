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
public class TaxonTransformer extends AbstractIteratorTransformer<SauTaxon, DataCiteJson>
{
    @Override
    protected DataCiteJson transformElement(SauTaxon taxon) throws TransformerException
    {
        final int taxonKey = taxon.getTaxonKey();
        String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                            SeaAroundUsRegionConstants.TAXA_API_NAME,
                            taxonKey);
        String label = createTaxonLabel(taxon);

        DataCiteJson document = new DataCiteJson(apiUrl);
        document.setVersion(taxon.getVersion());
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.addResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.addFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);
        document.addCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.addRights(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.addWebLinks(createWebLinks(apiUrl, taxonKey, label));
        document.addResearchData(createFiles(taxonKey, label));
        document.addGeoLocations(createGeoLocations(taxon));
        document.addTitles(createTitles(label));
        document.addSubjects(createSubjects(taxon));

        return document;
    }


    /**
     * Creates a list of titles for a taxon.
     *
     * @param label a human readable name of the taxon
     *
     * @return a list of titles for a taxon
     */
    private List<Title> createTitles(String label)
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
    private List<ResearchData> createFiles(int taxonKey, String label)
    {
        List<ResearchData> files = new LinkedList<>();

        for (EntryVO measure : SeaAroundUsRegionConstants.TAXON_MEASURES) {
            for (EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {

                // add catch value web link and file
                String catchValueLabel = String.format(
                                             SeaAroundUsDataCiteConstants.TAXON_CATCHES_LABEL,
                                             measure.getDisplayName(),
                                             label,
                                             dimension.getDisplayName());
                String downloadUrl = SeaAroundUsDataCiteUtils.getCatchesUrl(
                                         SeaAroundUsRegionConstants.TAXA_API_NAME,
                                         taxonKey,
                                         measure,
                                         dimension)
                                     + SeaAroundUsUrlConstants.CSV_FORM;
                ResearchData catchFile = new ResearchData(downloadUrl, catchValueLabel);
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
    private List<WebLink> createWebLinks(String apiUrl, int taxonKey, String label)
    {
        List<WebLink> links = new LinkedList<>();

        // add source link
        WebLink sourceLink = SeaAroundUsDataCiteUtils.createSourceLink(apiUrl);
        links.add(sourceLink);

        // add view link
        String viewUrl = String.format(SeaAroundUsUrlConstants.TAXON_PROFILE_VIEW_URL, taxonKey);
        WebLink viewLink = new WebLink(viewUrl);
        viewLink.setName(SeaAroundUsDataCiteConstants.TAXON_VIEW_NAME);
        viewLink.setType(WebLinkType.ViewURL);
        links.add(viewLink);

        // add catch links
        for (EntryVO measure : SeaAroundUsRegionConstants.TAXON_MEASURES) {
            for (EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {

                // add catch value web link and file
                String catchLabel = String.format(
                                        SeaAroundUsDataCiteConstants.TAXON_CATCHES_LABEL,
                                        measure.getDisplayName(),
                                        label,
                                        dimension.getDisplayName());
                String catchUrl = String.format(
                                      SeaAroundUsUrlConstants.TAXON_CATCH_VIEW_URL,
                                      taxonKey,
                                      dimension.getUrlName(),
                                      measure.getUrlName());

                WebLink catchLink = new WebLink(catchUrl);
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
    private String createTaxonLabel(SauTaxon taxon)
    {
        String commonName = taxon.getCommonName();
        String scientificName = taxon.getScientificName();
        String label;

        if (commonName != null && scientificName != null)
            label = String.format(SeaAroundUsDataCiteConstants.TAXON_LABEL, commonName, scientificName);
        else if (commonName != null)
            label = commonName;
        else
            label = scientificName;


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
    private List<GeoLocation> createGeoLocations(SauTaxon taxon)
    {
        List<GeoLocation> geoLocations = null;

        Double latNorth = taxon.getLatNorth();
        Double latSouth = taxon.getLatSouth();

        // check if geo data is available
        if (latNorth != null && latSouth != null) {
            geoLocations = new LinkedList<>();
            GeoLocation geo = new GeoLocation();
            geo.setBox(-180.0, 180.0, latSouth, latNorth);

            geoLocations.add(geo);
        }

        return geoLocations;
    }


    /**
     * Creates an array of search tags for a taxon.
     *
     * @param taxon a JSON-object that contains taxon data
     *
     * @return a list of search tags
     */
    private List<Subject> createSubjects(SauTaxon taxon)
    {
        List<Subject> subjects = new LinkedList<>();

        // add generic taxon fields
        subjects.add(new Subject(taxon.getCommonName()));
        subjects.add(new Subject(taxon.getScientificName()));
        subjects.add(new Subject(taxon.getFunctionalGroup()));
        subjects.add(new Subject(taxon.getCommercialGroup()));
        subjects.add(new Subject(taxon.getSlMaxCm() + SeaAroundUsDataCiteConstants.CENTIMETERS_SUFFIX));
        subjects.add(new Subject(taxon.getTaxonGroupName()));
        subjects.add(new Subject(taxon.getTaxonLevelName()));

        // add names of all habitats occupied by the taxon
        Map<String, Double> habitatIndex = taxon.getHabitatIndex();

        if (habitatIndex != null) {
            habitatIndex.forEach((String name, Double value) -> {

                if (value != null && value > 0.0 && !name.equals("habitat_diversity_index"))
                    subjects.add(new Subject(name));
            });
        }

        return subjects;
    }


}
