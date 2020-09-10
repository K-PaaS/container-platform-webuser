package org.paasta.container.platform.web.user.customServices;

import lombok.Data;

import java.util.List;

/**
 * Custom Services List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.10
 */
@Data
class CustomServicesList {

    private String resultCode;
    private String resultMessage;

    private List<CustomServices> items;

}
