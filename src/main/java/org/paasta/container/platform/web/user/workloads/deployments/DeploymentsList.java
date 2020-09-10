package org.paasta.container.platform.web.user.workloads.deployments;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Deployments List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Data
public class DeploymentsList {
    private String resultCode;
    private String resultMessage;

    private List<Deployments> items = new ArrayList<>();
}
