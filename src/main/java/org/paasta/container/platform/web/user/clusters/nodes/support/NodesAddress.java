package org.paasta.container.platform.web.user.clusters.nodes.support;

import lombok.Data;

/**
 * Nodes address 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.31
 */
@Data
class NodesAddress {
    private String address;
    private String type;
}
