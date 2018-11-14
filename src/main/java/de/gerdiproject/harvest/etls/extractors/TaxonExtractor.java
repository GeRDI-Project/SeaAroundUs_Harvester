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
package de.gerdiproject.harvest.etls.extractors;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.catches.SauCatch;
import de.gerdiproject.harvest.seaaroundus.json.catches.SauCatchesResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxon;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonGroup;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonLevel;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxonReduced;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.seaaroundus.vos.EntryVO;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.GsonUtils;

/**
 * A {@linkplain AbstractIteratorExtractor} implementation for extracting all taxa from SeaAroundUs. <br>
 * <br>
 * see: http://api.seaaroundus.org/api/v1/taxa/
 *
 * @author Robin Weiss
 */
public class TaxonExtractor extends AbstractIteratorExtractor<SauTaxon>
{
    private final HttpRequester httpRequester = new HttpRequester(GsonUtils.createGeoJsonGsonBuilder().create(), StandardCharsets.UTF_8);

    private Iterator<SauTaxonReduced> taxonListIterator;
    private int size = -1;
    private String version;
    private Map<Integer, String> taxonGroups;
    private Map<Integer, String> taxonLevels;


    @Override
    public void init(AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // request all taxa
        final String apiUrl = SeaAroundUsDataCiteUtils.getAllRegionsUrl(SeaAroundUsRegionConstants.TAXA_API_NAME);
        final GenericResponse<List<SauTaxonReduced>> allTaxa = httpRequester.getObjectFromUrl(
                                                                   apiUrl,
                                                                   SeaAroundUsRegionConstants.ALL_TAXA_RESPONSE_TYPE);
        this.taxonListIterator = allTaxa.getData().iterator();
        this.size = allTaxa.getData().size();

        // get version from metadata
        this.version = allTaxa.getMetadata().getVersion();

        // get taxon groups and levels from other URLs
        this.taxonGroups = getTaxonGroups();
        this.taxonLevels = getTaxonLevels();
    }


    @Override
    public String getUniqueVersionString()
    {
        return version;
    }


    @Override
    public int size()
    {
        return size;
    }


    @Override
    protected Iterator<SauTaxon> extractAll() throws ExtractorException
    {
        return new TaxonIterator();
    }


    /**
     * Gets a map of taxon group names.
     *
     * @return a map of taxon group names
     */
    private Map<Integer, String> getTaxonGroups()
    {
        final String taxonGroupUrl =
            SeaAroundUsDataCiteUtils.getAllRegionsUrl(SeaAroundUsRegionConstants.TAXON_GROUP_API_NAME);

        final GenericResponse<List<SauTaxonGroup>> response =
            httpRequester.getObjectFromUrl(taxonGroupUrl, SeaAroundUsRegionConstants.TAXON_GROUP_RESPONSE_TYPE);

        // create map out of group list
        final Map<Integer, String> taxonLevelMap = new HashMap<>();
        response.getData().forEach((SauTaxonGroup g) ->
                                   taxonLevelMap.put(g.getTaxonGroupId(), g.getName())
                                  );
        return taxonLevelMap;
    }


    /**
     * Gets a map of taxon level names.
     *
     * @return a map of taxon level names
     */
    private Map<Integer, String> getTaxonLevels()
    {
        final String taxonGroupUrl =
            SeaAroundUsDataCiteUtils.getAllRegionsUrl(SeaAroundUsRegionConstants.TAXON_LEVEL_API_NAME);

        final GenericResponse<List<SauTaxonLevel>> response =
            httpRequester.getObjectFromUrl(taxonGroupUrl, SeaAroundUsRegionConstants.TAXON_LEVEL_RESPONSE_TYPE);

        final Map<Integer, String> taxonLevelMap = new HashMap<>();
        response.getData().forEach((SauTaxonLevel l) ->
                                   taxonLevelMap.put(l.getTaxonLevelId(), l.getName())
                                  );
        return taxonLevelMap;
    }


    /**
     * This iterator iterates a list of taxon keys and extracts more taxon related
     * data which is then added to the retrieved {@linkplain SauTaxon}.
     *
     * @author Robin Weiss
     */
    private class TaxonIterator implements Iterator<SauTaxon>
    {
        @Override
        public boolean hasNext()
        {
            return taxonListIterator.hasNext();
        }


        @Override
        public SauTaxon next()
        {
            final int key = taxonListIterator.next().getTaxonKey();
            final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                                      SeaAroundUsRegionConstants.TAXA_API_NAME,
                                      key);

            // retrieve and enrich taxon details
            final GenericResponse<SauTaxon> response = httpRequester.getObjectFromUrl(apiUrl, SeaAroundUsRegionConstants.TAXON_RESPONSE_TYPE);
            final SauTaxon taxon = response.getData();
            enrich(taxon);

            return taxon;
        }


        /**
         * Possibly extracts and adds more data to the extracted item.
         *
         * @param item the item that is to be enriched
         */
        private void enrich(SauTaxon item)
        {
            // set version
            item.setVersion(version);

            // get group and level name from maps
            item.setTaxonGroupName(taxonGroups.get(item.getTaxonGroupId()));
            item.setTaxonLevelName(taxonLevels.get(item.getTaxonLevelId()));

            // add catches
            final List<SauCatch> catches = new LinkedList<>();

            for (EntryVO dimension : SeaAroundUsDimensionConstants.DIMENSIONS_TAXON) {
                final String valueUrl = SeaAroundUsDataCiteUtils.getCatchesUrl(
                                            SeaAroundUsRegionConstants.TAXA_API_NAME,
                                            item.getTaxonKey(),
                                            SeaAroundUsRegionConstants.TAXON_MEASURE_VALUE,
                                            dimension);

                final SauCatchesResponse catchResponse = httpRequester.getObjectFromUrl(valueUrl, SauCatchesResponse.class);

                // not all taxa have catch data
                if (catchResponse != null && catchResponse.getData() != null)
                    catches.addAll(catchResponse.getData());
            }

            item.setCatches(catches);
        }

    }
}
