package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;

import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.fao.SauFaoRegion;

/**
 * This harvester harvests all FAOs of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/fao/
 *
 * @author Robin Weiss
 */
public class FaoRegionHarvester extends GenericRegionHarvester<SauFaoRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * FAO parameters.
     */
    public FaoRegionHarvester()
    {
        super(RegionConstants.FAO_PARAMS);
    }
}
