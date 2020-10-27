package org.paasta.container.platform.web.user.intro.accessInfo;

import lombok.Data;

/**
 * Access Token Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.14
 */
@Data
class AccessToken {
    private String resultCode;
    private String resultMessage;
    private Integer httpStatusCode;
    private String detailMessage;

    private String caCertToken;
    private String userAccessToken;
}
