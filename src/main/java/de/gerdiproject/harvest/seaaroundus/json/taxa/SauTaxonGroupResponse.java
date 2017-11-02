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
package de.gerdiproject.harvest.seaaroundus.json.taxa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericResponse;

/**
 * This class represents the JSON response to a Seaaroundus taxon-group request.
 * <br>see http://api.seaaroundus.org/api/v1/taxon-group/
 *
 * @author Robin Weiss
 */
public final class SauTaxonGroupResponse extends GenericResponse<List<SauTaxonGroup>>
{
    /**
     * Maps the taxon group IDs to their respective names.
     *
     * @return a map of taxon group names
     */
    public Map<Integer, String> toMap()
    {
        // create map out of group list
        Map<Integer, String> taxonLevelMap = new HashMap<>();
        getData().forEach((SauTaxonGroup g) ->
                          taxonLevelMap.put(g.getTaxonGroupId(), g.getName())
                         );
        return taxonLevelMap;
    }
}