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
package de.gerdiproject.harvest.etls;

import java.util.List;

import de.gerdiproject.harvest.etls.extractors.CountryExtractor;
import de.gerdiproject.harvest.etls.extractors.FishingEntityExtractor;
import de.gerdiproject.harvest.etls.extractors.GlobalRegionExtractor;
import de.gerdiproject.harvest.etls.extractors.RegionExtractor;
import de.gerdiproject.harvest.etls.extractors.TaxonExtractor;
import de.gerdiproject.harvest.etls.transformers.AbstractRegionTransformer;
import de.gerdiproject.harvest.etls.transformers.CountryTransformer;
import de.gerdiproject.harvest.etls.transformers.EezTransformer;
import de.gerdiproject.harvest.etls.transformers.FaoTransformer;
import de.gerdiproject.harvest.etls.transformers.FishingEntityTransformer;
import de.gerdiproject.harvest.etls.transformers.GlobalRegionTransformer;
import de.gerdiproject.harvest.etls.transformers.HighSeasTransformer;
import de.gerdiproject.harvest.etls.transformers.MaricultureTransformer;
import de.gerdiproject.harvest.etls.transformers.RfmoTransformer;
import de.gerdiproject.harvest.etls.transformers.TaxonTransformer;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.json.mariculture.SauMariculture;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxon;
import de.gerdiproject.json.datacite.DataCiteJson;

/**
 * This factory offers creators for SeaAroundUs ETLs.
 *
 * @author Robin Weiss
 */
public class SeaAroundUsETLFactory
{
    /**
     * Private constructor because all methods are static.
     */
    private SeaAroundUsETLFactory()
    {

    }


    /**
     * Creates an ETL for harvesting {@linkplain SauCountry} metadata.
     *
     * @return an ETL for harvesting {@linkplain SauCountry} metadata
     */
    public static StaticIteratorETL<?, ?> createCountryETL()
    {
        return new StaticIteratorETL<>(
                   SeaAroundUsRegionConstants.COUNTRY_ETL_NAME,
                   new CountryExtractor(),
                   new CountryTransformer());
    }


    /**
     * Creates an ETL for harvesting {@linkplain SauTaxon} metadata.
     *
     * @return an ETL for harvesting {@linkplain SauTaxon} metadata
     */
    public static StaticIteratorETL<?, ?> createTaxonETL()
    {
        return new StaticIteratorETL<>(
                   SeaAroundUsRegionConstants.TAXON_ETL_NAME,
                   new TaxonExtractor(),
                   new TaxonTransformer());
    }


    /**
     * Creates an ETL for harvesting {@linkplain SauGlobal} metadata.
     *
     * @return an ETL for harvesting {@linkplain SauGlobal} metadata
     */
    public static StaticIteratorETL<?, ?> createGlobalOceanETL()
    {
        return new StaticIteratorETL<>(
                   SeaAroundUsRegionConstants.GLOBAL_ETL_NAME,
                   new GlobalRegionExtractor(),
                   new GlobalRegionTransformer());
    }


    /**
     * Creates an ETL for harvesting {@linkplain SauFishingEntity} metadata.
     *
     * @return an ETL for harvesting {@linkplain SauFishingEntity} metadata
     */
    public static StaticIteratorETL<?, ?> createFishingEntityETL()
    {
        return new StaticIteratorETL<>(
                   SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS.getEtlName(),
                   new FishingEntityExtractor(),
                   new FishingEntityTransformer());
    }


    /**
     * Creates an ETL for harvesting {@linkplain SauMariculture} metadata.
     *
     * @return an ETL for harvesting {@linkplain SauMariculture} metadata
     */
    public static StaticIteratorETL<GenericResponse<List<SauMariculture>>, DataCiteJson> createMaricultureETL()
    {
        return new StaticIteratorETL<>(
                   SeaAroundUsRegionConstants.MARICULTURE_ETL_NAME,
                   new RegionExtractor<>(SeaAroundUsRegionConstants.MARICULTURE_API_NAME),
                   new MaricultureTransformer());
    }


    /**
     * Creates an ETL for harvesting LMEs.
     *
     * @return an ETL for harvesting LMEs
     */
    public static StaticIteratorETL<?, ?> createLmeETL()
    {
        return createRegionETL(new EezTransformer());
    }


    /**
     * Creates an ETL for harvesting EEZs.
     *
     * @return an ETL for harvesting EEZs
     */
    public static StaticIteratorETL<?, ?> createEezETL()
    {
        return createRegionETL(new EezTransformer());
    }


    /**
     * Creates an ETL for harvesting RFMOs.
     *
     * @return an ETL for harvesting RFMOs
     */
    public static StaticIteratorETL<?, ?> createRfmoETL()
    {
        return createRegionETL(new RfmoTransformer());
    }


    /**
     * Creates an ETL for harvesting the HighSeas.
     *
     * @return an ETL for harvesting the HighSeas
     */
    public static StaticIteratorETL<?, ?> createHighSeasETL()
    {
        return createRegionETL(new HighSeasTransformer());
    }


    /**
     * Creates an ETL for harvesting FAOs.
     *
     * @return an ETL for harvesting FAOs
     */
    public static StaticIteratorETL<?, ?> createFaoETL()
    {
        return createRegionETL(new FaoTransformer());
    }


    /**
     * Creates an ETL for a specified region.
     *
     * @param transformer the transformer of the ETL
     *
     * @return an ETL for a specified region
     */
    private static <T extends GenericRegion> StaticIteratorETL<GenericResponse<T>, DataCiteJson> createRegionETL(AbstractRegionTransformer<T> transformer)
    {
        return new StaticIteratorETL<>(
                   transformer.getRegionParameters().getEtlName(),
                   new RegionExtractor<>(transformer.getRegionParameters().getRegionType().urlName),
                   transformer);
    }
}
