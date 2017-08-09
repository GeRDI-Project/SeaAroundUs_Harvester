/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
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
package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;


import de.gerdiproject.harvest.harvester.AbstractJsonArrayHarvester;
import de.gerdiproject.harvest.harvester.structure.Entry;
import de.gerdiproject.harvest.harvester.structure.JsonConst;
import de.gerdiproject.harvest.harvester.structure.SeaAroundUsConst;
import de.gerdiproject.harvest.harvester.structure.UrlVO;
import de.gerdiproject.json.IJsonArray;
import de.gerdiproject.json.IJsonObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author row
 */
public class GenericRegionHarvester extends AbstractJsonArrayHarvester
{
    private final static Entry MEASURE_VALUE = new Entry("value", "Real 2010 value (US$)");
    private final static Entry MEASURE_TONNAGE = new Entry("tonnage", "Catches");

    private final static String NAME_FORMAT = "%C%sHarvester";
    private final static String MARINE_TROPHIC_INDEX_LABEL = "Region-based Marine Trophic Index of the catch in the waters of %s";
    private final static String LONG_TITLE = "%s (%s)";

    protected final static String CATCHES_LABEL = "%s by %s %s %s";
    protected final static String PRIMARY_PRODUCTION_LABEL = "Primary Production Required for catches %s %s";
    protected final static String STOCK_STATUS_LABEL = "Stock status %s %s";

    static final Entry[] MEASURES = { MEASURE_VALUE, MEASURE_TONNAGE };

    protected final String viewUrlPrefix;
    protected String downloadUrlPrefix;
    protected final Entry regionType;
    protected final List<Entry> dimensions;
    protected final UrlVO urls;
    protected final int documentsPerEntry;


    /**
     *
     * @param harvestedDocuments
     *            the list to which harvested documents are added
     * @param regionType
     *            the type of the regions that are to be harvested
     * @param dimensions
     *            the dimensions that are available for each region
     * @param urls
     *            a value object of URLs that are used to retrieve region
     *            metadata
     */
    public GenericRegionHarvester(Entry regionType, List<Entry> dimensions, UrlVO urls)
    {
        this(regionType, dimensions, urls, 3 + dimensions.size() * MEASURES.length);
    }


    /**
     *
     * @param harvestedDocuments
     *            the list to which harvested documents are added
     * @param regionType
     *            the type of the regions that are to be harvested
     * @param dimensions
     *            the dimensions that are available for each region
     * @param urls
     *            a value object of URLs that are used to retrieve region
     *            metadata
     * @param numberOfDocumentsPerEntry
     *            the maximum number of documents that can be retrieved from a
     *            single entry
     */
    public GenericRegionHarvester(Entry regionType, List<Entry> dimensions, UrlVO urls, int numberOfDocumentsPerEntry)
    {
        super(
            String.format(NAME_FORMAT, regionType.urlName.charAt(0), regionType.urlName.substring(1)),
            numberOfDocumentsPerEntry);

        this.viewUrlPrefix = String.format(SeaAroundUsConst.VIEW_URL_PREFIX, regionType.urlName);
        this.regionType = regionType;
        this.dimensions = dimensions;
        this.urls = urls;
        this.documentsPerEntry = 3 + dimensions.size() * MEASURES.length;
    }


    @Override
    public void setProperty(String key, String value)
    {
        if (SeaAroundUsConst.PROPERTY_URL.equals(key))
            downloadUrlPrefix = value + String.format(SeaAroundUsConst.REGION_IDS_URL, regionType.urlName);
    }


    @Override
    protected IJsonArray getJsonArray()
    {
        IJsonObject regionData = httpRequester.getJsonObjectFromUrl(downloadUrlPrefix);
        IJsonArray regionArray = regionData.getJsonArray(JsonConst.FEATURES);

        return regionArray;
    }


