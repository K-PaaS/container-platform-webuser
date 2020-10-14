package org.paasta.container.platform.web.user.roles;

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
@Controller
public class RolesController {

    private static final String VIEW_URL = "/roles";
    private final CommonService commonService;
    private final RolesService rolesService;

    /**
     * Instantiates a new roles Roles controller.
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
     * Roles main 페이지로 이동한다.
     *
     * @param httpServletRequest the http servlet request
     * @return the roles main
     */
    @GetMapping(value = Constants.URI_ROLES)
    public ModelAndView getRolesMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }


    /**
     * Roles detail 페이지로 이동한다.
     *
     * @param httpServletRequest the http servlet request
     * @return the roles detail
     */
    @GetMapping(value = Constants.URI_ROLES + "/{roleName:.+}")
    public ModelAndView getRolesDetail(HttpServletRequest httpServletRequest, @PathVariable(value = "roleName") String roleName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }


    /**
     * Roles events 페이지로 이동한다.
     *
     * @param httpServletRequest the http servlet request
     * @return the roles detail events
     */
    @GetMapping(value = Constants.URI_ROLES + "/{roleName:.+}/events")
    public ModelAndView getRolesDetailEvents(HttpServletRequest httpServletRequest, @PathVariable(value = "roleName") String roleName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }


    /**
     * Roles yaml 페이지로 이동한다.
     *
     * @param httpServletRequest the http servlet request
     * @return the roles detail yaml
     */
    @GetMapping(value = Constants.URI_ROLES + "/{roleName:.+}/yaml")
    public ModelAndView getRolesDetailYaml(HttpServletRequest httpServletRequest, @PathVariable(value = "roleName") String roleName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }


    /**
     * Roles 목록을 조회한다.
     *
     * @param namespace the namespace
     * @return the roles list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ROLES_LIST)
    @ResponseBody
    public RolesList getRolesList(@PathVariable(value = "namespace") String namespace) {
        return rolesService.getRolesList(namespace);
    }


    /**
     * Roles 상세 정보를 조회한다.
     *
     * @param namespace   the namespace
     * @param roleName the role name
     * @return the roles
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ROLES_DETAIL)
    @ResponseBody
    public Roles getRoles(@PathVariable(value = "namespace") String namespace, @PathVariable(value = "roleName") String roleName) {
        return rolesService.getRoles(namespace, roleName);
    }


    /**
     * Roles YAML 정보를 조회한다.
     *
     * @param namespace   the namespace
     * @param roleName the role name
     * @return the roles yaml
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ROLES_YAML)
    @ResponseBody
    public Roles getRolesYaml(@PathVariable(value = "namespace") String namespace,@PathVariable(value = "roleName") String roleName) {
        return rolesService.getRolesYaml(namespace, roleName);
    }



}
