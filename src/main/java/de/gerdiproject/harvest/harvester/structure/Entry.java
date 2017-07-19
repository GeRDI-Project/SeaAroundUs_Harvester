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
 * An entry in this context is a typical filtering option for showing Sea Around
 * data.
 *
 * @author row
 */
public class Entry
{
    /**
     * The string as it is used in HTTP requests.
     */
    public final String urlName;

    /**
     * The string as it is displayed to the user.
     */
    public final String displayName;


    /**
     * Simple constructor.
     *
     * @param urlName the string as it is used in HTTP requests
     * @param displayName the string as it is displayed to the user
     */
    public Entry(String urlName, String displayName)
    {
        this.urlName = urlName;
        this.displayName = displayName;
    }
}
