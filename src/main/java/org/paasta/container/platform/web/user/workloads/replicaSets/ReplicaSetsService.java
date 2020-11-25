package org.paasta.container.platform.web.user.workloads.replicaSets;

import org.paasta.container.platform.web.user.common.CommonUtils;
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
     * Instantiates a new ReplicaSets service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public ReplicaSetsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * ReplicaSets 목록 조회(Get ReplicaSets node)
     *
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the replicaSets list
     */
    ReplicaSetsList getReplicaSetsList(String namespace, int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API,
                Constants.URI_API_REPLICA_SETS_LIST
                        .replace("{namespace:.+}", namespace) + param
                , HttpMethod.GET, null, ReplicaSetsList.class);
    }


    /**
     * ReplicaSets 상세 조회(Get ReplicaSets detail)
     *
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @return the replicaSets detail
     */
    ReplicaSets getReplicaSets(String namespace, String replicaSetName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName)
                , HttpMethod.GET, null, ReplicaSets.class);
    }


    /**
     * ReplicaSets YAML 조회(Get ReplicaSets yaml)
     *
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @return the replicaSets yaml
     */
    ReplicaSets getReplicaSetsYaml(String namespace, String replicaSetName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName),
                HttpMethod.GET, null, ReplicaSets.class);
    }


    /**
     * ReplicaSets 목록 조회(Get ReplicaSets selector)
     *
     * @param namespace          the namespace
     * @param selector           the selector
     * @param type               the type
     * @param ownerReferencesUid the ownerReferencesUid
     * @return the replicaSets list
     */
    ReplicaSetsList getReplicaSetsListLabelSelector(String namespace, String selector, String type, String ownerReferencesUid) {

        String param = "?selector=" + selector + "&type=" + type + "&ownerReferencesUid=" + ownerReferencesUid;

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_RESOURCES
                        .replace("{namespace:.+}", namespace) + param,
                HttpMethod.GET, null, ReplicaSetsList.class);
    }


    /**
     * ReplicaSets 생성(Create ReplicaSets)
     *
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    public Object createReplicaSets(String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_CREATE
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }


    /**
     * ReplicaSets 수정(Update ReplicaSets)
     *
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @param yaml           the yaml
     * @return return is succeeded
     */
    public Object updateReplicaSets(String namespace, String replicaSetName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_UPDATE
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }


    /**
     * ReplicaSets 삭제(Delete ReplicaSets)
     *
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @return return is succeeded
     */
    public Object deleteReplicaSets(String namespace, String replicaSetName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_REPLICA_SETS_DELETE
                        .replace("{namespace:.+}", namespace)
                        .replace("{replicaSetName:.+}", replicaSetName),
                HttpMethod.DELETE, null, Object.class);
    }
}
