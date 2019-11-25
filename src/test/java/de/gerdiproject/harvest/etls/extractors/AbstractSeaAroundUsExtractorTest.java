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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;

import de.gerdiproject.harvest.SeaAroundUsContextListener;
import de.gerdiproject.harvest.application.ContextListener;
import de.gerdiproject.harvest.utils.data.DiskIO;
import de.gerdiproject.json.GsonUtils;

/**
 * This abstract class provides Unit Tests for SeaAroundUs extractors.
 *
 * @author Robin Weiss
 */
public abstract class AbstractSeaAroundUsExtractorTest <T> extends AbstractIteratorExtractorTest<T>
{
    private static final String HTTP_RESOURCE_FOLDER = "mockedHttpResponses";
    private static final String CONFIG_RESOURCE = "config.json";
    private static final String OUTPUT_RESOURCE = "output.json";
    protected static final int MOCKED_REGION_ID = 1337;

    protected final DiskIO diskReader = new DiskIO(GsonUtils.createGerdiDocumentGsonBuilder().create(), StandardCharsets.UTF_8);


    /**
     * Returns the class of the extracted elements.
     *
     * @return the class of the extracted elements
     */
    @SuppressWarnings("unchecked")
    protected Type getExtractedType()
    {
        final Class<T> extractedClass =
            (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        return TypeToken.get(extractedClass).getType();
    }


    @Override
    protected ContextListener getContextListener()
    {
        return new SeaAroundUsContextListener();
    }


    @Override
    protected File getConfigFile()
    {
        return getResource(CONFIG_RESOURCE);
    }


    @Override
    protected File getMockedHttpResponseFolder()
    {
        return getResource(HTTP_RESOURCE_FOLDER);
    }


    @Override
    protected T getExpectedOutput()
    {
        final String regionPath = getResource(OUTPUT_RESOURCE).toString();
        return diskReader.getObject(
                   regionPath,
                   getExtractedType());
    }


    /**
     * Verifies that the number of extractable elements is greater than zero.
     */
    @Test
    public void testSize()
    {
        assertTrue(
            "Expected the size() function to return an integer greater than zero.",
            testedObject.size() > 0);
    }


    /**
     * Verifies that the version of the extracted resources can be retrieved.
     */
    @Test
    public void testGetUniqueVersionString()
    {
        assertNotNull(
            "Expected the getUniqueVersionString() function to return a valid string.",
            testedObject.getUniqueVersionString());
    }
}
