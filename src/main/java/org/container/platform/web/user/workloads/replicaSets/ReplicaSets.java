package org.container.platform.web.user.workloads.replicaSets;

import lombok.Data;
import org.container.platform.web.user.common.model.CommonMetaData;
import org.container.platform.web.user.common.model.CommonSpec;
import org.container.platform.web.user.common.model.CommonStatus;

import java.util.Map;

/**
 * ReplicaSets Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.09
 */
@Data
public class ReplicaSets {

    private String resultCode;
    private String resultMessage;
    private String nextActionUrl;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private CommonStatus status;

    private Map<String, Object> source;
    private String sourceTypeYaml;
}
