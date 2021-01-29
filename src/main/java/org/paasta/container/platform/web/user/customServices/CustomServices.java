package org.paasta.container.platform.web.user.customServices;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.common.model.CommonSpec;
import org.paasta.container.platform.web.user.common.model.CommonStatus;

/**
 * Custom Services Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.10
 */
@Data
class CustomServices {

    private String resultCode;
    private String resultMessage;
    private String nextActionUrl;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private CommonStatus status;
    private String sourceTypeYaml;

    // FOR DASHBOARD
    private String serviceName;

}
