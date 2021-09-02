package org.paasta.container.platform.web.user.config;


import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.CustomIntercepterService;
import org.paasta.container.platform.web.user.login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomIntercepter extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomIntercepter.class);


    @Autowired
    CustomIntercepterService customIntercepterService;

    @Autowired
    LoginService loginService;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {


        String url = request.getRequestURI();

        if (!(  url.indexOf("/css") >= 0 ||
                url.indexOf("/images") >= 0 ||
                url.indexOf("/js") >= 0 ||
                url.indexOf("/webjars") >= 0 ||
                url.indexOf("/sessionout") >= 0 ||
                url.indexOf("/error") >= 0
        )) {

            boolean isActive = customIntercepterService.isActive();

 	        if(!isActive) {
	       	 request.getSession().invalidate();
	       	 LOGGER.info("#### USER SESSION OUT DUE TO EXPIRATION OF KEYCLOAK SESSION");

	       	 response.sendRedirect(Constants.URI_SESSION_OUT);
	       	 return false;
	        }
      }


        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        super.afterCompletion(request, response, handler, ex);
    }

}
