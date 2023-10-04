package org.container.platform.web.user.workloads.pods.support;

import lombok.Data;

/**
 * Volume Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.08
 */
@Data
public class Volume {
    private String name;
    private SecretVolumeSource secret;
}