/*
 *  Copyright © 2019 Robin Weiss (http://www.gerdi-project.de/)
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
package de.gerdiproject.harvest.etls.extractors.constants;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.SeaAroundUsETLFactory;
import de.gerdiproject.harvest.etls.extractors.RegionExtractorTest;
import de.gerdiproject.harvest.etls.extractors.vos.RegionVO;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.eez.SauEezRegion;
import de.gerdiproject.harvest.seaaroundus.json.fao.SauFaoRegion;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import de.gerdiproject.harvest.seaaroundus.json.lme.SauLmeRegion;
import de.gerdiproject.harvest.seaaroundus.json.mariculture.SauMariculture;
import de.gerdiproject.harvest.seaaroundus.json.rfmo.SauRfmoRegion;
import de.gerdiproject.json.datacite.DataCiteJson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

/**
 * This class defines static parameters used by the {@linkplain RegionExtractorTest}.
 *
 * @author Robin Weiss
 */
public class RegionTestConstants
{
    public static final RegionTestData EEZ = new RegionTestData(
        SeaAroundUsRegionConstants.EEZ_PARAMS.getRegionType().getUrlName(),
    new TypeToken<RegionVO<SauEezRegion>>() {} .getType(),
    SeaAroundUsETLFactory::createEezETL);

    public static final RegionTestData FAO = new RegionTestData(
        SeaAroundUsRegionConstants.FAO_PARAMS.getRegionType().getUrlName(),
    new TypeToken<RegionVO<SauFaoRegion>>() {} .getType(),
    SeaAroundUsETLFactory::createFaoETL);

    public static final RegionTestData HIGH_SEAS = new RegionTestData(
        SeaAroundUsRegionConstants.HIGH_SEAS_PARAMS.getRegionType().getUrlName(),
    new TypeToken<RegionVO<GenericRegion>>() {} .getType(),
    SeaAroundUsETLFactory::createHighSeasETL);

    public static final RegionTestData LME = new RegionTestData(
        SeaAroundUsRegionConstants.LME_PARAMS.getRegionType().getUrlName(),
    new TypeToken<RegionVO<SauLmeRegion>>() {} .getType(),
    SeaAroundUsETLFactory::createLmeETL);

    public static final RegionTestData MARICULTURE = new RegionTestData(
        SeaAroundUsRegionConstants.MARICULTURE_API_NAME,
    new TypeToken<RegionVO<List<SauMariculture>>>() {} .getType(),
    SeaAroundUsETLFactory::createMaricultureETL);

    public static final RegionTestData RFMO = new RegionTestData(
        SeaAroundUsRegionConstants.RFMO_PARAMS.getRegionType().getUrlName(),
    new TypeToken<RegionVO<SauRfmoRegion>>() {} .getType(),
    SeaAroundUsETLFactory::createRfmoETL);


    /**
     * This class contains a set of parameters used by the {@linkplain RegionExtractorTest}.
     *
     * @author Robin Weiss
     */
    @Value
    public static class RegionTestData
    {
        private final String regionApiName;
        private final Type regionVoType;

        @Getter(AccessLevel.NONE)
        private final Supplier<AbstractIteratorETL<? extends RegionVO<?>, DataCiteJson>> etlCreator;


        @SuppressWarnings("unchecked")
        public AbstractIteratorETL<RegionVO<?>, ?> getETL()
        {
            return (AbstractIteratorETL<RegionVO<?>, ?>) etlCreator.get();
        }


        @Override
        public String toString()
        {
            return regionApiName;
        }
    }
}
