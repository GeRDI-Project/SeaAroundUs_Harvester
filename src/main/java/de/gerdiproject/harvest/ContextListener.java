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
package de.gerdiproject.harvest;

import de.gerdiproject.harvest.harvester.AbstractHarvester;
import de.gerdiproject.harvest.harvester.SeaAroundUsHarvester;
import de.gerdiproject.logger.ILogger;
import de.gerdiproject.logger.impl.ConsoleLogger;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author row
 */
@WebListener
public class ContextListener extends AbstractContextListener
{
    @Override
    protected ILogger createLogger()
    {
        return new ConsoleLogger();
    }


    @Override
    protected AbstractHarvester createHarvester()
    {
        return SeaAroundUsHarvester.createInstance();
    }


	@Override
	protected String createServiceName()
	{
		return "SeaAroundUsHarvesterService";
	}
    
    
}
