package org.container.platform.web.user.workloads.replicaSets;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.container.platform.web.user.common.CommonService;
import org.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * ReplicaSets Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.09
 */
@Api(value = "ReplicaSetsController v1")
@Controller
@RequestMapping
public class ReplicaSetsController {

    private static final String VIEW_URL = "/replicasets";
    private final CommonService commonService;
    private final ReplicaSetsService replicaSetService;

    /**
     * Instantiates a new ReplicaSets controller
     *
     * @param commonService      the common service
     * @param replicaSetsService the replicaSet service
     */
    @Autowired
    public ReplicaSetsController(CommonService commonService, ReplicaSetsService replicaSetsService) {
        this.commonService = commonService;
        this.replicaSetService = replicaSetsService;
    }

    /**
     * ReplicaSets main 페이지 이동(Move ReplicaSets main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the replicaSets main
     */
    @ApiOperation(value = "ReplicaSets main 페이지 이동(Move ReplicaSets main page)", nickname = "getReplicaSetsMain")
    @GetMapping(value = Constants.URI_WORKLOAD_REPLICA_SETS)
    public ModelAndView getReplicaSetsMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }


    /**
     * ReplicaSets detail 페이지 이동(Move ReplicaSets detail page)
     *
     * @param httpServletRequest the http servlet request
     * @param replicaSetName     the replicaSets name
     * @return the replicaSets detail
     */
    @ApiOperation(value = "ReplicaSets detail 페이지 이동(Move ReplicaSets detail page)", nickname = "getReplicaSetsDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_WORKLOAD_REPLICA_SETS + "/{replicaSetName:.+}")
    public ModelAndView getReplicaSetsDetail(HttpServletRequest httpServletRequest,
                                             @PathVariable("replicaSetName") String replicaSetName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }


    /**
     * ReplicaSets event 페이지 이동(Move ReplicaSets event page)
     *
     * @param httpServletRequest the http servlet request
     * @param replicaSetName     the replicaSets name
     * @return the replicaSets event
     */
    @ApiOperation(value = "ReplicaSets event 페이지 이동(Move ReplicaSets event page)", nickname = "getReplicaSetsDetailEvents")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_WORKLOAD_REPLICA_SETS + "/{replicaSetName:.+}/events")
    public ModelAndView getReplicaSetsDetailEvents(HttpServletRequest httpServletRequest,
                                                   @PathVariable("replicaSetName") String replicaSetName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }


    /**
     * ReplicaSets yaml 페이지 이동(Move ReplicaSets yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @param replicaSetName     the replicaSets name
     * @return the replicaSets yaml
     */
    @ApiOperation(value = "ReplicaSets yaml 페이지 이동(Move ReplicaSets yaml page)", nickname = "getReplicaSetsDetailYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_WORKLOAD_REPLICA_SETS + "/{replicaSetName:.+}/yaml")
    public ModelAndView getReplicaSetsDetailYaml(HttpServletRequest httpServletRequest,
                                                 @PathVariable("replicaSetName") String replicaSetName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }


    /**
     * ReplicaSets 목록 조회(Get ReplicaSets list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the replicaSets list
     */
    @ApiOperation(value = "ReplicaSets 목록 조회(Get ReplicaSets list)", nickname = "getReplicaSetsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_LIST)
    @ResponseBody
    public ReplicaSetsList getReplicaSetsList(@PathVariable(value = "cluster") String cluster,
                                              @PathVariable(value = "namespace") String namespace,
                                              @RequestParam(required = false, defaultValue = "0") int offset,
                                              @RequestParam(required = false, defaultValue = "0") int limit,
                                              @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                              @RequestParam(required = false, defaultValue = "desc") String order,
                                              @RequestParam(required = false, defaultValue = "") String searchName) {

        return replicaSetService.getReplicaSetsList(cluster, namespace, offset, limit, orderBy, order, searchName);
    }


    /**
     * ReplicaSets 상세 조회(Get ReplicaSets detail)
     *
     * @param cluster        the cluster
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @return the replicaSets detail
     */
    @ApiOperation(value = "ReplicaSets 상세 조회(Get ReplicaSets detail)", nickname = "getReplicaSets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_DETAIL)
    @ResponseBody
    public ReplicaSets getReplicaSets(@PathVariable(value = "cluster") String cluster,
                                      @PathVariable("namespace") String namespace,
                                      @PathVariable("replicaSetName") String replicaSetName) {
        return replicaSetService.getReplicaSets(cluster, namespace, replicaSetName);
    }


    /**
     * ReplicaSets YAML 조회(Get ReplicaSets yaml)
     *
     * @param cluster        the cluster
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @return the replicaSets yaml
     */
    @ApiOperation(value = "ReplicaSets YAML 조회(Get ReplicaSets yaml)", nickname = "getReplicaSetsYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_YAML)
    @ResponseBody
    public ReplicaSets getReplicaSetsYaml(@PathVariable(value = "cluster") String cluster,
                                          @PathVariable("namespace") String namespace,
                                          @PathVariable("replicaSetName") String replicaSetName) {
        return replicaSetService.getReplicaSetsYaml(cluster, namespace, replicaSetName);
    }


    /**
     * Selector 값에 의한 ReplicaSets 목록 조회 (Get ReplicaSets By Selector)
     *
     * @param cluster             the cluster
     * @param namespace           the namespace
     * @param selector            the selector
     * @param type                the type
     * @param ownerReferencesName the ownerReferencesName
     * @param ownerReferencesUid  the ownerReferencesUid
     * @param offset              the offset
     * @param limit               the limit
     * @param orderBy             the orderBy
     * @param order               the order
     * @param searchName          the searchName
     * @return the replicaSets list
     */
    @ApiOperation(value = "Selector 값에 의한 ReplicaSets 목록 조회 (Get ReplicaSets By Selector)", nickname = "getReplicaSetsListLabelSelector")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "selector", value = "셀렉터", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "리소스 타입", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ownerReferencesName", value = "참조 리소스 명", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ownerReferencesUid", value = "참조 리소스의 UID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_RESOURCES)
    @ResponseBody
    public ReplicaSetsList getReplicaSetsListLabelSelector(@PathVariable(value = "cluster") String cluster,
                                                           @PathVariable("namespace") String namespace,
                                                           @RequestParam(name = "selector", required = true, defaultValue = "") String selector,
                                                           @RequestParam(name = "type", required = false, defaultValue = "default") String type,
                                                           @RequestParam(name = "ownerReferencesName", required = false, defaultValue = "") String ownerReferencesName,
                                                           @RequestParam(name = "ownerReferencesUid", required = false, defaultValue = "") String ownerReferencesUid,
                                                           @RequestParam(required = false, defaultValue = "0") int offset,
                                                           @RequestParam(required = false, defaultValue = "0") int limit,
                                                           @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                                           @RequestParam(required = false, defaultValue = "desc") String order,
                                                           @RequestParam(required = false, defaultValue = "") String searchName) {
        return replicaSetService.getReplicaSetsListLabelSelector(cluster, namespace, selector, type, ownerReferencesName, ownerReferencesUid, offset, limit, orderBy, order, searchName);
    }

    /**
     * ReplicaSets 생성(Create ReplicaSets)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "ReplicaSets 생성(Create ReplicaSets)", nickname = "createReplicaSets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 생성 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_CREATE)
    @ResponseBody
    public Object createReplicaSets(@PathVariable(value = "cluster") String cluster,
                                    @PathVariable(value = "namespace") String namespace,
                                    @RequestBody String yaml) {
        return replicaSetService.createReplicaSets(cluster, namespace, yaml);

    }

    /**
     * ReplicaSets 수정(Update ReplicaSets)
     *
     * @param cluster        the cluster
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @param yaml           the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "ReplicaSets 수정(Update ReplicaSets)", nickname = "updateCustomReplicaSets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 수정 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_UPDATE)
    @ResponseBody
    public Object updateCustomReplicaSets(@PathVariable(value = "cluster") String cluster,
                                          @PathVariable(value = "namespace") String namespace,
                                          @PathVariable("replicaSetName") String replicaSetName,
                                          @RequestBody String yaml) {
        return replicaSetService.updateReplicaSets(cluster, namespace, replicaSetName, yaml);
    }

    /**
     * ReplicaSets 삭제(Delete ReplicaSets)
     *
     * @param cluster        the cluster
     * @param namespace      the namespace
     * @param replicaSetName the replicaSets name
     * @return return is succeeded
     */
    @ApiOperation(value = "ReplicaSets 삭제(Delete ReplicaSets)", nickname = "deleteReplicaSets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "replicaSetName", value = "ReplicaSets 명", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_REPLICA_SETS_DELETE)
    @ResponseBody
    public Object deleteReplicaSets(@PathVariable(value = "cluster") String cluster,
                                    @PathVariable("namespace") String namespace,
                                    @PathVariable("replicaSetName") String replicaSetName) {
        return replicaSetService.deleteReplicaSets(cluster, namespace, replicaSetName);
    }
}
