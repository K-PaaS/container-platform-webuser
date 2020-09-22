package org.paasta.container.platform.web.user.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Common Controller 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.09.15
 */
@RestController
public class commonController {

    private static final String BASE_URL = "/common";
    private final CommonService commonService;


    /**
     * Instantiates a new User controller.
     *
     * @param commonService the common service
     */
    @Autowired
    public commonController(CommonService commonService) {
        this.commonService = commonService;
    }



    /**
     * 리소스 생성화면으로 이동한다.
     *
     * @param httpServletRequest the http servlet request
     * @return the common resource create page
     */
    @GetMapping(value = Constants.CP_BASE_URL + Constants.URI_API_COMMON_RESOURCE_CREATE_VIEW)
    public ModelAndView createResource(HttpServletRequest httpServletRequest,
                                       @PathVariable(value = "namespace") String namespace,
                                       @PathVariable(value = "resourceKind") String resourceKind) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("namespace", namespace);
        mv.addObject("resourceKind", resourceKind);
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/resourceCreate", mv);
    }


    /**
     * 리소스 수정화면으로 이동한다.
     *
     * @param httpServletRequest the http servlet request
     * @return the common resource update page
     */
    @GetMapping(value = Constants.CP_BASE_URL + Constants.URI_API_COMMON_RESOURCE_UPDATE_VIEW)
    public ModelAndView updateResource(HttpServletRequest httpServletRequest,
                                       @PathVariable(value = "namespace") String namespace,
                                       @PathVariable(value = "resourceKind") String resourceKind,
                                       @PathVariable(value = "resourceName") String resourceName) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("namespace", namespace);
        mv.addObject("resourceKind", resourceKind);
        mv.addObject("resourceName", resourceName);

        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/resourceUpdate", mv);
    }




}