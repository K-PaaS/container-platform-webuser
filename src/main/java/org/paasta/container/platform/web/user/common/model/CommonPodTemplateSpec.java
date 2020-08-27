package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

/**
 * Common Pod Template Spec Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.26
 */
@Data
public class CommonPodTemplateSpec {
    private CommonMetaData metadata;
    private CommonPodSpec spec;
}
