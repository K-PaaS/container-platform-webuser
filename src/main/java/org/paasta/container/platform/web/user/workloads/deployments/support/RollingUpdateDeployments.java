package org.paasta.container.platform.web.user.workloads.deployments.support;

import lombok.Data;

/**
 * RollingUpdateDeployments
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Data
public class RollingUpdateDeployments {
    private String maxSurge;
    private String maxUnavailable;
}
