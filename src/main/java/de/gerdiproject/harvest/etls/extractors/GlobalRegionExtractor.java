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
import de.gerdiproject.harvest.etls.extractors.vos.GlobalRegionVO;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.utils.data.HttpRequester;

/**
 * A {@linkplain AbstractIteratorExtractor} implementation for extracting all sub-regions of the Global Seas from SeaAroundUs.
 * <br>
 * see http://api.seaaroundus.org/api/v1/global/1

 * @author Robin Weiss
 */
public class GlobalRegionExtractor extends AbstractIteratorExtractor<GlobalRegionVO>
{
    protected final HttpRequester httpRequester = new HttpRequester();
    protected final List<String> globalSubRegionNames = SeaAroundUsRegionConstants.GLOBAL_SUB_REGION_SUFFIXES;
    private String version;


    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // get version from metadata
        final GenericResponse<SauGlobal> globalResponse =
            httpRequester.getObjectFromUrl(
                createApiUrl(0),
                SeaAroundUsRegionConstants.GLOBAL_RESPONSE_TYPE);

        this.version = globalResponse.getMetadata().getVersion();
    }


    @Override
    public String getUniqueVersionString()
    {
        return version;
    }


    @Override
    public int size()
    {
        return globalSubRegionNames.size();
    }


    @Override
    protected Iterator<GlobalRegionVO> extractAll() throws ExtractorException
    {
        return new GlobalRegionIterator();
    }


    /**
     * Assembles the source URL for retrieving global ocean data.
     * @param subRegionId
     * @return the source URL for retrieving global ocean data
     */
    protected String createApiUrl(final int subRegionId)
    {
        final String regionUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                                     SeaAroundUsDataCiteConstants.GLOBAL_REGION_NAME,
                                     1);

        final StringBuilder urlBuilder = new StringBuilder(regionUrl);

        // add sub-region suffix, if necessary
        if (subRegionId != 0)
            urlBuilder.append(String.format(SeaAroundUsUrlConstants.GLOBAL_SUB_REGION_API_SUFFIX, subRegionId));

        return urlBuilder.toString();
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }


    /**
     * This iterator iterates a list of sub region IDs of the global Ocean and
     * returns {@linkplain SauGlobal} objects.
     *
     * @author Robin Weiss
     */
    private class GlobalRegionIterator implements Iterator<GlobalRegionVO>
    {
        private int subRegionId = 0; // NOPMD explicit initialization of 0

        @Override
        public boolean hasNext()
        {
            return subRegionId < size();
        }


        @Override
        public GlobalRegionVO next()
        {
            final GenericResponse<SauGlobal> globalResponse = httpRequester.getObjectFromUrl(
                                                                  createApiUrl(subRegionId),
                                                                  SeaAroundUsRegionConstants.GLOBAL_RESPONSE_TYPE);

            final String subRegionName = globalSubRegionNames.get(subRegionId);
            final GlobalRegionVO vo = new GlobalRegionVO(globalResponse, subRegionId, subRegionName);
            subRegionId++;

            return vo;
        }
    }
}
