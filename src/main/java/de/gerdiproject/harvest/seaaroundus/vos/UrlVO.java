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
package de.gerdiproject.harvest.seaaroundus.vos;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsDimensionConstants;
import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;

/**
 * This value object contains URL suffixes and allows to retrieve complete URLs that are
 * used to retrieve SeaAroundUs data, or to link to SeaAroundUs data overview pages.
 *
 * @author Robin Weiss
 */
public class UrlVO
{
    private final String primaryProductionViewSuffix;
    private final String primaryProductionDownloadSuffix;

    private final String stockStatusViewSuffix;
    private final String stockStatusDownloadSuffix;

    private final String marineTrophicIndexViewSuffix;
    private final String marineTrophicIndexDownloadSuffix;

    private final String catchesViewSuffix;
    private final String catchesDownloadSuffix;


    /**
     * Constructor that requires suffixes for various URLs.
     *
     * @param primaryProductionViewSuffix the suffix to link to a primary production overview page
     * @param primaryProductionDownloadSuffix the suffix to download primary production data
     * @param stockStatusViewSuffix the suffix to link to a stock-status overview page
     * @param stockStatusDownloadSuffix the suffix to download stock-status data
     * @param marineTrophicIndexViewSuffix the suffix to link to the marine trophic index overview page
     * @param marineTrophicIndexDownloadSuffix the suffix to download marine trophic index data
     * @param catchesViewSuffix the suffix to link to a catches overview page
     * @param catchesDownloadSuffix the suffix to download catch data
     */
    public UrlVO(String primaryProductionViewSuffix,
                 String primaryProductionDownloadSuffix,
                 String stockStatusViewSuffix,
                 String stockStatusDownloadSuffix,
                 String marineTrophicIndexViewSuffix,
                 String marineTrophicIndexDownloadSuffix,
                 String catchesViewSuffix,
                 String catchesDownloadSuffix)
    {
        this.primaryProductionViewSuffix = primaryProductionViewSuffix;
        this.primaryProductionDownloadSuffix = primaryProductionDownloadSuffix;
        this.stockStatusViewSuffix = stockStatusViewSuffix;
        this.stockStatusDownloadSuffix = stockStatusDownloadSuffix;
        this.marineTrophicIndexViewSuffix = marineTrophicIndexViewSuffix;
        this.marineTrophicIndexDownloadSuffix = marineTrophicIndexDownloadSuffix;
        this.catchesViewSuffix = catchesViewSuffix;
        this.catchesDownloadSuffix = catchesDownloadSuffix;
    }


    /**
     * Retrieves a link to a primary production overview page.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     *
     * @return a link to a primary production overview page
     */
    public String getPrimaryProductionViewUrl(String prefix, int regionId)
    {
        return prefix + String.format(primaryProductionViewSuffix, regionId);
    }


    /**
     * Retrieves a download-link of primary production data.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     *
     * @return a download-link of primary production data
     */
    public String getPrimaryProductionDownloadUrl(String prefix, int regionId)
    {
        return prefix + String.format(primaryProductionDownloadSuffix, regionId);

    }


    /**
     * Retrieves a link to a stock-status overview page.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     *
     * @return a link to a stock-status overview page
     */
    public String getStockStatusViewUrl(String prefix, int regionId)
    {
        return prefix + String.format(stockStatusViewSuffix, regionId);
    }


    /**
     * Retrieves a download-link of stock-status data.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     *
     * @return a download-link of stock-status data
     */
    public String getStockStatusDownloadUrl(String prefix, int regionId)
    {
        return prefix + String.format(stockStatusDownloadSuffix, regionId);
    }


    /**
     * Retrieves a link to the marine trophic index overview page.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     *
     * @return a link to the marine trophic index overview page
     */
    public String getMarineTrophicIndexViewUrl(String prefix, int regionId)
    {
        return prefix + String.format(marineTrophicIndexViewSuffix, regionId);
    }


    /**
     * Retrieves a download-link of marine trophic index data.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     *
     * @return a download-link of marine trophic index data
     */
    public String getMarineTrophicIndexDownloadUrl(String prefix, int regionId)
    {
        return prefix + String.format(marineTrophicIndexDownloadSuffix, regionId);
    }


    /**
     * Retrieves a link to a catches overview page.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     * @param dimension the catch dimension (see {@linkplain SeaAroundUsDimensionConstants}
     * @param measure the measure of the catches (see {@linkplain SeaAroundUsRegionConstants}
     *
     * @return a link to a catches overview page
     */
    public String getCatchesViewUrl(String prefix, int regionId, String dimension, String measure)
    {
        return prefix + String.format(catchesViewSuffix, regionId, dimension, measure);
    }


    /**
     * Retrieves a download-link of catch data.
     *
     * @param prefix the SeaAroundUs path including version and language
     * @param regionId a unique identifier of the source region that is to be linked
     * @param dimension the catch dimension (see {@linkplain SeaAroundUsDimensionConstants}
     * @param measure the measure of the catches (see {@linkplain SeaAroundUsRegionConstants}
     *
     * @return a download-link of catch data
     */
    public String getCatchesDownloadUrl(String prefix, int regionId, String dimension, String measure)
    {
        return prefix + String.format(catchesDownloadSuffix, regionId, dimension, measure);
    }
}
