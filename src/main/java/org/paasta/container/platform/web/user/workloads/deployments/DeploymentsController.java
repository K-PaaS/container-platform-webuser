package org.paasta.container.platform.web.user.workloads.deployments;

import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Deployments Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@RestController
@RequestMapping
public class DeploymentsController {
    private final CommonService commonService;
    private final DeploymentsService deploymentsService;

    private static final String BASE_URL = "/deployments";

    @Autowired
    public DeploymentsController(CommonService commonService, DeploymentsService deploymentsService) {
        this.commonService = commonService;
        this.deploymentsService = deploymentsService;
    }

    /**
     * Deployments main 페이지 이동(Move Deployments main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the deployments main
     */
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments")
    public ModelAndView getDashboardMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/main", new ModelAndView());
    }

    /**
     * Deployments detail 페이지 이동(Move Deployments detail page)
     *
     * @param httpServletRequest the http servlet request
     * @return the deployments detail
     */
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments/{deploymentName}")
    public ModelAndView getDashboardDetail(HttpServletRequest httpServletRequest, @PathVariable(value = "deploymentName") String deploymentName) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/detail", new ModelAndView());
    }

    /**
     * Deployments event 페이지 이동(Move Deployments event page)
     *
     * @param httpServletRequest the http servlet request
     * @return the deployments detail event
     */
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments/{deploymentName}/events")
    public ModelAndView getDashboardEvent(HttpServletRequest httpServletRequest, @PathVariable(value = "deploymentName") String deploymentName) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/events", new ModelAndView());
    }

    /**
     * Deployments yaml 페이지 이동(Move Deployments yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @return the deployments detail yaml
     */
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments/{deploymentName}/yaml")
    public ModelAndView getDashboardYaml(HttpServletRequest httpServletRequest, @PathVariable(value = "deploymentName") String deploymentName) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/yaml", new ModelAndView());
    }

    /**
     * Deployments 목록 조회(Get Deployments list)
     *
     * @param namespace the namespace
     * @return the deployments list
     */
    @GetMapping( value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_LIST )
    public DeploymentsList getDeploymentsList(@PathVariable(value = "namespace") String namespace,
                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "0") int limit,
                                              @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                              @RequestParam(required = false, defaultValue = "desc") String order,
                                              @RequestParam(required = false, defaultValue = "") String searchName)
    {
        return deploymentsService.getDeploymentsList(namespace, offset, limit, orderBy, order, searchName);
    }

    /**
     * Deployments 상세 조회(Get Deployments detail)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return the deployments detail
     */
    @GetMapping( value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_DETAIL )
    public Deployments getDeployments(@PathVariable String namespace, @PathVariable String deploymentName) {
        return deploymentsService.getDeployments(namespace, deploymentName);
    }

    /**
     * Deployments YAML 정보 조회(Get Deployments yaml)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return the deployments yaml
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_YAML)
    @ResponseBody
    public Deployments getDeploymentsYaml(@PathVariable(value = "namespace") String namespace, @PathVariable("deploymentName") String deploymentName) {
        return deploymentsService.getDeploymentsYaml(namespace, deploymentName);
    }

    /**
     * Deployments 생성(Create Deployments)
     *
     * @param namespace the namespace
     * @param yaml the yaml
     * @return
     */
    @PostMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_CREATE)
    @ResponseBody
    public Object createDeployments(@PathVariable(value = "namespace") String namespace, @RequestBody String yaml) {
        return deploymentsService.createDeployments(namespace, yaml);

    }

    /**
     * Deployments 수정(Update Deployments)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @param yaml the yaml
     * @return
     */
    @PutMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_UPDATE)
    public Object updateDeployments(@PathVariable(value = "namespace") String namespace, @PathVariable("deploymentName") String deploymentName, @RequestBody String yaml) {
        return deploymentsService.updateDeployments(namespace, deploymentName, yaml);
    }

    /**
     * Deployments 삭제(Delete Deployments)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return
     */
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_DELETE)
    public Object deleteDeployments(@PathVariable(value = "namespace") String namespace, @PathVariable("deploymentName") String deploymentName) {
        return deploymentsService.deleteDeployments(namespace, deploymentName);
    }
}
