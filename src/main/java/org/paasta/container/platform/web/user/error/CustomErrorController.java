package org.paasta.container.platform.web.user.error;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.config.NoAuth;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Error Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.11.13
 **/
@Controller
public class CustomErrorController implements ErrorController {

    private static final String VIEW_URL = "/errors/";

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @NoAuth
    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String reqUrl = VIEW_URL + status.toString();

        ModelAndView mv = new ModelAndView();
        mv.setViewName(reqUrl);

        return mv;
    }


    @NoAuth
    @GetMapping(Constants.URI_SESSION_OUT)
    public ModelAndView sessionout() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(VIEW_URL + "sessionout");

        return mv;
    }


    @NoAuth
    @GetMapping(Constants.URI_INACTIVE_USER_ACCESS)
    public ModelAndView inactiveUser() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(VIEW_URL + "inactive");
        return mv;
    }

    @NoAuth
    @GetMapping(Constants.URI_INACTIVE_USER_ACCESS + Constants.URI_SESSION_OUT)
    public RedirectView inactiveUserSessionOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return new RedirectView("/");
    }


    @NoAuth
    @GetMapping("/error/500")
    public ModelAndView handle500Error() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName(VIEW_URL + "500");
        return mv;
    }

    @NoAuth
    @GetMapping("/error/403")
    public ModelAndView handle403Error() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName(VIEW_URL + "403");
        return mv;
    }


    @NoAuth
    @GetMapping("/error/404")
    public ModelAndView handle400Error() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName(VIEW_URL + "404");
        return mv;
    }

    @NoAuth
    @GetMapping("/error/401")
    public ModelAndView handle401Error() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName(VIEW_URL + "401");
        return mv;
    }
}