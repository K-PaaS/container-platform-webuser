package org.paasta.container.platform.web.user.workloads.deployments;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Deployments List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Data
public class DeploymentsList {
    private String resultCode;
    private String resultMessage;
    private Map<String,Object> metadata;
    private Map<String, Object> itemMetaData;
    private List<Deployments> items = new ArrayList<>();
}
