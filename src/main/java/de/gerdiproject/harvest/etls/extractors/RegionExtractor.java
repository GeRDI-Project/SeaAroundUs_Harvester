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
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.Feature;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureCollection;
import de.gerdiproject.harvest.seaaroundus.json.generic.FeatureProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.json.global.SauGlobal;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.GsonUtils;

/**
 * This extractor retrieves region objects from a feature collection of generic regions.
 *
 * @author Robin Weiss
 */
public class RegionExtractor <EXOUT> extends AbstractIteratorExtractor<GenericResponse<EXOUT>>
{
    private final HttpRequester httpRequester;

    private final Type singleEntryResponseType;
    private final String regionApiName;

    private Iterator<Feature<FeatureProperties>> baseListIterator;
    private String version;
    private int size = -1;


    public RegionExtractor(String regionApiName)
    {
        super();
        this.httpRequester = new HttpRequester(GsonUtils.createGeoJsonGsonBuilder().create(), StandardCharsets.UTF_8);
        this.singleEntryResponseType = GsonUtils.<GenericResponse<EXOUT>>createType();
        this.regionApiName = regionApiName;
    }


    @Override
    public void init(AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // request all countries
        final String apiUrl = SeaAroundUsDataCiteUtils.getAllRegionsUrl(regionApiName);
        final GenericResponse<FeatureCollection<FeatureProperties>> allCountries =
            httpRequester.getObjectFromUrl(apiUrl, SeaAroundUsRegionConstants.ALL_REGIONS_RESPONSE_TYPE);

        // get version from metadata
        this.version = allCountries.getMetadata().getVersion();

        this.size = allCountries.getData().getFeatures().size();
        this.baseListIterator = allCountries.getData().getFeatures().iterator();
    }


    @Override
    public int size()
    {
        return size;
    }


    @Override
    public String getUniqueVersionString()
    {
        return version;
    }


    @Override
    protected Iterator<GenericResponse<EXOUT>> extractAll() throws ExtractorException
    {
        return new EntryIterator();
    }


    /**
     * This iterator iterates a list of sub region IDs of the global Ocean and
     * returns {@linkplain SauGlobal} objects.
     *
     * @author Robin Weiss
     */
    private class EntryIterator implements Iterator<GenericResponse<EXOUT>>
    {
        @Override
        public boolean hasNext()
        {
            return baseListIterator.hasNext();
        }


        @Override
        public GenericResponse<EXOUT> next()
        {
            final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                                      regionApiName,
                                      baseListIterator.next().getProperties().getRegionId());
            return httpRequester.getObjectFromUrl(apiUrl, singleEntryResponseType);
        }
    }
}