    @Override
    protected List<IJsonObject> harvestJsonArrayEntry(IJsonObject entry)
    {
        // read region ID
        int regionId = getRegionId(entry);

        // get region info from URL
        IJsonObject regionObject = httpRequester.getJsonObjectFromUrl(downloadUrlPrefix + regionId);

        // get name
        String regionName = getRegionName(regionObject);

        // get shape coordinates of the region
        IJsonArray geoData = getGeoData(regionObject);

        // get default search tags
        List<String> defaultTags = getDefaultSearchTags(regionObject);

        // create and add documents
        return createDocuments(regionId, regionObject, regionName, geoData, defaultTags);

    }


    protected List<IJsonObject> createDocuments(int regionId, IJsonObject regionObject, String regionName,
                                                IJsonArray geoData, List<String> defaultTags)
    {
        List<IJsonObject> documentList = new ArrayList<>(numberOfDocumentsPerEntry);

        // Catches and Value
        for (Entry measure : MEASURES) {
            for (Entry dimension : dimensions) {
                documentList.add(
                    createCatchesByDimensionDocument(
                        regionId,
                        regionName,
                        dimension,
                        measure,
                        geoData,
                        defaultTags));
            }
        }

        // Primary Production
        documentList.add(createPrimaryProductionDocument(regionId, regionName, geoData, defaultTags));

        // Stock Status
        documentList.add(createStockStatusDocument(regionId, regionName, geoData, defaultTags));

        // Marine Trophic Index
        documentList.add(createMarineTrophicIndexDocument(regionId, regionName, geoData, defaultTags));

        return documentList;
    }


    protected IJsonObject createPrimaryProductionDocument(int regionId, String regionName, IJsonArray geoData,
                                                          List<String> defaultTags)
    {
        String label = String.format(PRIMARY_PRODUCTION_LABEL, regionType.displayName, regionName);
        String apiUrl = urls.getPrimaryProductionDownloadUrl(downloadUrlPrefix, regionId);
        String viewUrl = urls.getPrimaryProductionViewUrl(viewUrlPrefix, regionId);

        return createDocument(label, apiUrl, viewUrl, geoData, defaultTags, JsonConst.COUNTRIES);
    }


    protected IJsonObject createStockStatusDocument(int regionId, String regionName, IJsonArray geoData,
                                                    List<String> defaultTags)
    {
        String label = String.format(STOCK_STATUS_LABEL, regionType.displayName, regionName);
        String apiUrl = urls.getStockStatusDownloadUrl(downloadUrlPrefix, regionId);
        String viewUrl = urls.getStockStatusViewUrl(viewUrlPrefix, regionId);

        return createDocument(label, apiUrl, viewUrl, geoData, defaultTags, JsonConst.CSS);
    }


    protected IJsonObject createMarineTrophicIndexDocument(int regionId, String regionName, IJsonArray geoData,
                                                           List<String> defaultTags)
    {

        String label = String.format(MARINE_TROPHIC_INDEX_LABEL, regionName);
        String apiUrl = urls.getMarineTrophicIndexDownloadUrl(downloadUrlPrefix, regionId);
        String viewUrl = urls.getMarineTrophicIndexViewUrl(viewUrlPrefix, regionId);

        return createDocument(label, apiUrl, viewUrl, geoData, defaultTags);
    }


    protected IJsonObject createCatchesByDimensionDocument(int regionId, String regionName, Entry dimension,
                                                           Entry measure, IJsonArray geoData, List<String> defaultTags)
    {
        String label = String.format(
                           CATCHES_LABEL,
                           measure.displayName,
                           dimension.displayName,
                           regionType.displayName,
                           regionName);
        String apiUrl = urls.getCatchesDownloadUrl(downloadUrlPrefix, regionId, dimension.urlName, measure.urlName);
        String viewUrl = urls.getCatchesViewUrl(viewUrlPrefix, regionId, dimension.urlName, measure.urlName);

        return createDocument(label, apiUrl, viewUrl, geoData, defaultTags);
    }


