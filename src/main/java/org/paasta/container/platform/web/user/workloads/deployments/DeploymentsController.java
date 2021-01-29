package org.paasta.container.platform.web.user.workloads.deployments;

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
 * Deployments Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Api(value = "DeploymentsController v1")
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
    @ApiOperation(value = "Deployments main 페이지 이동(Move Deployments main page)", nickname = "getDashboardMain")
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments")
    public ModelAndView getDashboardMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/main", new ModelAndView());
    }

    /**
     * Deployments detail 페이지 이동(Move Deployments detail page)
     *
     * @param httpServletRequest the http servlet request
     * @param deploymentName the deployments name
     * @return the deployments detail
     */
    @ApiOperation(value = "Deployments detail 페이지 이동(Move Deployments detail page)", nickname = "getDashboardDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments/{deploymentName}")
    public ModelAndView getDashboardDetail(HttpServletRequest httpServletRequest,
                                           @PathVariable(value = "deploymentName") String deploymentName) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/detail", new ModelAndView());
    }

    /**
     * Deployments event 페이지 이동(Move Deployments event page)
     *
     * @param httpServletRequest the http servlet request
     * @param deploymentName the deployments name
     * @return the deployments detail event
     */
    @ApiOperation(value = "Deployments event 페이지 이동(Move Deployments event page)", nickname = "getDashboardEvent")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments/{deploymentName}/events")
    public ModelAndView getDashboardEvent(HttpServletRequest httpServletRequest,
                                          @PathVariable(value = "deploymentName") String deploymentName) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/events", new ModelAndView());
    }

    /**
     * Deployments yaml 페이지 이동(Move Deployments yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @param deploymentName the deployments name
     * @return the deployments detail yaml
     */
    @ApiOperation(value = "Deployments yaml 페이지 이동(Move Deployments yaml page)", nickname = "getDashboardYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.CP_BASE_URL + "/workloads/deployments/{deploymentName}/yaml")
    public ModelAndView getDashboardYaml(HttpServletRequest httpServletRequest,
                                         @PathVariable(value = "deploymentName") String deploymentName) {
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/yaml", new ModelAndView());
    }

    /**
     * Deployments 목록 조회(Get Deployments list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the deployments list
     */
    @ApiOperation(value = "Deployments 목록 조회(Get Deployments list)", nickname = "getDeploymentsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping( value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_LIST )
    public DeploymentsList getDeploymentsList(@PathVariable(value = "cluster") String cluster,
                                              @PathVariable(value = "namespace") String namespace,
                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "0") int limit,
                                              @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                              @RequestParam(required = false, defaultValue = "desc") String order,
                                              @RequestParam(required = false, defaultValue = "") String searchName) {

        return deploymentsService.getDeploymentsList(cluster, namespace, offset, limit, orderBy, order, searchName);
    }

    /**
     * Deployments 상세 조회(Get Deployments detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return the deployments detail
     */
    @ApiOperation(value = "Deployments 상세 조회(Get Deployments detail)", nickname = "getDeployments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping( value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_DETAIL )
    public Deployments getDeployments(@PathVariable(value = "cluster") String cluster,
                                      @PathVariable String namespace,
                                      @PathVariable String deploymentName) {
        return deploymentsService.getDeployments(cluster, namespace, deploymentName);
    }

    /**
     * Deployments YAML 정보 조회(Get Deployments yaml)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return the deployments yaml
     */
    @ApiOperation(value = "Deployments YAML 정보 조회(Get Deployments yaml)", nickname = "getDeploymentsYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_YAML)
    @ResponseBody
    public Deployments getDeploymentsYaml(@PathVariable(value = "cluster") String cluster,
                                          @PathVariable(value = "namespace") String namespace,
                                          @PathVariable("deploymentName") String deploymentName) {
        return deploymentsService.getDeploymentsYaml(cluster, namespace, deploymentName);
    }

    /**
     * Deployments 생성(Create Deployments)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param yaml the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "Deployments 생성(Create Deployments)", nickname = "createDeployments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 생성 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_CREATE)
    @ResponseBody
    public Object createDeployments(@PathVariable(value = "cluster") String cluster,
                                    @PathVariable(value = "namespace") String namespace,
                                    @RequestBody String yaml) {
        return deploymentsService.createDeployments(cluster, namespace, yaml);

    }

    /**
     * Deployments 수정(Update Deployments)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @param yaml the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "Deployments 수정(Update Deployments)", nickname = "updateDeployments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 수정 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_UPDATE)
    public Object updateDeployments(@PathVariable(value = "cluster") String cluster,
                                    @PathVariable(value = "namespace") String namespace,
                                    @PathVariable("deploymentName") String deploymentName,
                                    @RequestBody String yaml) {
        return deploymentsService.updateDeployments(cluster, namespace, deploymentName, yaml);
    }

    /**
     * Deployments 삭제(Delete Deployments)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return return is succeeded
     */
    @ApiOperation(value = "Deployments 삭제(Delete Deployments)", nickname = "deleteDeployments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "deploymentName", value = "deployment 명", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_DEPLOYMENTS_DELETE)
    public Object deleteDeployments(@PathVariable(value = "cluster") String cluster,
                                    @PathVariable(value = "namespace") String namespace,
                                    @PathVariable("deploymentName") String deploymentName) {
        return deploymentsService.deleteDeployments(cluster, namespace, deploymentName);
    }
}
