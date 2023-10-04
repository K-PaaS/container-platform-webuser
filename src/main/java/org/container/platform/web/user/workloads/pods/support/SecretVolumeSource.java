package org.container.platform.web.user.workloads.pods.support;

import lombok.Data;

/**
 * Secret Volume Source Model Model 클래스 (Secret 클래스가 아님)
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.08
 */
@Data
class SecretVolumeSource {
    private String secretName;
    private String defaultMode;
}