    protected IJsonObject createDocument(String label, String apiUrl, String viewUrl, IJsonArray geoData,
                                         List<String> defaultTags, String arrayKey)
    {
        IJsonObject document = null;
        IJsonArray valueArray = null;

        if (arrayKey != null) {
            IJsonObject infoObject = httpRequester.getJsonObjectFromUrl(apiUrl);

            if (infoObject != null && !infoObject.isNull(arrayKey))
                valueArray = infoObject.getJsonArray(arrayKey);
        } else
            valueArray = httpRequester.getJsonArrayFromUrl(apiUrl);

        // if the download URL is not working, do not add the document
        if (valueArray != null) {
            // retrieve years and search tags
            IJsonArray years = getYearsFromValueArray(valueArray);

            // get key names and merge them with default tags
            List<String> keyNames = getNamesFromValueArray(valueArray);
            IJsonArray searchTags = jsonBuilder.createArrayFromLists(defaultTags, keyNames);

            // try to offer a CSV file download
            IJsonArray downloadUrls = jsonBuilder.createArrayFromObjects(apiUrl + SeaAroundUsConst.CSV_FORM);

            document = searchIndexFactory.createSearchableDocument(
                           label,
                           null,
                           viewUrl,
                           downloadUrls,
                           SeaAroundUsConst.LOGO_URL,
                           null,
                           geoData,
                           years,
                           searchTags);
        }

        return document;
    }


    protected final IJsonObject createDocument(String label, String apiUrl, String viewUrl, IJsonArray geoData,
                                               List<String> defaultTags)
    {

        return createDocument(label, apiUrl, viewUrl, geoData, defaultTags, null);
    }


    private IJsonArray getYearsFromValueArray(IJsonArray valueArray)
    {
        final IJsonArray years = jsonBuilder.createArray();
        final IJsonObject firstRegion = valueArray.getJsonObject(0);

        if (firstRegion != null) {
            IJsonArray catchValues = firstRegion.getJsonArray(JsonConst.VALUES);

            if (catchValues != null) {
                catchValues.forEach((catchValue) -> {
                    years.add(((IJsonArray) catchValue).getInt(0));
                });
            }
        }

        return years;
    }


    private List<String> getNamesFromValueArray(IJsonArray valueArray)
    {
        List<String> nameList = new LinkedList<>();

        for (Object attribute : valueArray) {
            IJsonObject obj = (IJsonObject) attribute;

            // add key, if it exists
            String key = obj.getString(JsonConst.KEY, null);

            if (key != null)
                nameList.add(key);

            // add scientificName, if it exists
            String scientificName = obj.getString(JsonConst.SCIENTIFIC_NAME, null);

            if (scientificName != null)
                nameList.add(scientificName);
        }

        return nameList;
    }


    protected String getRegionName(IJsonObject regionObject)
    {
        String regionName = regionObject.getString(JsonConst.TITLE);

        // check if region name is just an abbreviation, add the complete name
        // as well
        String longRegionName = regionObject.getString(JsonConst.LONG_TITLE, null);

        if (longRegionName != null)
            regionName = String.format(LONG_TITLE, longRegionName, regionName);

        return regionName;
    }


    protected int getRegionId(IJsonObject region)
    {
        IJsonObject properties = region.getJsonObject(JsonConst.PROPERTIES);
        return properties.getInt(JsonConst.REGION_ID);
    }


    protected IJsonArray getGeoData(IJsonObject regionObject)
    {
        IJsonObject geojson = regionObject.getJsonObject(JsonConst.GEOJSON);

        // check if the geojson exists and add it to an array, if so
        if (geojson != null && geojson.isNonEmptyValue(JsonConst.COORDINATES))
            return jsonBuilder.createArrayFromObjects(geojson);

        return null;
    }


    protected List<String> getDefaultSearchTags(IJsonObject regionObject)
    {
        List<String> tags = new LinkedList<>();
        IJsonArray metrics = regionObject.getJsonArray(JsonConst.METRICS);

        if (metrics != null) {
            for (Object attribute : metrics) {
                IJsonObject metric = (IJsonObject) attribute;
                double value = metric.getDouble(JsonConst.VALUE);

                if (value != 0.0)
                    tags.add(metric.getString(JsonConst.TITLE, null));
            }
        }

        return tags;
    }


    /**
     * Not required, because this list is not visible via REST.
     *
     * @return null
     */
    @Override
    public List<String> getValidProperties()
    {
        return null;
    }
}
