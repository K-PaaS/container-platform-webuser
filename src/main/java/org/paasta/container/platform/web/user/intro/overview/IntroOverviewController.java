package org.paasta.container.platform.web.user.intro.overview;

import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@Controller
public class IntroOverviewController {

    private static final String VIEW_URL = "/intro";
    private final CommonService commonService;

    @Value("${roleSet.administratorCode}")
    private String administratorCode;


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
     * Intro overview 페이지로 이동한다.
     *
     * @return the view
     */
    @GetMapping("/")
    public RedirectView indexView() {
        return new RedirectView("/login");
    }

    /**
     * Intro overview 페이지 이동(Move Intro overview page)
     *
     * @param httpServletRequest the http servlet request
     * @return the intro overview
     */
    @GetMapping(value = Constants.URI_INTRO_OVERVIEW)
    public ModelAndView getIntroOverview(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/overview", new ModelAndView());
    }

}

