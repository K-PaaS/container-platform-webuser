package org.container.platform.web.user.persistentVolumeClaims;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.container.platform.web.user.common.CommonService;
import org.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * PersistentVolumeClaims Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@Api(value = "PersistentVolumeClaimsController v1")
@RestController
@RequestMapping
public class PersistentVolumeClaimsController {

    private static final String VIEW_URL = "/persistentVolumeClaims";
    private final CommonService commonService;
    private final PersistentVolumeClaimsService persistentVolumeClaimsService;

    /**
     * Instantiates a new persistentVolumeClaims controller
     *
     * @param commonService                 the common service
     * @param persistentVolumeClaimsService the persistentVolumeClaims Service
     */
    @Autowired
    public PersistentVolumeClaimsController(CommonService commonService, PersistentVolumeClaimsService persistentVolumeClaimsService) {
        this.commonService = commonService;
        this.persistentVolumeClaimsService = persistentVolumeClaimsService;
    }


    /**
     * PersistentVolumeClaim main 페이지 이동(Move PersistentVolumeClaim main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the persistentVolumeClaims main
     */
    @ApiOperation(value = "PersistentVolumeClaim main 페이지 이동(Move PersistentVolumeClaim main page)", nickname = "getStoragesMain")
    @GetMapping(value = Constants.URI_STORAGES)
    public ModelAndView getStoragesMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }


    /**
     * PersistentVolumeClaim detail 페이지 이동(Move PersistentVolumeClaim detail page)
     *
     * @param httpServletRequest the http servlet request
     * @param persistentVolumeClaimName the persistentVolumeClaim name
     * @return the persistentVolumeClaims detail
     */
    @ApiOperation(value = "PersistentVolumeClaim detail 페이지 이동(Move PersistentVolumeClaim detail page)", nickname = "getCustomServices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "persistentVolumeClaim 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_STORAGES + "/{persistentVolumeClaimName:.+}")
    public ModelAndView getStoragesDetail(HttpServletRequest httpServletRequest,
                                          @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }


    /**
     * PersistentVolumeClaim event 페이지 이동(Move PersistentVolumeClaim event page)
     *
     * @param httpServletRequest the http servlet request
     * @param persistentVolumeClaimName the persistentVolumeClaim name
     * @return the persistentVolumeClaims event
     */
    @ApiOperation(value = "PersistentVolumeClaim event 페이지 이동(Move PersistentVolumeClaim event page)", nickname = "getCustomServices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "getPersistentVolumeClaimEvent 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_STORAGES + "/{persistentVolumeClaimName:.+}/events")
    public ModelAndView getPersistentVolumeClaimEvent(HttpServletRequest httpServletRequest,
                                                      @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }


    /**
     * PersistentVolumeClaim yaml 페이지 이동(Move PersistentVolumeClaim event yaml)
     *
     * @param httpServletRequest the http servlet request
     * @param persistentVolumeClaimName the persistentVolumeClaim name
     * @return the persistentVolumeClaims yaml
     */
    @ApiOperation(value = "PersistentVolumeClaim yaml 페이지 이동(Move PersistentVolumeClaim event yaml)", nickname = "getPersistentVolumeClaimYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "getPersistentVolumeClaimEvent 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_STORAGES + "/{persistentVolumeClaimName:.+}/yaml")
    public ModelAndView getPersistentVolumeClaimYaml(HttpServletRequest httpServletRequest,
                                                     @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }


    /**
     * PersistentVolumeClaims 목록 조회(Get PersistentVolumeClaims list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the persistentVolumeClaims list
     */
    @ApiOperation(value = "PersistentVolumeClaims 목록 조회(Get PersistentVolumeClaims list)", nickname = "getPersistentVolumeClaimsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_LIST)
    @ResponseBody
    public PersistentVolumeClaimsList getPersistentVolumeClaimsList(@PathVariable(value = "cluster") String cluster,
                                                                    @PathVariable(value = "namespace") String namespace,
                                                                    @RequestParam(required = false, defaultValue = "0") int offset,
                                                                    @RequestParam(required = false, defaultValue = "0") int limit,
                                                                    @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                                                    @RequestParam(required = false, defaultValue = "desc") String order,
                                                                    @RequestParam(required = false, defaultValue = "") String searchName) {

        return persistentVolumeClaimsService.getPersistentVolumeClaimsList(cluster, namespace, offset, limit, orderBy, order, searchName);
    }


    /**
     * PersistentVolumeClaims 상세 조회(Get PersistentVolumeClaims detail)
     *
     * @param cluster   the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return the persistentVolumeClaims detail
     */
    @ApiOperation(value = "PersistentVolumeClaims 상세 조회(Get PersistentVolumeClaims detail)", nickname = "getPersistentVolumeClaims")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "persistentVolumeClaim 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_DETAIL)
    public PersistentVolumeClaims getPersistentVolumeClaims(@PathVariable(value = "cluster") String cluster,
                                                            @PathVariable(value = "namespace") String namespace,
                                                            @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return persistentVolumeClaimsService.getPersistentVolumeClaims(cluster, namespace, persistentVolumeClaimName);
    }


    /**
     * PersistentVolumeClaims YAML 조회(Get PersistentVolumeClaims yaml)
     *
     * @param cluster   the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return the persistentVolumeClaims yaml
     */
    @ApiOperation(value = "PersistentVolumeClaims YAML 조회(Get PersistentVolumeClaims yaml)", nickname = "getPersistentVolumeClaimYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "persistentVolumeClaim 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_YAML)
    public PersistentVolumeClaims getPersistentVolumeClaimYaml(@PathVariable(value = "cluster") String cluster,
                                                               @PathVariable(value = "namespace") String namespace,
                                                               @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return persistentVolumeClaimsService.getPersistentVolumeClaimYaml(cluster, namespace, persistentVolumeClaimName);
    }

    /**
     * PersistentVolumeClaims 생성(Create PersistentVolumeClaims)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "PersistentVolumeClaims 생성(Create PersistentVolumeClaims)", nickname = "createPersistentVolumeClaims")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 생성 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_CREATE)
    @ResponseBody
    public Object createPersistentVolumeClaims(@PathVariable(value = "cluster") String cluster,
                                               @PathVariable(value = "namespace") String namespace,
                                               @RequestBody String yaml) {
        return persistentVolumeClaimsService.createPersistentVolumeClaims(cluster, namespace, yaml);

    }

    /**
     * PersistentVolumeClaims 수정(Update PersistentVolumeClaims)
     *
     * @param cluster     the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @param yaml                      the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "PersistentVolumeClaims 수정(Update PersistentVolumeClaims)", nickname = "updatePersistentVolumeClaims")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "persistentVolumeClaim 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 생성 yaml", required = true, dataType = "String", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_UPDATE)
    @ResponseBody
    public Object updatePersistentVolumeClaims(@PathVariable(value = "cluster") String cluster,
                                               @PathVariable(value = "namespace") String namespace,
                                               @PathVariable("persistentVolumeClaimName") String persistentVolumeClaimName,
                                               @RequestBody String yaml) {
        return persistentVolumeClaimsService.updatePersistentVolumeClaims(cluster, namespace, persistentVolumeClaimName, yaml);
    }

    /**
     * PersistentVolumeClaims 삭제(Delete PersistentVolumeClaims)
     *
     * @param cluster     the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return return is succeeded
     */
    @ApiOperation(value = "PersistentVolumeClaims 삭제(Delete PersistentVolumeClaims)", nickname = "deletePersistentVolumeClaims")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "persistentVolumeClaimName", value = "persistentVolumeClaim 명", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_DELETE)
    @ResponseBody
    public Object deletePersistentVolumeClaims(@PathVariable(value = "cluster") String cluster,
                                               @PathVariable(value = "namespace") String namespace,
                                               @PathVariable("persistentVolumeClaimName") String persistentVolumeClaimName) {
        return persistentVolumeClaimsService.deletePersistentVolumeClaims(cluster, namespace, persistentVolumeClaimName);
    }
}
