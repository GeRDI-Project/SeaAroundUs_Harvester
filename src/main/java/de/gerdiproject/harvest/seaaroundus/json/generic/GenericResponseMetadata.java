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
package de.gerdiproject.harvest.seaaroundus.json.generic;

import com.google.gson.annotations.SerializedName;

/**
 * This JSON object represents metadata that is returned as part of every SeaAroundUs API request.
 * <br> e.g. the "meta" object of http://api.seaaroundus.org/api/v1/country/12
 *
 * @author Robin Weiss
 */
public class GenericResponseMetadata
{
    private String title;
    private String date;
    private String version;


    @SerializedName("request_path")
    private String requestPath;


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getDate()
    {
        return date;
    }


    public void setDate(String date)
    {
        this.date = date;
    }


    public String getVersion()
    {
        return version;
    }


    public void setVersion(String version)
    {
        this.version = version;
    }


    public String getRequestPath()
    {
        return requestPath;
    }


    public void setRequestPath(String requestPath)
    {
        this.requestPath = requestPath;
    }

}
