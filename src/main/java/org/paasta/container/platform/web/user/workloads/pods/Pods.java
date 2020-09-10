package org.paasta.container.platform.web.user.workloads.pods;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.common.model.CommonSpec;
import org.paasta.container.platform.web.user.common.model.CommonStatus;

import java.util.Map;

/**
 * Pods Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.08
 */
@Data
public class Pods {
    private String resultCode;
    private String resultMessage;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private CommonStatus status;

    private String selector;
    private String serviceName;

    private Map<String, Object> source;
    private String sourceTypeYaml;
}
