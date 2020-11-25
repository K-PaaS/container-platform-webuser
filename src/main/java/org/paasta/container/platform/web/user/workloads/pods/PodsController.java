package org.paasta.container.platform.web.user.workloads.pods;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Pods Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.08
 */
@Api(value = "PodsController v1")
@RestController
public class PodsController {
    private static final String VIEW_URL = "/pods";
    private final CommonService commonService;
    private final PodsService podsService;

    /**
     * Instantiates a new Pods controller
     *
     * @param commonService the common service
     * @param podsService   the pods service
     */
    @Autowired
    public PodsController(CommonService commonService, PodsService podsService) {
        this.commonService = commonService;
        this.podsService = podsService;
    }

    /**
     * Pods main 페이지 이동(Move Pods main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the pods main
     */
    @ApiOperation(value = "Pods main 페이지 이동(Move Pods main page)", nickname = "getPodList")
    @GetMapping(value = Constants.URI_WORKLOAD_PODS)
    public ModelAndView getPodList(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }

    /**
     * Pods details 페이지 이동(Move Pods detail page)
     *
     * @param httpServletRequest the http servlet request
     * @param podName            the pods name
     * @return the pods detail
     */
    @ApiOperation(value = "Pods details 페이지 이동(Move Pods detail page)", nickname = "getPodDetails")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_WORKLOAD_PODS + "/{podName:.+}")
    public ModelAndView getPodDetails(HttpServletRequest httpServletRequest,
                                      @PathVariable(value = "podName") String podName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/details", new ModelAndView());
    }

    /**
     * Pods event 페이지 이동(Move Pods event page)
     *
     * @param httpServletRequest the http servlet request
     * @param podName            the pods name
     * @return the pods event
     */
    @ApiOperation(value = "Pods event 페이지 이동(Move Pods event page)", nickname = "getPodEvents")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_WORKLOAD_PODS + "/{podName:.+}/events")
    public ModelAndView getPodEvents(HttpServletRequest httpServletRequest,
                                     @PathVariable(value = "podName") String podName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }

    /**
     * Pods yaml 페이지 이동(Move Pods yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @param podName            the pods name
     * @return the pods yaml
     */
    @ApiOperation(value = "Pods yaml 페이지 이동(Move Pods yaml page)", nickname = "getPodYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_WORKLOAD_PODS + "/{podName:.+}/yaml")
    public ModelAndView getPodYaml(HttpServletRequest httpServletRequest,
                                   @PathVariable(value = "podName") String podName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }

    /**
     * Pods 목록 조회(Get Pods list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the pods list
     */
    @ApiOperation(value = "Pods 목록 조회(Get Pods list)", nickname = "getPodList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST)
    public PodsList getPodList(@PathVariable String cluster,
                               @PathVariable(value = "namespace") String namespace,
                               @RequestParam(required = false, defaultValue = "0") int offset,
                               @RequestParam(required = false, defaultValue = "0") int limit,
                               @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                               @RequestParam(required = false, defaultValue = "desc") String order,
                               @RequestParam(required = false, defaultValue = "") String searchName) {

        return podsService.getPodList(cluster, namespace, offset, limit, orderBy, order, searchName);
    }

    /**
     * Pods 상세 조회(Get Pods detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods
     */
    @ApiOperation(value = "Pods 상세 조회(Get Pods detail)", nickname = "getPod")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_DETAIL)
    public Pods getPod(@PathVariable String cluster,
                       @PathVariable(value = "namespace") String namespace,
                       @PathVariable(value = "podName") String podName) {
        return podsService.getPod(cluster, namespace, podName);
    }

    /**
     * Pods YAML 조회(Get Pods yaml)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods yaml
     */
    @ApiOperation(value = "Pods YAML 조회(Get Pods yaml)", nickname = "getPodYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_YAML)
    public Pods getPodYaml(@PathVariable String cluster,
                           @PathVariable(value = "namespace") String namespace,
                           @PathVariable(value = "podName") String podName) {
        return podsService.getPodYaml(cluster, namespace, podName);
    }

    /**
     * Pods 목록 조회(Get Pods selector)
     *
     * @param cluster            the cluster
     * @param namespace          the namespace
     * @param selector           the selector
     * @param type               the type
     * @param ownerReferencesUid the ownerReferencesUid
     * @return the pods list
     */
    @ApiOperation(value = "Pods 목록 조회(Get Pods selector)", nickname = "getPodListBySelector")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "selector", value = "셀렉터", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "리소스 타입", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ownerReferencesUid", value = "참조 리소스의 UID", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST_BY_SELECTOR)
    @ResponseBody
    public PodsList getPodListBySelector(@PathVariable String cluster,
                                         @PathVariable("namespace") String namespace,
                                         @RequestParam(name = "selector", required = false, defaultValue = "") String selector,
                                         @RequestParam(name = "type", required = false, defaultValue = "default") String type,
                                         @RequestParam(name = "ownerReferencesUid", required = false, defaultValue = "") String ownerReferencesUid) {
        return podsService.getPodListBySelector(cluster, namespace, selector, type, ownerReferencesUid);
    }


    /**
     * Pods 목록 조회(Get Pods selector, service)
     *
     * @param cluster     the cluster
     * @param namespace   the namespace
     * @param serviceName the service name
     * @param selector    the selector
     * @return the pods list
     */
    @ApiOperation(value = "Pods 목록 조회(Get Pods selector, service)", nickname = "getPodListBySelectorWithService")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "serviceName", value = "서비스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "selector", value = "셀렉터", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST_BY_SELECTOR_WITH_SERVICE)
    @ResponseBody
    public PodsList getPodListBySelectorWithService(@PathVariable String cluster,
                                                    @PathVariable("namespace") String namespace,
                                                    @PathVariable("serviceName") String serviceName,
                                                    @RequestParam(name = "selector", required = true, defaultValue = "") String selector) {
        PodsList podsList = podsService.getPodListBySelector(cluster, namespace, selector,"","");
        podsList.setServiceName(serviceName);  // FOR DASHBOARD
        podsList.setSelector(selector);        // FOR DASHBOARD

        return podsList;
    }

    /**
     * Pods 목록 조회(Get Pods node)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param nodeName  the nodes name
     * @return the pods list
     */
    @ApiOperation(value = "Pods 목록 조회(Get Pods node)", nickname = "getPodListByNode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "nodeName", value = "노드 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST_BY_NODE)
    @ResponseBody
    public PodsList getPodListByNode(@PathVariable String cluster,
                                     @PathVariable(value = "namespace") String namespace,
                                     @PathVariable(value = "nodeName") String nodeName) {
        return podsService.getPodListNamespaceByNode(cluster, namespace, nodeName);
    }

    /**
     * Pods 생성(Create Pods)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "Pods 생성(Create Pods)", nickname = "createDeployments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 생성 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping(value = Constants.API_URL + Constants.URI_API_PODS_CREATE)
    @ResponseBody
    public Object createDeployments(@PathVariable String cluster,
                                    @PathVariable(value = "namespace") String namespace,
                                    @RequestBody String yaml) {
        return podsService.createPods(cluster, namespace, yaml);

    }

    /**
     * Pods 수정(Update Pods)
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @param yaml      the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "Pods 수정(Update Pods)", nickname = "updatePods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 수정 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_POD_UPDATE)
    @ResponseBody
    public Object updatePods(@PathVariable(value = "cluster") String cluster,
                             @PathVariable(value = "namespace") String namespace,
                             @PathVariable("podName") String podName,
                             @RequestBody String yaml) {
        return podsService.updatePods(cluster, namespace, podName, yaml);
    }

    /**
     * Pods 삭제(Delete Pods)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param podName   the pods name
     * @return return is succeeded
     */
    @ApiOperation(value = "Pods 삭제(Delete Pods)", nickname = "deletePods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "podName", value = "Pods 명", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_POD_DELETE)
    @ResponseBody
    public Object deletePods(@PathVariable(value = "cluster") String cluster,
                             @PathVariable(value = "namespace") String namespace,
                             @PathVariable("podName") String podName) {
        return podsService.deletePods(cluster,namespace, podName);
    }
}