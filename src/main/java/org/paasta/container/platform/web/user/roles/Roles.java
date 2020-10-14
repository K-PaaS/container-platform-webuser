package org.paasta.container.platform.web.user.roles;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.workloads.deployments.support.DeploymentsSpec;
import org.paasta.container.platform.web.user.workloads.deployments.support.DeploymentsStatus;

import java.util.ArrayList;
import java.util.Map;

/**
 * Roles Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.10.13
 */
@Data
public class Roles {
    private String resultCode;
    private String resultMessage;
    private String nextActionUrl;

    private CommonMetaData metadata;
    private ArrayList rules;

    private String sourceTypeYaml;

}