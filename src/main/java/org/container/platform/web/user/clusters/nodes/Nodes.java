package org.container.platform.web.user.clusters.nodes;

import lombok.Data;
import org.container.platform.web.user.clusters.nodes.support.NodesStatus;
import org.container.platform.web.user.common.model.CommonMetaData;
import org.container.platform.web.user.common.model.CommonSpec;

/**
 * Nodes Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.31
 */
@Data
public class Nodes {
    private String resultCode;
    private String resultMessage;

    private CommonMetaData metadata;
    private CommonSpec spec;
    private NodesStatus status;

    private String nodeName;
}
