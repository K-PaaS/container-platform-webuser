package org.paasta.container.platform.web.user.roles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Roles Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.10.13
 */
@Api(value = "RolesController v1")
@Controller
public class RolesController {

    private static final String VIEW_URL = "/managements/roles";
    private final CommonService commonService;
    private final RolesService rolesService;

    /**
     * Instantiates a new roles Roles controller
     *
     * @param commonService  the common service
     * @param rolesService  the roles service
     */
    @Autowired
    public RolesController(CommonService commonService, RolesService rolesService) {
        this.commonService = commonService;
        this.rolesService = rolesService;
    }


    /**
     * Roles main 페이지 이동(Move Roles main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the roles main
     */
    @ApiOperation(value = "Roles main 페이지 이동(Move Roles main page)", nickname = "getRolesMain")
    @GetMapping(value = Constants.URI_ROLES)
    public ModelAndView getRolesMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }


    /**
     * Roles detail 페이지 이동(Move Roles detail page)
     *
     * @param httpServletRequest the http servlet request
     * @return the roles detail
     */
    @ApiOperation(value = "Roles detail 페이지 이동(Move Roles detail page)", nickname = "getRolesDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "Roles 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.URI_ROLES + "/{roleName:.+}")
    public ModelAndView getRolesDetail(HttpServletRequest httpServletRequest,
                                       @PathVariable(value = "roleName") String roleName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }


    /**
     * Roles event 페이지 이동(Move Roles event page)
     *
     * @param httpServletRequest the http servlet request
     * @return the roles event
     */
    @ApiOperation(value = "Roles event 페이지 이동(Move Roles event page)", nickname = "getRolesDetailEvents")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "Roles 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.URI_ROLES + "/{roleName:.+}/events")
    public ModelAndView getRolesDetailEvents(HttpServletRequest httpServletRequest,
                                             @PathVariable(value = "roleName") String roleName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }


    /**
     * Roles yaml 페이지 이동(Move Roles yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @return the roles yaml
     */
    @ApiOperation(value = "Roles yaml 페이지 이동(Move Roles yaml page)", nickname = "getRolesDetailYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "Roles 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.URI_ROLES + "/{roleName:.+}/yaml")
    public ModelAndView getRolesDetailYaml(HttpServletRequest httpServletRequest,
                                           @PathVariable(value = "roleName") String roleName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }


    /**
     * Roles 목록 조회(Get Roles list)
     *
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the roles list
     */
    @ApiOperation(value = "Roles 목록 조회(Get Roles list)", nickname = "getRolesList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "string", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ROLES_LIST)
    @ResponseBody
    public RolesList getRolesList(@PathVariable(value = "namespace") String namespace,
                                  @RequestParam(required = false, defaultValue = "0") int offset,
                                  @RequestParam(required = false, defaultValue = "0") int limit,
                                  @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                  @RequestParam(required = false, defaultValue = "desc") String order,
                                  @RequestParam(required = false, defaultValue = "") String searchName) {

        return rolesService.getRolesList(namespace, offset, limit, orderBy, order, searchName);
    }



    /**
     * Roles 상세 조회(Get Roles detail)
     *
     * @param namespace the namespace
     * @param roleName the roles name
     * @return the roles detail
     */
    @ApiOperation(value = "Roles 상세 조회(Get Roles detail)", nickname = "getRoles")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "roleName", value = "Roles 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ROLES_DETAIL)
    @ResponseBody
    public Roles getRoles(@PathVariable(value = "namespace") String namespace,
                          @PathVariable(value = "roleName") String roleName) {
        return rolesService.getRoles(namespace, roleName);
    }

    /**
     * Roles YAML 조회(Get Roles yaml)
     *
     * @param namespace the namespace
     * @param roleName the roles name
     * @return the roles yaml
     */
    @ApiOperation(value = "Roles YAML 조회(Get Roles yaml)", nickname = "getRolesYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "roleName", value = "Roles 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ROLES_YAML)
    @ResponseBody
    public Roles getRolesYaml(@PathVariable(value = "namespace") String namespace,
                              @PathVariable(value = "roleName") String roleName) {
        return rolesService.getRolesYaml(namespace, roleName);
    }
}
