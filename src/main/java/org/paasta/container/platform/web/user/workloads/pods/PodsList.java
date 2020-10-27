package org.paasta.container.platform.web.user.workloads.pods;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Pods List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.08
 */
@Data
public class PodsList {
    private String resultCode;
    private String resultMessage;
    private Map<String,Object> metadata;

    private List<Pods> items;
    private Map<String, Object> itemMetaData;
    private String selector;
    private String serviceName;
}
