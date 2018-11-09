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
package de.gerdiproject.harvest.etls.transformers;

import de.gerdiproject.harvest.seaaroundus.constants.SeaAroundUsRegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.lme.SauLmeRegion;

/**
 * This {@linkplain AbstractRegionTransformer} transforms all FAOs of SeaAroundUs to documents.
 * <br>see http://api.seaaroundus.org/api/v1/fao/
 *
 * @author Robin Weiss
 */
public class FaoTransformer extends AbstractRegionTransformer<SauLmeRegion>
{
    /**
     * Constructor that sets region parameters.
     */
    public FaoTransformer()
    {
        super(SeaAroundUsRegionConstants.FAO_PARAMS);
    }
}
