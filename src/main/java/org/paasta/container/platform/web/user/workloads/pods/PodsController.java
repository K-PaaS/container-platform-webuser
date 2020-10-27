package org.paasta.container.platform.web.user.workloads.pods;

import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.workloads.replicaSets.ReplicaSets;
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
    @GetMapping(value = Constants.URI_WORKLOAD_PODS)
    public ModelAndView getPodList(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }

    /**
     * Pods details 페이지 이동(Move Pods detail page)
     *
     * @param httpServletRequest the http servlet request
     * @param podName the pods name
     * @return the pods detail
     */
    @GetMapping(value = Constants.URI_WORKLOAD_PODS + "/{podName:.+}")
    public ModelAndView getPodDetails(HttpServletRequest httpServletRequest, @PathVariable(value = "podName") String podName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/details", new ModelAndView());
    }

    /**
     * Pods event 페이지 이동(Move Pods event page)
     *
     * @param httpServletRequest the http servlet request
     * @param podName the pods name
     * @return the pods event
     */
    @GetMapping(value = Constants.URI_WORKLOAD_PODS + "/{podName:.+}/events")
    public ModelAndView getPodEvents(HttpServletRequest httpServletRequest, @PathVariable(value = "podName") String podName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }

    /**
     * Pods yaml 페이지 이동(Move Pods yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @param podName the pods name
     * @return the pods yaml
     */
    @GetMapping(value = Constants.URI_WORKLOAD_PODS + "/{podName:.+}/yaml")
    public ModelAndView getPodYaml(HttpServletRequest httpServletRequest, @PathVariable(value = "podName") String podName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }

    /**
     * Pods 목록 조회(Get Pods list)
     *
     * @param namespace the namespace
     * @return the pods list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST)
    public PodsList getPodList(@PathVariable String namespace,
                               @RequestParam(required = false, defaultValue = "0") int limit,
                               @RequestParam(required = false, name = "continue") String continueToken) {
        return podsService.getPodList(namespace, limit, continueToken);
    }

    /**
     * Pods 상세 조회(Get Pods detail)
     *
     * @param namespace the namespace
     * @param podName   the pods name
     * @return the pods
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_DETAIL)
    public Pods getPod(@PathVariable(value = "namespace") String namespace,
                       @PathVariable(value = "podName") String podName) {
        return podsService.getPod(namespace, podName);
    }

    /**
     * Pods YAML 조회(Get Pods yaml)
     *
     * @param namespace the namespace
     * @param podName the pods name
     * @return the pods yaml
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_YAML)
    public Pods getPodYaml(@PathVariable(value = "namespace") String namespace,
                           @PathVariable(value = "podName") String podName) {
        return podsService.getPodYaml(namespace, podName);
    }

    /**
     * Pods 목록 조회(Get Pods selector)
     *
     * @param namespace the namespace
     * @param selector  the selector
     * @return the pods list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST_BY_SELECTOR)
    @ResponseBody
    public PodsList getPodListBySelector(@PathVariable("namespace") String namespace,
                                         @PathVariable("selector") String selector) {
        return podsService.getPodListBySelector(namespace, selector);
    }


    /**
     * Pods 목록 조회(Get Pods selector, service)
     *
     * @param namespace   the namespace
     * @param serviceName the service name
     * @param selector    the selector
     * @return the pods list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST_BY_SELECTOR_WITH_SERVICE)
    @ResponseBody
    public PodsList getPodListBySelectorWithService(@PathVariable("namespace") String namespace,
                                                    @PathVariable("serviceName") String serviceName,
                                                    @PathVariable("selector") String selector) {
        PodsList podsList = podsService.getPodListBySelector(namespace, selector);
        podsList.setServiceName(serviceName);  // FOR DASHBOARD
        podsList.setSelector(selector);        // FOR DASHBOARD

        return podsList;
    }

    /**
     * Pods 목록 조회(Get Pods node)
     *
     * @param namespace the namespace
     * @param nodeName  the node name
     * @return the pods list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PODS_LIST_BY_NODE)
    @ResponseBody
    public PodsList getPodListByNode(@PathVariable(value = "namespace") String namespace,
                                     @PathVariable(value = "nodeName") String nodeName) {
        return podsService.getPodListNamespaceByNode(namespace, nodeName);
    }

    /**
     * Pods 생성(Create Pods)
     *
     * @param namespace the namespace
     * @param yaml the yaml
     * @return return is succeeded
     */
    @PostMapping(value = Constants.API_URL + Constants.URI_API_PODS_CREATE)
    @ResponseBody
    public Object createDeployments(@PathVariable(value = "namespace") String namespace, @RequestBody String yaml) {
        return podsService.createPods(namespace, yaml);

    }

    /**
     * Pods 수정(Update Pods)
     *
     * @param namespace   the namespace
     * @param podName the pod name
     * @param yaml        the yaml
     * @return return is succeeded
     */
    @PutMapping(value = Constants.API_URL + Constants.URI_API_POD_UPDATE)
    @ResponseBody
    public Object updatePods(@PathVariable(value = "namespace") String namespace, @PathVariable("podName") String podName, @RequestBody String yaml) {
        return podsService.updatePods(namespace, podName, yaml);
    }


    /**
     * Pods 삭제(Delete Pods)
     *
     * @param namespace the namespace
     * @param podName the pod name
     * @return return is succeeded
     */
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_POD_DELETE)
    @ResponseBody
    public Object deletePods(@PathVariable(value = "namespace") String namespace, @PathVariable("podName") String podName ){
        return podsService.deletePods(namespace, podName);
    }
}