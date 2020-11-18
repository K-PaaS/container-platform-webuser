package org.paasta.container.platform.web.user.clusters.limitRanges;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;

@Data
public class LimitRanges {

    private String resultCode;
    private String resultMessage;

    private String apiVersion;
    private String kind;
    private CommonMetaData metadata;
    
}
