package org.paasta.container.platform.web.user.workloads.replicaSets;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * ReplicaSets Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.09
 */
@Service
public class ReplicaSetsService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new ReplicaSets service.
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public ReplicaSetsService(RestTemplateService restTemplateService) {this.restTemplateService = restTemplateService;}


    /**
     * ReplicaSets 목록을 조회한다.
     * @param namespace   the namespace
     * @return the replicaSets list
     */
    ReplicaSetsList getReplicaSetsList(String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_LIST
                        .replace("{namespace:.+}", namespace),
                HttpMethod.GET, null, ReplicaSetsList.class);
    }


    /**
     * ReplicaSets 상세 정보를 조회한다.
     *
     * @param namespace   the namespace
     * @param replicaSetName the replicaSet name
     * @return the replicaSets
     */
    ReplicaSets getReplicaSets(String namespace, String replicaSetName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName)
                , HttpMethod.GET, null, ReplicaSets.class);
    }


    /**
     * ReplicaSets YAML을 조회한다.
     *
     * @param namespace   the namespace
     * @param replicaSetName the replicaSet name
     * @return the replicaSets yaml
     */
    ReplicaSets getReplicaSetsYaml(String namespace, String replicaSetName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName),
                HttpMethod.GET, null, ReplicaSets.class);
    }


    /**
     * ReplicaSets 목록을 조회한다. (Label Selector)
     *
     * @param namespace the namespace
     * @param selectors the selectors
     * @return the replicaSets list
     */
    ReplicaSetsList getReplicaSetsListLabelSelector(String namespace, String selectors) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_RESOURCES
                        .replace("{namespace:.+}", namespace)
                        .replace("{selector:.+}", selectors),
                HttpMethod.GET, null, ReplicaSetsList.class);
    }


    /**
     * ReplicaSets을 생성한다.
     *
     * @param namespace the namespace
     * @param yaml the yaml
     * @return
     */
    public Object createReplicaSets(String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_CREATE
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }


    /**
     * ReplicaSets을 수정한다.
     *
     * @param namespace the namespace
     * @param replicaSetName the replicaSet name
     * @param yaml the yaml
     * @return
     */
    public Object updateReplicaSets(String namespace, String replicaSetName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_UPDATE
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }


    /**
     * ReplicaSets 상세 정보를 삭제한다.
     *
     * @param namespace   the namespace
     * @param replicaSetName   the replicaSet name
     * @return the replicaSets
     */
    public Object deleteReplicaSets(String namespace, String replicaSetName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_DELETE
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName),
                HttpMethod.DELETE, null, Object.class);
    }
}
