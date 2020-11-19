package org.paasta.container.platform.web.user.clusters.limitRanges;


import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonItemMetaData;

import java.util.List;
import java.util.Map;

/**
 * LimitRangeList Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.03
 */
@Data

public class LimitRangesList {
    private String resultCode;
    private String resultMessage;
    private Map metadata;
    private List<LimitRangesItem> items;
}

@Data
class LimitRangesItem {
    private String name;
    private String type;
    private String defaultRequest;
    private String defaultLimit;
    private String checkYn;

}

