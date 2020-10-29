package org.paasta.container.platform.web.user.customServices;

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
 * Custom Services Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.10
 */
@Api(value = "NamespacesController v1")
@Controller
public class CustomServicesController {

    private static final String VIEW_URL = "/services";
    private final CommonService commonService;
    private final CustomServicesService customServicesService;

    /**
     * Instantiates a new Custom services controller
     *
     * @param commonService         the common service
     * @param customServicesService the custom services service
     */
    @Autowired
    public CustomServicesController(CommonService commonService, CustomServicesService customServicesService) {
        this.commonService = commonService;
        this.customServicesService = customServicesService;
    }


    /**
     * Services main 페이지 이동(Move Services main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the custom services main
     */
    @ApiOperation(value = "Services main 페이지 이동(Move Services main page)", nickname = "getCustomServicesMain")
    @GetMapping(value = Constants.URI_SERVICES)
    public ModelAndView getCustomServicesMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }


    /**
     * Services detail 페이지 이동(Move Services detail page)
     *
     * @param httpServletRequest the http servlet request
     * @return the custom services detail
     */
    @ApiOperation(value = "Services detail 페이지 이동(Move Services detail page)", nickname = "getCustomServicesDetail")
    @GetMapping(value = Constants.URI_SERVICES + "/{serviceName:.+}")
    public ModelAndView getCustomServicesDetail(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }


    /**
     * Services event 페이지 이동(Move Services event page)
     *
     * @param httpServletRequest the http servlet request
     * @return the custom services event
     */
    @ApiOperation(value = "Services event 페이지 이동(Move Services event page)", nickname = "getCustomServicesDetailEvents")
    @GetMapping(value = Constants.URI_SERVICES + "/{serviceName:.+}/events")
    public ModelAndView getCustomServicesDetailEvents(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }


    /**
     * Services yaml 페이지 이동(Move Services yaml page)
     *
     * @param httpServletRequest the http servlet request
     * @return the custom services yaml
     */
    @ApiOperation(value = "Services yaml 페이지 이동(Move Services yaml page)", nickname = "getCustomServicesDetailYaml")
    @GetMapping(value = Constants.URI_SERVICES + "/{serviceName:.+}/yaml")
    public ModelAndView getCustomServicesDetailYaml(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }


    /**
     * Services 목록 조회(Get Services list)
     *
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the custom services list
     */
    @ApiOperation(value = "Services 목록 조회(Get Services list)", nickname = "getCustomServicesList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "offset", value = "목록 시작지점, 기본값 0", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "한 페이지에 가져올 리소스 최대 수", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "정렬 기준, 기본값 creationTime(생성날짜)", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "정렬 순서, 기본값 desc(내림차순)", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "리소스 명 검색", required = false, dataType = "string", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_LIST)
    @ResponseBody
    public CustomServicesList getCustomServicesList(@PathVariable(value = "namespace") String namespace,
                                                    @RequestParam(required = false, defaultValue = "0") int offset,
                                                    @RequestParam(required = false, defaultValue = "0") int limit,
                                                    @RequestParam(required = false, defaultValue = "creationTime") String orderBy,
                                                    @RequestParam(required = false, defaultValue = "desc") String order,
                                                    @RequestParam(required = false, defaultValue = "") String searchName)
    {

        return customServicesService.getCustomServicesList(namespace, offset, limit, orderBy, order, searchName);
    }


    /**
     * Services 상세 조회(Get Services detail)
     *
     * @param namespace the namespace
     * @param serviceName the services name
     * @return the custom services detail
     */
    @ApiOperation(value = "Services 상세 조회(Get Services detail)", nickname = "getCustomServices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "serviceName", value = "서비스 명",  required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_DETAIL)
    @ResponseBody
    public CustomServices getCustomServices(@PathVariable(value = "namespace") String namespace,
                                            @PathVariable("serviceName") String serviceName) {
        return customServicesService.getCustomServices(namespace, serviceName);
    }


    /**
     * Services YAML 조회(Get Services yaml)
     *
     * @param namespace the namespace
     * @param serviceName the services name
     * @return the custom services yaml
     */
    @ApiOperation(value = "Services YAML 조회(Get Services yaml)", nickname = "getCustomServicesYaml")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "serviceName", value = "서비스 명", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_YAML)
    @ResponseBody
    public CustomServices getCustomServicesYaml(@PathVariable(value = "namespace") String namespace,
                                                @PathVariable("serviceName") String serviceName) {
        return customServicesService.getCustomServicesYaml(namespace, serviceName);
    }


    /**
     * Services 생성(Create Services)
     *
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "Services 생성(Create Services)", nickname = "createCustomServices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 생성 yaml", required = true, dataType = "string", paramType = "body")
    })
    @PostMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_CREATE)
    @ResponseBody
    public Object createCustomServices(@PathVariable(value = "namespace") String namespace,
                                       @RequestBody String yaml) {
        return customServicesService.createCustomServices(namespace, yaml);

    }


    /**
     * Services 수정(Update Services)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @param yaml        the yaml
     * @return return is succeeded
     */
    @ApiOperation(value = "Services 수정(Update Services)", nickname = "updateCustomServices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "serviceName", value = "서비스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "yaml", value = "리소스 수정 yaml", required = true, dataType = "string", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_UPDATE)
    @ResponseBody
    public Object updateCustomServices(@PathVariable(value = "namespace") String namespace,
                                       @PathVariable("serviceName") String serviceName,
                                       @RequestBody String yaml) {
        return customServicesService.updateCustomServices(namespace, serviceName, yaml);
    }


    /**
     * Services 삭제(Delete Services)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return return is succeeded
     */
    @ApiOperation(value = "Services 삭제(Delete Services)", nickname = "deleteServices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "serviceName", value = "서비스 명", required = true, dataType = "string", paramType = "path")
    })
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_DELETE)
    @ResponseBody
    public Object deleteCustomServices(@PathVariable(value = "namespace") String namespace,
                                       @PathVariable("serviceName") String serviceName) {
        return customServicesService.deleteCustomServices(namespace, serviceName);
    }


}
