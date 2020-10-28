package org.paasta.container.platform.web.user.workloads.replicaSets;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ReplicaSets List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.09
 */
@Data
public class ReplicaSetsList {

    private String resultCode;
    private String resultMessage;
    private Map<String,Object> metadata;
    private Map<String, Object> itemMetaData;
    private List<ReplicaSets> items;

}
