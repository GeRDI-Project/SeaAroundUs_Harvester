/**
 * Copyright © 2017 Robin Weiss (http://www.gerdi-project.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.gerdiproject.harvest;

import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebListener;

import de.gerdiproject.harvest.application.ContextListener;
import de.gerdiproject.harvest.etls.AbstractETL;
import de.gerdiproject.harvest.etls.SeaAroundUsETLFactory;

/**
 * This class initializes the harvester service and all objects that are required.
 *
 * @author Robin Weiss
 */
@WebListener
public class SeaAroundUsContextListener extends ContextListener
{
    @Override
    protected List<? extends AbstractETL<?, ?>> createETLs()
    {
        return Arrays.asList(
                   SeaAroundUsETLFactory.createTaxonETL(),
                   SeaAroundUsETLFactory.createGlobalOceanETL(),
                   SeaAroundUsETLFactory.createFishingEntityETL(),
                   SeaAroundUsETLFactory.createMaricultureETL(),
                   SeaAroundUsETLFactory.createCountryETL(),
                   SeaAroundUsETLFactory.createEezETL(),
                   SeaAroundUsETLFactory.createRfmoETL(),
                   SeaAroundUsETLFactory.createLmeETL(),
                   SeaAroundUsETLFactory.createHighSeasETL(),
                   SeaAroundUsETLFactory.createFaoETL()
               );
    }
}
