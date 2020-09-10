package org.paasta.container.platform.web.user.workloads.deployments;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.workloads.deployments.support.DeploymentsSpec;
import org.paasta.container.platform.web.user.workloads.deployments.support.DeploymentsStatus;

import java.util.Map;

/**
 * Deployments Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Data
public class Deployments {
    private String resultCode;
    private String resultMessage;

    private CommonMetaData metadata;
    private DeploymentsSpec spec;
    private DeploymentsStatus status;

    private Map<String, Object> source;
    private String sourceTypeYaml;
}
