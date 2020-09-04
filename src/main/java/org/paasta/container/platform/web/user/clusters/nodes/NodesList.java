package org.paasta.container.platform.web.user.clusters.nodes;

import lombok.Data;

import java.util.List;

/**
 * Nodes List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.01
 */
@Data
public class NodesList {
    private String resultCode;
    private String resultMessage;

    private List<Nodes> items;
}
