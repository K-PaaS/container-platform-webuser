package org.paasta.container.platform.web.user.workloads.deployments.support;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonLabelSelector;
import org.paasta.container.platform.web.user.common.model.CommonPodTemplateSpec;

/**
 * Deployments Spec Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Data
public class DeploymentsSpec {
    private int minReadySeconds;
    private boolean paused;
    private int progressDeadlineSeconds;
    private int replicas;
    private int revisionHistoryLimit;
    private CommonLabelSelector selector;
    private DeploymentsStrategy strategy;
    private CommonPodTemplateSpec template;
}
