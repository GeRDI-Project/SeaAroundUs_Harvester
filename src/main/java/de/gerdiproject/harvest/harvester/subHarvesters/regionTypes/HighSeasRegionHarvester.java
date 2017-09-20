package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;

import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;

/**
 * This harvester harvests all HighSeas of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/highseas/
 *
 * @author Robin Weiss
 */
public class HighSeasRegionHarvester extends GenericRegionHarvester<GenericRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * High Seas parameters.
     */
    public HighSeasRegionHarvester()
    {
        super(RegionConstants.HIGH_SEAS_PARAMS);
    }
}
