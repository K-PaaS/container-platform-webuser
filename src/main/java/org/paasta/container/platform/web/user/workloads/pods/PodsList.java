package org.paasta.container.platform.web.user.workloads.pods;

import lombok.Data;

import java.util.List;

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

    private List<Pods> items;

    private String selector;
    private String serviceName;
}
