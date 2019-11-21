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

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.etls.extractors.CountryExtractor;
import de.gerdiproject.harvest.etls.extractors.FishingEntityExtractor;
import de.gerdiproject.harvest.etls.extractors.GlobalRegionExtractor;
import de.gerdiproject.harvest.etls.extractors.RegionExtractor;
import de.gerdiproject.harvest.etls.extractors.TaxonExtractor;
import de.gerdiproject.harvest.etls.extractors.vos.CountryVO;
import de.gerdiproject.harvest.etls.extractors.vos.FishingEntityVO;
import de.gerdiproject.harvest.etls.extractors.vos.GlobalRegionVO;
import de.gerdiproject.harvest.etls.extractors.vos.RegionVO;
import de.gerdiproject.harvest.etls.extractors.vos.TaxonVO;
import de.gerdiproject.harvest.etls.transformers.CountryTransformer;
import de.gerdiproject.harvest.etls.transformers.EezTransformer;
import de.gerdiproject.harvest.etls.transformers.FaoTransformer;
import de.gerdiproject.harvest.etls.transformers.FishingEntityTransformer;
import de.gerdiproject.harvest.etls.transformers.GlobalRegionTransformer;
import de.gerdiproject.harvest.etls.transformers.HighSeasTransformer;
import de.gerdiproject.harvest.etls.transformers.LmeTransformer;
import de.gerdiproject.harvest.etls.transformers.MaricultureTransformer;
import de.gerdiproject.harvest.etls.transformers.RfmoTransformer;
import de.gerdiproject.harvest.etls.transformers.TaxonTransformer;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauEezRegion;
import de.gerdiproject.harvest.seaaroundus.json.fao.SauFaoRegion;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.json.lme.SauLmeRegion;
import de.gerdiproject.harvest.seaaroundus.json.mariculture.SauMariculture;
import de.gerdiproject.harvest.seaaroundus.json.rfmo.SauRfmoRegion;
import de.gerdiproject.harvest.seaaroundus.json.taxa.SauTaxon;
import de.gerdiproject.json.datacite.DataCiteJson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This factory offers creators for SeaAroundUs ETLs.
 *
 * @author Robin Weiss
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeaAroundUsETLFactory // NOPMD the helper class name is suitable
{
    /**
     * Creates an ETL for harvesting {@linkplain SauCountry} metadata.
     *
     * @return an ETL for harvesting {@linkplain SauCountry} metadata
     */
    public static StaticIteratorETL<CountryVO, DataCiteJson> createCountryETL()
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
    public static StaticIteratorETL<TaxonVO, DataCiteJson> createTaxonETL()
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
    public static StaticIteratorETL<GlobalRegionVO, DataCiteJson> createGlobalRegionETL()
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
    public static StaticIteratorETL<FishingEntityVO, DataCiteJson> createFishingEntityETL()
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
    public static StaticIteratorETL<RegionVO<List<SauMariculture>>, DataCiteJson> createMaricultureETL()
    {
        return new StaticIteratorETL<>(
                   SeaAroundUsRegionConstants.MARICULTURE_ETL_NAME,
        new RegionExtractor<>(SeaAroundUsRegionConstants.MARICULTURE_API_NAME, new TypeToken<GenericResponse<List<SauMariculture>>>() {} .getType()),
        new MaricultureTransformer());
    }


    /**
     * Creates an ETL for harvesting LMEs.
     *
     * @return an ETL for harvesting LMEs
     */
    public static StaticIteratorETL<RegionVO<SauLmeRegion>, DataCiteJson> createLmeETL()
    {
        final LmeTransformer transformer = new LmeTransformer();
        final String etlName = transformer.getRegionParameters().getEtlName();
        final String urlName = transformer.getRegionParameters().getRegionType().getUrlName();
        final Type responseType = new TypeToken<GenericResponse<SauLmeRegion>>() {} .getType();
        final RegionExtractor<SauLmeRegion> extractor = new RegionExtractor<>(urlName, responseType);

        return new StaticIteratorETL<>(etlName, extractor, transformer);
    }


    /**
     * Creates an ETL for harvesting EEZs.
     *
     * @return an ETL for harvesting EEZs
     */
    public static StaticIteratorETL<RegionVO<SauEezRegion>, DataCiteJson> createEezETL()
    {
        final EezTransformer transformer = new EezTransformer();
        final String etlName = transformer.getRegionParameters().getEtlName();
        final String urlName = transformer.getRegionParameters().getRegionType().getUrlName();
        final Type responseType = new TypeToken<GenericResponse<SauEezRegion>>() {} .getType();
        final RegionExtractor<SauEezRegion> extractor = new RegionExtractor<>(urlName, responseType);

        return new StaticIteratorETL<>(etlName, extractor, transformer);
    }


    /**
     * Creates an ETL for harvesting RFMOs.
     *
     * @return an ETL for harvesting RFMOs
     */
    public static StaticIteratorETL<RegionVO<SauRfmoRegion>, DataCiteJson> createRfmoETL()
    {
        final RfmoTransformer transformer = new RfmoTransformer();
        final String etlName = transformer.getRegionParameters().getEtlName();
        final String urlName = transformer.getRegionParameters().getRegionType().getUrlName();
        final Type responseType = new TypeToken<GenericResponse<SauRfmoRegion>>() {} .getType();
        final RegionExtractor<SauRfmoRegion> extractor = new RegionExtractor<>(urlName, responseType);

        return new StaticIteratorETL<>(etlName, extractor, transformer);
    }


    /**
     * Creates an ETL for harvesting the HighSeas.
     *
     * @return an ETL for harvesting the HighSeas
     */
    public static StaticIteratorETL<RegionVO<GenericRegion>, DataCiteJson> createHighSeasETL()
    {
        final HighSeasTransformer transformer = new HighSeasTransformer();
        final String etlName = transformer.getRegionParameters().getEtlName();
        final String urlName = transformer.getRegionParameters().getRegionType().getUrlName();
        final Type responseType = new TypeToken<GenericResponse<GenericRegion>>() {} .getType();
        final RegionExtractor<GenericRegion> extractor = new RegionExtractor<>(urlName, responseType);

        return new StaticIteratorETL<>(etlName, extractor, transformer);
    }


    /**
     * Creates an ETL for harvesting FAOs.
     *
     * @return an ETL for harvesting FAOs
     */
    public static StaticIteratorETL<RegionVO<SauFaoRegion>, DataCiteJson> createFaoETL()
    {
        final FaoTransformer transformer = new FaoTransformer();
        final String etlName = transformer.getRegionParameters().getEtlName();
        final String urlName = transformer.getRegionParameters().getRegionType().getUrlName();
        final Type responseType = new TypeToken<GenericResponse<SauFaoRegion>>() {} .getType();
        final RegionExtractor<SauFaoRegion> extractor = new RegionExtractor<>(urlName, responseType);

        return new StaticIteratorETL<>(etlName, extractor, transformer);
    }
}
