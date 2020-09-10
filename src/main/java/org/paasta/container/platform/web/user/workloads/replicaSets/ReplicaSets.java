package org.paasta.container.platform.web.user.workloads.replicaSets;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.common.model.CommonSpec;
import org.paasta.container.platform.web.user.common.model.CommonStatus;

/**
 * ReplicaSets Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.09
 */
@Data
public class ReplicaSets {

    private String resultCode;
    private String resultMessage;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private CommonStatus status;

    private String sourceTypeYaml;
}
