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

import java.util.Iterator;

import org.junit.Test;

import de.gerdiproject.harvest.etls.AbstractIteratorETL;
import de.gerdiproject.harvest.etls.SeaAroundUsETLFactory;
import de.gerdiproject.harvest.etls.extractors.vos.GlobalRegionVO;
import de.gerdiproject.json.datacite.DataCiteJson;

/**
 * This class provides Unit Tests for the {@linkplain GlobalRegionExtractor}.
 *
 * @author Robin Weiss
 */
public class GlobalRegionExtractorTest extends AbstractSeaAroundUsExtractorTest<GlobalRegionVO>
{
    private static final String SUB_REGION_OUTPUT_RESOURCE = "output_subRegion.json";


    @Override
    protected AbstractIteratorETL<GlobalRegionVO, DataCiteJson> getEtl()
    {
        return SeaAroundUsETLFactory.createGlobalRegionETL();
    }


    @Override
    protected Class<GlobalRegionVO> getExtractedType()
    {
        return GlobalRegionVO.class;
    }


    /**
     * Tests if retrieving a global sub region returns the expected value object.
     */
    @Test
    public void testSubRegionExtraction()
    {
        final Iterator<GlobalRegionVO> globalIter = testedObject.extract();

        // skip first entry, it is different from the others
        globalIter.next();

        // retrieve sub region 1
        final GlobalRegionVO actualOutput = globalIter.next();
        final GlobalRegionVO expectedOutput = diskReader.getObject(
                                                  getResource(SUB_REGION_OUTPUT_RESOURCE).toString(),
                                                  getExtractedType());

        assertExpectedOutput(expectedOutput, actualOutput);
    }
}
