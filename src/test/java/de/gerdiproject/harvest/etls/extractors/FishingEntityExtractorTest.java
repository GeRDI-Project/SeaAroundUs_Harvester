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
package de.gerdiproject.harvest.etls.extractors;

import java.lang.reflect.Type;

import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.SeaAroundUsETLFactory;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.json.datacite.DataCiteJson;

/**
 * This class provides Unit Tests for the {@linkplain FishingEntityExtractor}.
 *
 * @author Robin Weiss
 */
public class FishingEntityExtractorTest extends AbstractSeaAroundUsExtractorTest<GenericResponse<SauFishingEntity>>
{
    @Override
    protected String getApiName()
    {
        return SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS.getRegionType().getUrlName();
    }


    @Override
    protected AbstractIteratorETL<GenericResponse<SauFishingEntity>, DataCiteJson> getEtl()
    {
        return SeaAroundUsETLFactory.createFishingEntityETL();
    }


    @Override
    protected Type getResponseType()
    {
        return SeaAroundUsRegionConstants.FISHING_ENTITY_RESPONSE_TYPE;
    }
}
