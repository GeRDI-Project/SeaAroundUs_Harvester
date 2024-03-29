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

import java.util.Iterator;
import java.util.List;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.etls.extractors.vos.FishingEntityVO;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntity;
import de.gerdiproject.harvest.seaaroundus.json.fishingentity.SauFishingEntityReduced;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.utils.data.HttpRequester;

/**
 * A {@linkplain AbstractIteratorExtractor} implementation for extracting all Fishing Entities of SeaAroundUs. <br>
 * see http://api.seaaroundus.org/api/v1/fishing-entity/
 *
 * @author Robin Weiss
 */
public class FishingEntityExtractor extends AbstractIteratorExtractor<FishingEntityVO>
{
    protected static final String REGION_API_NAME = SeaAroundUsRegionConstants.FISHING_ENTITY_PARAMS.getRegionType().getUrlName();
    protected final HttpRequester httpRequester = new HttpRequester();
    protected Iterator<SauFishingEntityReduced> fishingEntityListIterator;

    private String version;
    private int fishingEntityCount = -1;


    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // request all items
        final String apiUrl = SeaAroundUsDataCiteUtils.getAllRegionsUrl(REGION_API_NAME);

        final GenericResponse<List<SauFishingEntityReduced>> allFishingEntities =
            httpRequester.getObjectFromUrl(apiUrl, SeaAroundUsRegionConstants.ALL_FISHING_ENTITIES_RESPONSE_TYPE);

        this.fishingEntityListIterator = allFishingEntities.getData().iterator();
        this.fishingEntityCount = allFishingEntities.getData().size();

        // get version from metadata
        this.version = allFishingEntities.getMetadata().getVersion();
    }


    @Override
    public int size()
    {
        return fishingEntityCount;
    }


    @Override
    public String getUniqueVersionString()
    {
        return version;
    }


    @Override
    protected Iterator<FishingEntityVO> extractAll() throws ExtractorException
    {
        return new FishingEntityIterator();
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }


    /**
     * This iterator iterates a list of sub region IDs of the global Ocean and
     * returns {@linkplain SauFishingEntity} objects.
     *
     * @author Robin Weiss
     */
    private class FishingEntityIterator implements Iterator<FishingEntityVO>
    {
        @Override
        public boolean hasNext()
        {
            return fishingEntityListIterator.hasNext();
        }


        @Override
        public FishingEntityVO next()
        {
            final SauFishingEntityReduced baseInfo = fishingEntityListIterator.next();
            final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(REGION_API_NAME, baseInfo.getId());

            final GenericResponse<SauFishingEntity> fishingEntity =
                httpRequester.getObjectFromUrl(apiUrl, SeaAroundUsRegionConstants.FISHING_ENTITY_RESPONSE_TYPE);

            return new FishingEntityVO(fishingEntity, baseInfo);
        }
    }
}
