package org.container.platform.web.user.clusters.nodes.support;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Nodes Status Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.31
 */
@Data
public class NodesStatus {
    private Map<String, Object> capacity;
    private Map<String, Object> allocatable;

    private List<Map<String, Object>> conditions;
    private List<NodesAddress> addresses;
    private NodesSystemInfo nodeInfo;
}
