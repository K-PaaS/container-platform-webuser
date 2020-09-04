package org.paasta.container.platform.web.user.clusters.namespaces;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.common.model.CommonSpec;
import org.paasta.container.platform.web.user.common.model.CommonStatus;

/**
 * Namespace Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.02
 */
@Data
public class Namespaces {

    private String resultCode;
    private String resultMessage;

    private String kind;
    private String apiVersion;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private CommonStatus status;
}