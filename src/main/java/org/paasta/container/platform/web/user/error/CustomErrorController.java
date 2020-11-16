package org.paasta.container.platform.web.user.error;

import org.paasta.container.platform.web.user.config.NoAuth;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String reqUrl = VIEW_URL + status.toString();

        ModelAndView mv = new ModelAndView();
        mv.setViewName(reqUrl);

        return mv;
    }
}