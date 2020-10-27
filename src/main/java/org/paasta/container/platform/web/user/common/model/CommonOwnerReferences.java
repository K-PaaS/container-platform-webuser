package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

/**
 * Common Owner References Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.26
 */
@Data
class CommonOwnerReferences {
    private String name;
    private boolean controller;
}
