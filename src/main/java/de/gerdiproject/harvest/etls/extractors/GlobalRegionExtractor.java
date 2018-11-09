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
import java.util.Iterator;
import java.util.List;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsUrlConstants;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobalResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.GsonUtils;

/**
 * A {@linkplain AbstractIteratorExtractor} implementation for extracting all sub-regions of the Global Seas from SeaAroundUs.
 * <br>
 * see http://api.seaaroundus.org/api/v1/global/1

 * @author Robin Weiss
 */
public class GlobalRegionExtractor extends AbstractIteratorExtractor<SauGlobal>
{
    private final HttpRequester httpRequester = new HttpRequester(GsonUtils.createGeoJsonGsonBuilder().create(), StandardCharsets.UTF_8);
    private final List<String> globalSubRegionNames = SeaAroundUsRegionConstants.GLOBAL_SUB_REGION_SUFFIXES;
    private String version;


    @Override
    public void init(AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // get version from metadata
        final SauGlobalResponse globalResponse =
            httpRequester.getObjectFromUrl(
                createApiUrl(0),
                SauGlobalResponse.class);

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
    protected Iterator<SauGlobal> extractAll() throws ExtractorException
    {
        return new GlobalRegionIterator();
    }


    /**
     * Assembles the source URL for retrieving global ocean data.
     * @param subRegionId
     * @return the source URL for retrieving global ocean data
     */
    private String createApiUrl(int subRegionId)
    {
        String url = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                         SeaAroundUsDataCiteConstants.GLOBAL_REGION_NAME,
                         1);

        // add sub-region suffix, if necessary
        if (subRegionId != 0)
            url += String.format(SeaAroundUsUrlConstants.GLOBAL_SUB_REGION_API_SUFFIX, subRegionId);

        return url;
    }


    /**
     * This iterator iterates a list of sub region IDs of the global Ocean and
     * returns {@linkplain SauGlobal} objects.
     *
     * @author Robin Weiss
     */
    private class GlobalRegionIterator implements Iterator<SauGlobal>
    {
        private int subRegionId = 0;

        @Override
        public boolean hasNext()
        {
            return subRegionId < size();
        }


        @Override
        public SauGlobal next()
        {
            final SauGlobalResponse globalResponse = httpRequester.getObjectFromUrl(
                                                         createApiUrl(subRegionId),
                                                         SauGlobalResponse.class);

            final SauGlobal globalRegion = globalResponse.getData();
            globalRegion.setSubRegionId(subRegionId);
            globalRegion.setSubRegionNameSuffix(globalSubRegionNames.get(subRegionId));
            globalRegion.setVersion(globalResponse.getMetadata().getVersion());

            subRegionId++;
            return globalRegion;
        }
    }
}
