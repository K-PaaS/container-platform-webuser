package org.paasta.container.platform.web.user.clusters.nodes.support;

import lombok.Data;

/**
 * Nodes System Info Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.31
 */
@Data
class NodesSystemInfo {
    private String architecture;
    private String bootID;
    private String containerRuntimeVersion;
    private String kernelVersion;
    private String kubeProxyVersion;
    private String kubeletVersion;
    private String machineID;
    private String operatingSystem;
    private String osImage;
    private String systemUUID;
}
