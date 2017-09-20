package de.gerdiproject.harvest.harvester.subHarvesters.regionTypes;

import java.util.List;

import de.gerdiproject.harvest.seaaroundus.constants.DataCiteConstants;
import de.gerdiproject.harvest.seaaroundus.constants.RegionConstants;
import de.gerdiproject.harvest.seaaroundus.json.lme.SauLmeRegion;
import de.gerdiproject.json.datacite.WebLink;
import de.gerdiproject.json.datacite.WebLink.WebLinkType;

/**
 * This harvester harvests all LMEs of SeaAroundUs.
 * <br>see http://api.seaaroundus.org/api/v1/lme/
 *
 * @author Robin Weiss
 */
public class LmeRegionHarvester extends GenericRegionHarvester<SauLmeRegion>
{
    /**
     * Simple constructor that initializes the super class with
     * LME parameters.
     */
    public LmeRegionHarvester()
    {
        super(RegionConstants.LME_PARAMS);
    }


    @Override
    protected void enrichWebLinks(List<WebLink> links, SauLmeRegion regionObject)
    {
        super.enrichWebLinks(links, regionObject);

        // add a link to fishbase
        if (regionObject.getFishbaseLink() != null) {
            WebLink fishBaseLink = new WebLink(regionObject.getFishbaseLink());
            fishBaseLink.setName(DataCiteConstants.FISHBASE_TAXA_LINK_NAME);
            fishBaseLink.setType(WebLinkType.Related);

            links.add(fishBaseLink);
        }

        // add a link to the LME website
        if (regionObject.getProfileUrl() != null) {
            WebLink lmeLink = new WebLink(regionObject.getProfileUrl());
            lmeLink.setName(DataCiteConstants.LME_NOAA_LINK_NAME);
            lmeLink.setType(WebLinkType.Related);

            links.add(lmeLink);
        }
    }
}
