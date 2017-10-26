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
package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;

import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.rfmo.SauRfmoContractingCountry;
import de.gerdiproject.harvest.seaaroundus.json.rfmo.SauRfmoRegion;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonReduced;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.extension.WebLink;

import java.util.List;

import com.google.gson.reflect.TypeToken;

/**
 * This harvester harvests all RFMOs of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/rfmo/
 *
 * @author Robin Weiss
 */
public class RfmoRegionHarvester extends GenericRegionHarvester<SauRfmoRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * RFMO parameters.
     */
    public RfmoRegionHarvester()
    {
        super(new TypeToken<GenericResponse<SauRfmoRegion>>() {}, RegionConstants.RFMO_PARAMS);
    }


    @Override
    protected void enrichSubjects(List<Subject> subjects, SauRfmoRegion regionObject)
    {
        super.enrichSubjects(subjects, regionObject);
        enrichSubjectsByTaxa(subjects, regionObject.getPrimaryTaxa());
        enrichSubjectsByTaxa(subjects, regionObject.getSecondaryTaxa());
    }


    /**
     * Adds {@linkplain Subject}s of taxa of the RFMO to the document's weblinks.
     *
     * @param subjects the weblink list of the document that is to be enriched
     * @param taxa a list of taxon data
     */
    private void enrichSubjectsByTaxa(List<Subject> subjects, List<SauTaxonReduced> taxa)
    {
        if (taxa != null && !taxa.isEmpty()) {

            taxa.forEach((SauTaxonReduced taxon) -> {

                String commonName = taxon.getCommonName();

                if (commonName != null)
                    subjects.add(new Subject(commonName));

                String scientificName = taxon.getScientificName();
                if (scientificName != null)
                    subjects.add(new Subject(scientificName));
            });
        }
    }


    @Override
    protected void enrichWebLinks(List<WebLink> links, SauRfmoRegion regionObject)
    {
        super.enrichWebLinks(links, regionObject);

        List<SauRfmoContractingCountry> countries = regionObject.getContractingCountries();

        if (countries != null && !countries.isEmpty()) {
            countries.forEach((SauRfmoContractingCountry c) -> {

                WebLink cLink = new WebLink(c.getFacpUrl());
                cLink.setName(String.format(
                                  DataCiteConstants.CONTRACTING_COUNTRY_NAME,
                                  c.getName(),
                                  c.getIso3())
                             );
                links.add(cLink);
            });
        }
    }
}
