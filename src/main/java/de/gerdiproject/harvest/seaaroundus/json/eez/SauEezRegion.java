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
package de.gerdiproject.harvest.seaaroundus.json.eez;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import de.gerdiproject.harvest.seaaroundus.json.generic.GenericRegion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This class represents a JSON object response to a Seaaroundus eez request.
 * <br>e.g. see http://api.seaaroundus.org/api/v1/eez/12
 *
 * @author Robin Weiss
 */
@Data @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class SauEezRegion extends GenericRegion
{
    @SerializedName("gsi_link")
    private String gsiLink;

    @SerializedName("intersecting_fao_area_id")
    private List<Integer> intersectingFaoAreaId;

    @SerializedName("fishbase_id")
    private String fishbaseId;

    @SerializedName("c_code")
    private String cCode;

    @SerializedName("ohi_link")
    private String ohiLink;

    @SerializedName("geo_entity_id")
    private int geoEntityId;

    @SerializedName("fao_profile_url")
    private String faoProfileUrl;

    @SerializedName("fao_rfb")
    private List<SauFaoRfb> faoRfb;

    @SerializedName("year_started_eez_at")
    private int yearStartedEezAt;

    @SerializedName("reconstruction_documents")
    private List<SauReconstructionDocument> reconstructionDocuments;

    @SerializedName("year_allowed_to_fish_high_seas")
    private int yearAllowedToFishHighSeas;

    @SerializedName("declaration_year")
    private int declarationYear;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("country_name")
    private String countryName;

    @SerializedName("estuary_count")
    private int estuaryCount;

    @SerializedName("year_allowed_to_fish_other_eezs")
    private int yearAllowedToFishOtherEezs;
}
