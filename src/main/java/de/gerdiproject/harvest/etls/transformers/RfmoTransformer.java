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

import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.rfmo.SauRfmoContractingCountry;
import de.gerdiproject.harvest.seaaroundus.json.rfmo.SauRfmoRegion;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonReduced;
import de.gerdiproject.json.datacite.Subject;
import de.gerdiproject.json.datacite.extension.generic.WebLink;

/**
 * This {@linkplain AbstractRegionTransformer} transforms all RFMOs of SeaAroundUs to documents.
 * <br>see http://api.seaaroundus.org/api/v1/rfmo/
 *
 * @author Robin Weiss
 */
public class RfmoTransformer extends AbstractRegionTransformer<SauRfmoRegion>
{
    /**
     * Constructor that sets region parameters.
     */
    public RfmoTransformer()
    {
        super(SeaAroundUsRegionConstants.RFMO_PARAMS);
    }


    @Override
    protected List<WebLink> createWebLinks(final SauRfmoRegion regionObject)
    {
        final List<WebLink> links = super.createWebLinks(regionObject);

        final List<SauRfmoContractingCountry> countries = regionObject.getContractingCountries();

        if (countries != null && !countries.isEmpty()) {
            countries.forEach((final SauRfmoContractingCountry c) -> {

                final WebLink cLink = new WebLink(c.getFacpUrl());
                cLink.setName(String.format(
                                  SeaAroundUsDataCiteConstants.CONTRACTING_COUNTRY_NAME,
                                  c.getName(),
                                  c.getIso3())
                             );
                links.add(cLink);
            });
        }

        return links;
    }


    @Override
    protected List<Subject> createSubjects(final SauRfmoRegion regionObject)
    {
        final List<Subject> subjects = super.createSubjects(regionObject);

        addTaxaSubjects(subjects, regionObject.getPrimaryTaxa());
        addTaxaSubjects(subjects, regionObject.getSecondaryTaxa());

        return subjects;
    }


    /**
     * Adds {@linkplain Subject}s of taxa of the RFMO to the document's weblinks.
     *
     * @param subjects the subject list of the document that is to be enriched
     * @param taxa a list of taxon data
     */
    private void addTaxaSubjects(final List<Subject> subjects, final List<SauTaxonReduced> taxa)
    {
        if (taxa != null && !taxa.isEmpty()) {

            taxa.forEach((final SauTaxonReduced taxon) -> {

                final String commonName = taxon.getCommonName();

                if (commonName != null)
                    subjects.add(new Subject(commonName));

                final String scientificName = taxon.getScientificName();
                if (scientificName != null)
                    subjects.add(new Subject(scientificName));
            });
        }
    }

}
