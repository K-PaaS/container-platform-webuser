package org.paasta.container.platform.web.user.workloads.pods;

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
     * @param namespace the namespace
     * @param selector  the selector
     * @return the pods list
     */
    PodsList getPodListBySelector(String namespace, String selector) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_LIST_BY_SELECTOR
                .replace("{namespace:.+}", namespace).replace("{selector:.+}", selector), HttpMethod.GET, null, PodsList.class);
    }

    /**
     * Pods 목록 조회(Get Pods namespace)
     *
     * @param namespace the namespace
     * @return the pods list
     */
    PodsList getPodList(String namespace ,int limit, String continueToken) {
        String param = "";

        if(continueToken != null) {
            param = "&continue=" + continueToken;
        }

        return restTemplateService.send(Constants.TARGET_CP_API,
                Constants.URI_API_PODS_LIST
                        .replace( "{namespace:.+}", namespace ) + "?limit=" + limit + param
                        ,HttpMethod.GET, null, PodsList.class);
    }

    /**
     * Pods 목록 조회(Get Pods node)
     *
     * @param namespace the namespace
     * @param nodeName  the node name
     * @return the pods list
     */
    PodsList getPodListNamespaceByNode(String namespace, String nodeName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_LIST_BY_NODE
                .replace("{namespace:.+}", namespace).replace("{nodeName:.+}", nodeName), HttpMethod.GET, null, PodsList.class);
    }

    /**
     * Pods 상세 조회(Get Pods detail)
     *
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods
     */
    Pods getPod(String namespace, String podName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_DETAIL
                .replace("{namespace:.+}", namespace).replace("{podName:.+}", podName), HttpMethod.GET, null, Pods.class);
    }

    /**
     * Pods YAML 조회(Get Pods yaml)
     *
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods
     */
    Pods getPodYaml(String namespace, String podName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PODS_YAML
                .replace("{namespace:.+}", namespace).replace("{podName:.+}", podName), HttpMethod.GET, null, Pods.class);
    }

    /**
     * Pods 생성(Create Pods)
     *
     * @param namespace
     * @param yaml
     * @return
     */
    public Object createPods(String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_PODS_CREATE
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }

    /**
     * Pods 수정(Update Pods)
     *
     * @param namespace the namespace
     * @param podName the pods name
     * @param yaml the yaml
     * @return
     */
    public Object updatePods(String namespace, String podName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_POD_UPDATE
                        .replace("{namespace:.+}", namespace)
                        .replace("{podName:.+}", podName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }

    /**
     * Pods 삭제(Delete Pods)
     *
     * @param namespace the namespace
     * @param podName the pods name
     * @return the Pods
     */
    public Object deletePods(String namespace, String podName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_POD_DELETE
                        .replace("{namespace:.+}", namespace)
                        .replace("{podName:.+}", podName),
                HttpMethod.DELETE, null, Object.class);
    }
}
