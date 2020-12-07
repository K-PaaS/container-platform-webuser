package org.paasta.container.platform.web.user.workloads.pods;

import org.paasta.container.platform.web.user.common.CommonUtils;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Pods Service
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.08
 */
@Service
public class PodsService {
    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Pods service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public PodsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    /**
     * Pods 목록 조회(Get Pods selector)
     *
     * @param cluster    the cluster
     * @param namespace the namespace
     * @param selector  the selector
     * @param type the type
     * @param ownerReferencesUid the ownerReferencesUid
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the pods list
     */
    PodsList getPodListBySelector(String cluster, String namespace, String selector,String type, String ownerReferencesUid,
                                  int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);
        param += "&selector=" + selector + "&type=" + type + "&ownerReferencesUid=" + ownerReferencesUid;

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_LIST_BY_SELECTOR
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace) + param, HttpMethod.GET, null, PodsList.class);
    }

    /**
     * Pods 목록 조회(Get Pods namespace)
     *
     * @param cluster   the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the pods list
     */
    PodsList getPodList(String cluster, String namespace, int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_LIST
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace) + param
                , HttpMethod.GET, null, PodsList.class);
    }

    /**
     * Pods 목록 조회(Get Pods node)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param nodeName  the node name
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the pods list
     */
    PodsList getPodListNamespaceByNode(String cluster, String namespace, String nodeName,int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_LIST_BY_NODE
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace)
                .replace("{nodeName:.+}", nodeName) + param, HttpMethod.GET, null, PodsList.class);
    }

    /**
     * Pods 상세 조회(Get Pods detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods detail
     */
    Pods getPod(String cluster, String namespace, String podName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_DETAIL
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace).replace("{podName:.+}", podName), HttpMethod.GET, null, Pods.class);
    }

    /**
     * Pods YAML 조회(Get Pods yaml)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods yaml
     */
    Pods getPodYaml(String cluster, String namespace, String podName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_YAML
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace)
                .replace("{podName:.+}", podName), HttpMethod.GET, null, Pods.class);
    }

    /**
     * Pods 생성(Create Pods)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    public Object createPods(String cluster, String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_PODS_CREATE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }

    /**
     * Pods 수정(Update Pods)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @param yaml      the yaml
     * @return return is succeeded
     */
    public Object updatePods(String cluster, String namespace, String podName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_POD_UPDATE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{podName:.+}", podName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }

    /**
     * Pods 삭제(Delete Pods)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @return return is succeeded
     */
    public Object deletePods(String cluster,String namespace, String podName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_POD_DELETE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{podName:.+}", podName),
                HttpMethod.DELETE, null, Object.class);
    }
}
