package org.paasta.container.platform.web.user.clusters.namespaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Namespaces Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.02
 */
@Api(value = "NamespacesController v1")
@RestController
public class NamespacesController {

    private static final String VIEW_URL = "/namespaces";

    private final CommonService commonService;
    private final NamespacesService namespacesService;

    /**
     * Instantiates a Namespaces controller
     *
     * @param commonService the common service
     * @param namespacesService the namespaces service
     */
    @Autowired
    public NamespacesController(CommonService commonService, NamespacesService namespacesService) {
        this.commonService = commonService;
        this.namespacesService = namespacesService;
    }

    /**
     * Namespaces 상세 조회(Get Namespaces detail)
     *
     * @param httpServletRequest the http servlet request
     * @return the namespaces detail
     */
    @ApiOperation(value = "Namespaces 상세 조회(Get Namespaces detail)", nickname = "getNamespacesDetail")
    @GetMapping(value = Constants.URI_CLUSTER_NAMESPACES + "/{namespace:.+}")
    public ModelAndView getNamespacesDetail(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }

    /**
     * Namespaces 상세 조회(Get Namespaces detail)
     *
     * @param namespace the namespaces
     * @return the namespaces detail
     */
    @ApiOperation(value = "Namespaces 상세 조회(Get Namespaces detail)", nickname = "getNamespaces")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_NAME_SPACES_DETAIL)
    public Namespaces getNamespaces(@PathVariable String namespace) {
        return namespacesService.getNamespaces(namespace);
    }


    /**
     * Namespaces event 페이지 이동(Move Namespaces event page)
     *
     * @param httpServletRequest the http servlet request
     * @param namespace
     * @return the namespaces event
     */
    @ApiOperation(value = "Namespaces event 페이지 이동(Move Namespaces event page)", nickname = "getNamespaceEvents")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_CLUSTER_NAMESPACES + "/{namespace:.+}/events")
    public ModelAndView getNamespaceEvents(HttpServletRequest httpServletRequest,
                                           @PathVariable String namespace) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }
}
