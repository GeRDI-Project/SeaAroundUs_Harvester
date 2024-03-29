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

import java.lang.reflect.Type;
import java.util.Iterator;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.etls.extractors.vos.RegionVO;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.generic.SauFeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.geo.Feature;
import de.gerdiproject.json.geo.FeatureCollection;

/**
 * This extractor retrieves region objects from a feature collection of generic regions.
 *
 * @param <T> the type of the extracted region
 *
 * @author Robin Weiss
 */
public class RegionExtractor <T> extends AbstractIteratorExtractor<RegionVO<T>>
{
    protected final HttpRequester httpRequester;

    protected final Type responseType;
    protected final String regionApiName;

    protected Iterator<Feature<SauFeatureProperties>> baseListIterator;
    private String version;
    private int regionCount = -1;


    /**
     * Constructor that requires the API key of the region that is to be harvested.
     *
     * @param regionApiName the API key of the region that is to be harvested
     * @param responseType the type of a single region server response
     */
    public RegionExtractor(final String regionApiName, final Type responseType)
    {
        super();
        this.httpRequester = new HttpRequester();
        this.responseType = responseType;

        this.regionApiName = regionApiName;
    }


    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // request all regions
        final String apiUrl = SeaAroundUsDataCiteUtils.getAllRegionsUrl(regionApiName);
        final GenericResponse<FeatureCollection<SauFeatureProperties>> allRegions =
            httpRequester.getObjectFromUrl(apiUrl, SeaAroundUsRegionConstants.ALL_REGIONS_RESPONSE_TYPE);

        // get version from metadata
        this.version = allRegions.getMetadata().getVersion();

        this.regionCount = allRegions.getData().getFeatures().size();
        this.baseListIterator = allRegions.getData().getFeatures().iterator();
    }


    @Override
    public int size()
    {
        return regionCount;
    }


    @Override
    public String getUniqueVersionString()
    {
        return version;
    }


    @Override
    protected Iterator<RegionVO<T>> extractAll() throws ExtractorException
    {
        return new EntryIterator();
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
    private class EntryIterator implements Iterator<RegionVO<T>>
    {
        @Override
        public boolean hasNext()
        {
            return baseListIterator.hasNext();
        }


        @Override
        public RegionVO<T> next()
        {
            // retrieve feature
            final Feature<SauFeatureProperties> feature = baseListIterator.next();

            // retrieve region
            final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                                      regionApiName,
                                      feature.getProperties().getRegionId());

            final GenericResponse<T> response = httpRequester.getObjectFromUrl(apiUrl, responseType);

            return new RegionVO<>(response, feature);
        }
    }
}
