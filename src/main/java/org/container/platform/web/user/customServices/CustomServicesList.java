package org.container.platform.web.user.customServices;

import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private Map<String, Object> metadata;
    private Map<String, Object> itemMetaData;
    private List<CustomServices> items;

}
