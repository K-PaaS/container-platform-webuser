package org.paasta.container.platform.web.user.customServices;

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
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return the custom service detail
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_DETAIL)
    @ResponseBody
    public CustomServices getCustomServices(@PathVariable(value = "namespace") String namespace, @PathVariable("serviceName") String serviceName) {
        return customServicesService.getCustomServices(namespace, serviceName);
    }


    /**
     * Services YAML 조회(Get Services yaml)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return the custom services yaml
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_YAML)
    @ResponseBody
    public CustomServices getCustomServicesYaml(@PathVariable(value = "namespace") String namespace, @PathVariable("serviceName") String serviceName) {
        return customServicesService.getCustomServicesYaml(namespace, serviceName);
    }


    /**
     * Services 생성(Create Services)
     *
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return
     */
    @PostMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_CREATE)
    @ResponseBody
    public Object createCustomServices(@PathVariable(value = "namespace") String namespace, @RequestBody String yaml) {
        return customServicesService.createCustomServices(namespace, yaml);

    }


    /**
     * Services 수정(Update Services)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @param yaml        the yaml
     * @return
     */
    @PutMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_UPDATE)
    @ResponseBody
    public Object updateCustomServices(@PathVariable(value = "namespace") String namespace, @PathVariable("serviceName") String serviceName, @RequestBody String yaml) {
        return customServicesService.updateCustomServices(namespace, serviceName, yaml);
    }


    /**
     * Services 삭제(Delete Services)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return
     */
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_SERVICES_DELETE)
    @ResponseBody
    public Object deleteCustomServices(@PathVariable(value = "namespace") String namespace, @PathVariable("serviceName") String serviceName) {
        return customServicesService.deleteCustomServices(namespace, serviceName);
    }


}
