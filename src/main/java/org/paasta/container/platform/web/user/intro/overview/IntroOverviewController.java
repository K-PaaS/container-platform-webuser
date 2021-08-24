package org.paasta.container.platform.web.user.intro.overview;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.config.NoAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * Intro Overview Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.15
 */
@Api(value = "IntroOverviewController v1")
@Controller
public class IntroOverviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntroOverviewController.class);
    private static final String VIEW_URL = "/intro";
    private final CommonService commonService;

    /**
     * Instantiates a new Intro overview controller
     *
     * @param commonService the common service
     */
    @Autowired
    public IntroOverviewController(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * Intro overview 페이지 이동(Move Intro overview page)
     *
     * @return the view
     */
    @ApiOperation(value = "Intro overview 페이지 이동(Move Intro overview page)", nickname = "indexView")
    @GetMapping("/")
    @NoAuth
    public RedirectView indexView(@RequestParam(name = Constants.SERVICE_SESSION_REFRESH, required = false, defaultValue = "false") String sessionRefresh,
                                  @RequestParam(name = Constants.SERVICEINSTANCE_ID, required = false, defaultValue = "none") String serviceInstanceId) {

        if(sessionRefresh.equalsIgnoreCase(Constants.CHECK_TRUE)){
            LOGGER.info("[FOR THE SERVICE TYPE] CONNECT VIA DASHBOARD URI BUTTON TO REFRESH SESSION...");
            SecurityContextHolder.clearContext();
            return new RedirectView("/?serviceInstanceId="+ serviceInstanceId);
        }

        return new RedirectView(Constants.URI_INTRO_OVERVIEW);
    }

    /**
     * Intro overview 페이지 이동(Move Intro overview page)
     *
     * @param httpServletRequest the http servlet request
     * @return the intro overview
     */
    @ApiOperation(value = "Intro overview 페이지 이동(Move Intro overview page)", nickname = "getIntroOverview")
    @GetMapping(value = Constants.URI_INTRO_OVERVIEW)
    public ModelAndView getIntroOverview(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/overview", new ModelAndView());
    }

}

