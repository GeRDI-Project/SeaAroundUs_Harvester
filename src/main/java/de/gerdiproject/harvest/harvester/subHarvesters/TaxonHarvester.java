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
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.catches.SauCatch;
import de.gerdiproject.harvest.seaaroundus.json.catches.SauCatchesResponse;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauAllTaxaResponse;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxon;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonGroupResponse;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonLevelResponse;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonReduced;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.json.datacite.DataCiteJson;
import de.gerdiproject.json.datacite.GeoLocation;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.Title;
import de.gerdiproject.json.datacite.extension.ResearchData;
import de.gerdiproject.json.datacite.extension.WebLink;
import de.gerdiproject.json.datacite.extension.enums.WebLinkType;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * A harvester for harvesting all taxa from SeaAroundUs.
 * <br><br>
 * All Taxa:     http://api.seaaroundus.org/api/v1/taxa/<br>
 * Example Taxon: http://api.seaaroundus.org/api/v1/taxa/600009
 *
 * @author Robin Weiss
 */
public class TaxonHarvester extends AbstractListHarvester<SauTaxonReduced>
{
    private Map<Integer, String> taxonGroups;
    private Map<Integer, String> taxonLevels;

    private String version;


    /**
     * @param harvestedDocuments the list to which harvested documents are added
     */
    public TaxonHarvester()
    {
        super(1);
    }


    @Override
    protected Collection<SauTaxonReduced> loadEntries()
    {
        // request all taxa
        String apiUrl = SeaAroundUsDataCiteUtils.instance().getAllRegionsUrl(SeaAroundUsRegionConstants.TAXA_API_NAME);
        SauAllTaxaResponse allCountries = httpRequester.getObjectFromUrl(apiUrl, SauAllTaxaResponse.class);

        // get version from metadata
        version = allCountries.getMetadata().getVersion();

        // get taxon group and level names
        taxonGroups = getTaxonGroups();
        taxonLevels = getTaxonLevels();

        // return taxa array
        return allCountries.getData();
    }


    @Override
    protected List<IDocument> harvestEntry(SauTaxonReduced entry)
    {
        int taxonKey = entry.getTaxonKey();
        String apiUrl = SeaAroundUsDataCiteUtils.instance().getRegionEntryUrl(SeaAroundUsRegionConstants.TAXA_API_NAME, taxonKey);
        SauTaxon taxon = httpRequester.getObjectFromUrl(apiUrl, SauTaxonResponse.class).getData();
        String label = createTaxonLabel(taxon);

        DataCiteJson document = new DataCiteJson();
        document.setVersion(version);
        document.setRepositoryIdentifier(SeaAroundUsDataCiteConstants.REPOSITORY_ID);
        document.setResearchDisciplines(SeaAroundUsDataCiteConstants.RESEARCH_DISCIPLINES);
        document.setPublisher(SeaAroundUsDataCiteConstants.PROVIDER);
        document.setFormats(SeaAroundUsDataCiteConstants.CSV_FORMATS);
        document.setCreators(SeaAroundUsDataCiteConstants.SAU_CREATORS);
        document.setRightsList(SeaAroundUsDataCiteConstants.RIGHTS_LIST);
        document.setWebLinks(createWebLinks(apiUrl, taxonKey, label));
        document.setResearchDataList(createFiles(taxonKey, label));
        document.setGeoLocations(createGeoLocations(taxon));
        document.setTitles(createTitles(label));
        document.setSubjects(createSubjects(taxon));

        return Arrays.asList(document);
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
     * Creates a list of {@linkplain File}s for downloading CSV catch files
     * of the taxon.
     *
     * @param taxonKey the unique taxon ID in SeaAroundUs
     * @param label a human readable name of the taxon
     *
     * @return a list of {@linkplain File}s for downloading CSV catch files
     * of the taxon
     */
    private List<ResearchData> createFiles(int taxonKey, String label)
    {
        List<ResearchData> files = new LinkedList<>();

        for (EntryVO measure : SeaAroundUsRegionConstants.TAXON_MEASURES) {
            for (EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {

                // add catch value web link and file
                String catchValueLabel = String.format(SeaAroundUsDataCiteConstants.TAXON_CATCHES_LABEL, measure.displayName, label, dimension.displayName);
                String downloadUrl = SeaAroundUsDataCiteUtils.instance().getCatchesUrl(
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
        WebLink sourceLink = SeaAroundUsDataCiteUtils.instance().createSourceLink(apiUrl);
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
                String catchLabel = String.format(SeaAroundUsDataCiteConstants.TAXON_CATCHES_LABEL, measure.displayName, label, dimension.displayName);
                String catchUrl = String.format(SeaAroundUsUrlConstants.TAXON_CATCH_VIEW_URL, taxonKey, dimension.urlName, measure.urlName);

                WebLink catchLink = new WebLink(catchUrl);
                catchLink.setName(catchLabel);
                catchLink.setType(WebLinkType.ViewURL);

                links.add(catchLink);
            }
        }

        return links;
    }


    /**
     * Creates a label for the taxon containing both its common
     * and scientific name, iff applicable.
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
     * Creates a list of {@linkplain GeoLocation}s out of
     * the southern and northern latitudes of the taxon.
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
        subjects.add(new Subject(taxonGroups.get(taxon.getTaxonGroupId())));
        subjects.add(new Subject(taxonLevels.get(taxon.getTaxonLevelId())));

        // add names of all habitats occupied by the taxon
        Map<String, Double> habitatIndex = taxon.getHabitatIndex();

        if (habitatIndex != null) {
            habitatIndex.forEach((String name, Double value) -> {

                if (value != null && value > 0.0 && !name.equals("habitat_diversity_index"))
                    subjects.add(new Subject(name));
            });
        }

        // add catch regions
        int taxonKey = taxon.getTaxonKey();

        for (EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {
            String valueUrl = SeaAroundUsDataCiteUtils.instance().getCatchesUrl(SeaAroundUsRegionConstants.TAXA_API_NAME, taxonKey, SeaAroundUsRegionConstants.TAXON_MEASURE_VALUE, dimension);

            SauCatchesResponse catchResponse = httpRequester.getObjectFromUrl(valueUrl, SauCatchesResponse.class);

            // not all taxa have catch data
            if (catchResponse != null && catchResponse.getData() != null) {
                // add catch zone names
                catchResponse.getData().forEach((SauCatch c) -> subjects.add(new Subject(c.getKey())));
            }
        }

        return subjects;
    }


    /**
     * Gets a map of taxon group names.
     *
     * @return a map of taxon group names
     */
    private Map<Integer, String> getTaxonGroups()
    {
        String taxonGroupUrl = SeaAroundUsDataCiteUtils.instance().getAllRegionsUrl(SeaAroundUsRegionConstants.TAXON_GROUP_API_NAME);
        SauTaxonGroupResponse taxonGroupResponse = httpRequester.getObjectFromUrl(taxonGroupUrl, SauTaxonGroupResponse.class);

        return taxonGroupResponse.toMap();
    }


    /**
     * Gets a map of taxon level names.
     *
     * @return a map of taxon level names
     */
    private Map<Integer, String> getTaxonLevels()
    {
        String taxonGroupUrl = SeaAroundUsDataCiteUtils.instance().getAllRegionsUrl(SeaAroundUsRegionConstants.TAXON_LEVEL_API_NAME);
        SauTaxonLevelResponse taxonGroupResponse = httpRequester.getObjectFromUrl(taxonGroupUrl, SauTaxonLevelResponse.class);

        return taxonGroupResponse.toMap();
    }
}
