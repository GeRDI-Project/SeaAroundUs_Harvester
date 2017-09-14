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

import com.google.gson.annotations.SerializedName;


/**
 * This class represents a JSON object that is part of the response to a Seaaroundus taxa request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/taxa/600972
 *
 * @author Robin Weiss
 */
public class SauTaxonHabitatIndex
{
    private double slope;
    private int inshore;
    private int seamount;
    private double others;
    private double estuaries;
    private int front;
    private int offshore;
    private int abyssal;
    private double temperature;
    private double shelf;
    private int coral;
    private int seagrass;

    @SerializedName("habitat_diversity_index")
    private double habitatDiversityIndex;

    @SerializedName("effective_d")
    private double effectiveD;


    public double getSlope()
    {
        return slope;
    }


    public void setSlope(double value)
    {
        this.slope = value;
    }


    public double getHabitatDiversityIndex()
    {
        return habitatDiversityIndex;
    }


    public void setHabitatDiversityIndex(double value)
    {
        this.habitatDiversityIndex = value;
    }


    public int getInshore()
    {
        return inshore;
    }


    public void setInshore(int value)
    {
        this.inshore = value;
    }


    public int getSeamount()
    {
        return seamount;
    }


    public void setSeamount(int value)
    {
        this.seamount = value;
    }


    public double getOthers()
    {
        return others;
    }


    public void setOthers(double value)
    {
        this.others = value;
    }


    public double getEstuaries()
    {
        return estuaries;
    }


    public void setEstuaries(double value)
    {
        this.estuaries = value;
    }


    public int getFront()
    {
        return front;
    }


    public void setFront(int value)
    {
        this.front = value;
    }


    public double getEffectiveD()
    {
        return effectiveD;
    }


    public void setEffectiveD(double value)
    {
        this.effectiveD = value;
    }


    public int getOffshore()
    {
        return offshore;
    }


    public void setOffshore(int value)
    {
        this.offshore = value;
    }


    public int getAbyssal()
    {
        return abyssal;
    }


    public void setAbyssal(int value)
    {
        this.abyssal = value;
    }


    public double getTemperature()
    {
        return temperature;
    }


    public void setTemperature(double value)
    {
        this.temperature = value;
    }


    public double getShelf()
    {
        return shelf;
    }


    public void setShelf(double value)
    {
        this.shelf = value;
    }


    public int getCoral()
    {
        return coral;
    }


    public void setCoral(int value)
    {
        this.coral = value;
    }


    public int getSeagrass()
    {
        return seagrass;
    }


    public void setSeagrass(int value)
    {
        this.seagrass = value;
    }
}
