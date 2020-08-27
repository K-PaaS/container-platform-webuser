package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

/**
 * Common Condition Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.25
 */
@Data
public class CommonCondition {
    private String type;
    private String status;

    private String message;
    private String reason;

    private String lastHeartbeatTime;
    private String lastTransitionTime;
}
