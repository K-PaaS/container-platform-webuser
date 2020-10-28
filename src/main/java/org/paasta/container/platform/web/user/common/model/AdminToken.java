package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

/**
 * Admin Token Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.25
 */
@Data
public class AdminToken {
    private String token_name;
    private String token_value;
}
