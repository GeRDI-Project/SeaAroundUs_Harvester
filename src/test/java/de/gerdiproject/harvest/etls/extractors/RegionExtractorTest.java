/*
 *  Copyright © 2019 Robin Weiss (http://www.gerdi-project.de/)
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

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.extractors.constants.RegionTestConstants;
import de.gerdiproject.harvest.etls.extractors.constants.RegionTestConstants.RegionTestData;
import de.gerdiproject.harvest.etls.extractors.vos.RegionVO;
import lombok.RequiredArgsConstructor;

/**
 * This class provides Unit Tests for the {@linkplain RegionExtractor}.
 *
 * @author Robin Weiss
 */
@RunWith(Parameterized.class) @RequiredArgsConstructor
public class RegionExtractorTest extends AbstractSeaAroundUsExtractorTest<RegionVO<?>>
{
    private static final String OUTPUT_REGION_RESOURCE = "output_%s.json";

    @Parameters(name = "{0}")
    public static Object[] getParameters()
    {
        return new Object[] {
                   RegionTestConstants.EEZ,
                   RegionTestConstants.FAO,
                   RegionTestConstants.HIGH_SEAS,
                   RegionTestConstants.LME,
                   RegionTestConstants.MARICULTURE,
                   RegionTestConstants.RFMO
               };
    }


    private final RegionTestData testParameters;


    @Override
    protected AbstractIteratorETL<RegionVO<?>, ?> getEtl()
    {
        return testParameters.getETL();
    }


    @Override
    protected Type getExtractedType()
    {
        return testParameters.getRegionVoType();
    }


    @Override
    protected RegionVO<?> getExpectedOutput()
    {
        final String regionPath = getResource(String.format(OUTPUT_REGION_RESOURCE, testParameters.getRegionApiName())).toString();
        System.out.println(getExtractedType());
        return diskReader.getObject(
                   regionPath,
                   getExtractedType());
    }
}
