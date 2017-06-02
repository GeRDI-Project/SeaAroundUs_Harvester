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
package de.gerdiproject.harvest.harvester.structure;

/**
 *
 * @author row
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


    public UrlVO( String primaryProductionViewSuffix,
            String primaryProductionDownloadSuffix,
            String stockStatusViewSuffix,
            String stockStatusDownloadSuffix,
            String marineTrophicIndexViewSuffix,
            String marineTrophicIndexDownloadSuffix,
            String catchesViewSuffix,
            String catchesDownloadSuffix )
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


    public String getPrimaryProductionViewUrl( String prefix, int regionId )
    {
        return prefix + String.format( primaryProductionViewSuffix, regionId );
    }


    public String getPrimaryProductionDownloadUrl( String prefix, int regionId )
    {
        return prefix + String.format( primaryProductionDownloadSuffix, regionId );

    }


    public String getStockStatusViewUrl( String prefix, int regionId )
    {
        return prefix + String.format( stockStatusViewSuffix, regionId );
    }


    public String getStockStatusDownloadUrl( String prefix, int regionId )
    {
        return prefix + String.format( stockStatusDownloadSuffix, regionId );
    }


    public String getMarineTrophicIndexViewUrl( String prefix, int regionId )
    {
        return prefix + String.format( marineTrophicIndexViewSuffix, regionId );
    }


    public String getMarineTrophicIndexDownloadUrl( String prefix, int regionId )
    {
        return prefix + String.format( marineTrophicIndexDownloadSuffix, regionId );
    }


    public String getCatchesViewUrl( String prefix, int regionId, String dimension, String measure )
    {
        return prefix + String.format( catchesViewSuffix, regionId, dimension, measure );
    }


    public String getCatchesDownloadUrl( String prefix, int regionId, String dimension, String measure )
    {
        return prefix + String.format( catchesDownloadSuffix, regionId, dimension, measure );
    }
}
