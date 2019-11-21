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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.etls.extractors.vos.CountryVO;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountry;
import de.gerdiproject.harvest.seaaroundus.json.country.SauCountryProperties;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;
import de.gerdiproject.harvest.seaaroundus.utils.SeaAroundUsDataCiteUtils;
import de.gerdiproject.harvest.utils.data.HttpRequester;
import de.gerdiproject.json.geo.Feature;
import de.gerdiproject.json.geo.FeatureCollection;

/**
 * A {@linkplain AbstractIteratorExtractor} implementation for extracting all countries from SeaAroundUs. <br>
 * <br>
 * see: http://api.seaaroundus.org/api/v1/country/
 *
 * @author Robin Weiss
 */
public class CountryExtractor extends AbstractIteratorExtractor<CountryVO>
{
    protected final HttpRequester httpRequester = new HttpRequester();

    protected final Map<Integer, List<Feature<SauCountryProperties>>> countryMap = new HashMap<>();
    private int countryCount = -1;
    private String version;


    @Override
    public void init(final AbstractETL<?, ?> etl)
    {
        super.init(etl);

        // request all countries
        final String apiUrl = SeaAroundUsDataCiteUtils.getAllRegionsUrl(SeaAroundUsRegionConstants.COUNTRY_API_NAME);
        final GenericResponse<FeatureCollection<SauCountryProperties>> allCountries =
            httpRequester.getObjectFromUrl(apiUrl, SeaAroundUsRegionConstants.ALL_COUNTRIES_RESPONSE_TYPE);

        // set up the map of country sub-regions
        countryMap.clear();
        this.countryCount = 0;

        for (final Feature<SauCountryProperties> basicCountry : allCountries.getData().getFeatures()) {
            final int countryId = basicCountry.getProperties().getCNumber();

            List<Feature<SauCountryProperties>> subRegions = countryMap.get(countryId);

            if (subRegions == null) {
                subRegions = new LinkedList<>();
                countryMap.put(countryId, subRegions);
                this.countryCount++;
            }

            subRegions.add(basicCountry);
        }


        // get version from metadata
        this.version = allCountries.getMetadata().getVersion();
    }


    @Override
    public String getUniqueVersionString()
    {
        return version;
    }


    @Override
    public int size()
    {
        return countryCount;
    }


    @Override
    protected Iterator<CountryVO> extractAll() throws ExtractorException
    {
        return new CountryIterator();
    }


    @Override
    public void clear()
    {
        // nothing to clean up
    }


    /**
     * This iterator iterates through the map of countries and extracts related
     * data which is then added to the retrieved {@linkplain SauCountry}.
     *
     * @author Robin Weiss
     */
    private class CountryIterator implements Iterator<CountryVO>
    {
        private final Iterator<List<Feature<SauCountryProperties>>> countryMapIterator = countryMap.values().iterator();
        private final Type responseType = new TypeToken<GenericResponse<SauCountry>>() {} .getType();

        @Override
        public boolean hasNext()
        {
            return countryMapIterator.hasNext();
        }


        @Override
        public CountryVO next()
        {
            final List<Feature<SauCountryProperties>> subRegions = countryMapIterator.next();
            final int key = subRegions.get(0).getProperties().getCNumber();
            final String apiUrl = SeaAroundUsDataCiteUtils.getRegionEntryUrl(
                                      SeaAroundUsRegionConstants.COUNTRY_API_NAME,
                                      key);

            // retrieve and enrich country
            final GenericResponse<SauCountry> country = httpRequester.getObjectFromUrl(
                                                            apiUrl,
                                                            responseType);

            return new CountryVO(country, subRegions);
        }
    }
}
