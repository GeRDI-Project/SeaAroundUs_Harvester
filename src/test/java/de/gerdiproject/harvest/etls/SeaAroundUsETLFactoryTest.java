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
package de.gerdiproject.harvest.etls;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.gerdiproject.harvest.AbstractUnitTest;
import de.gerdiproject.harvest.event.EventSystem;
import lombok.RequiredArgsConstructor;

/**
 * This class provides Unit Tests for the {@linkplain SeaAroundUsETLFactory}.
 *
 * @author Robin Weiss
 */
@RunWith(Parameterized.class) @RequiredArgsConstructor
public class SeaAroundUsETLFactoryTest extends AbstractUnitTest
{
    private final EtlCreator etlCreator;


    @Parameters(name = "{0}")
    public static Object[] getParameters()
    {
        return new Object[] {
                   new EtlCreator(SeaAroundUsETLFactory::createCountryETL),
                   new EtlCreator(SeaAroundUsETLFactory::createEezETL),
                   new EtlCreator(SeaAroundUsETLFactory::createFaoETL),
                   new EtlCreator(SeaAroundUsETLFactory::createFishingEntityETL),
                   new EtlCreator(SeaAroundUsETLFactory::createGlobalRegionETL),
                   new EtlCreator(SeaAroundUsETLFactory::createHighSeasETL),
                   new EtlCreator(SeaAroundUsETLFactory::createLmeETL),
                   new EtlCreator(SeaAroundUsETLFactory::createMaricultureETL),
                   new EtlCreator(SeaAroundUsETLFactory::createRfmoETL),
                   new EtlCreator(SeaAroundUsETLFactory::createTaxonETL)
               };
    }


    /**
     * Tests if the {@linkplain SeaAroundUsETLFactory} creates non-null ETLs.
     */
    @Test
    public void testCreateETL()
    {
        assertNotNull("Expected a non-null ETL to be created.",
                      etlCreator.create());
    }


    /**
     * Tests if the {@linkplain SeaAroundUsETLFactory} creaters do not create event listeners.
     */
    @Test
    public void testForNoEventListeners()
    {
        final AbstractETL<?, ?> etl = etlCreator.create();
        assertFalse(String.format("Expected the creation of %s to not add any event listeners.", etl.getClass().getSimpleName()),
                    EventSystem.hasAsynchronousEventListeners()
                    || EventSystem.hasSynchronousEventListeners());
    }


    /**
     * Tests if {@linkplain AbstractETL#removeEventListeners()} removes all event listeners that
     * can been added via {@linkplain AbstractETL#addEventListeners()}.
     */
    @Test
    public void testRemovingEventListeners()
    {
        final AbstractETL<?, ?> etl = etlCreator.create();
        etl.addEventListeners();
        etl.removeEventListeners();

        assertFalse(String.format("Expected the removeEventListeners() method of %s to remove all event listeners.", etl.getClass().getSimpleName()),
                    EventSystem.hasAsynchronousEventListeners()
                    || EventSystem.hasSynchronousEventListeners());
    }


    /**
     * Test object that wraps an {@linkplain AbstractETL} {@linkplain Supplier}.
     * Functional Interfaces are not objects and can therefore not be defined directly
     * as Unit test parameters, making this wrapper class necessary.
     *
     * @author Robin Weiss
     */
    @RequiredArgsConstructor
    private static class EtlCreator
    {
        final Supplier<AbstractETL<?, ?>> supplier;

        /**
         * Creates and returns an {@linkplain AbstractETL} using the wrapped {@linkplain Supplier}.
         *
         * @return an {@linkplain AbstractETL}
         */
        public AbstractETL<?, ?> create()
        {
            return supplier.get();
        }
    }
}
