package org.paasta.container.platform.web.user.endpoints;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.common.model.CommonSpec;
import org.paasta.container.platform.web.user.common.model.CommonStatus;
import org.paasta.container.platform.web.user.common.model.CommonSubset;

import java.util.List;

/**
 * Endpoints Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Data
public class Endpoints {

    private String resultCode;
    private String resultMessage;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private CommonStatus status;
    private List<CommonSubset> subsets;

    // FOR DASHBOARD
    private String serviceName;

}
