package org.paasta.container.platform.web.user.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.paasta.container.platform.web.user.login.LoginService;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.paasta.container.platform.web.user.security.DashboardAuthenticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Aspect Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.25
 */
@Aspect
@Service
public class AspectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectService.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginService loginService;

    @Value("${access.cp-token}")
    private String cpToken;

    /**
     * On before log service access
     *
     * @param joinPoint the join point
     */
    @Before("execution(* org.paasta.container.platform..*Service.*(..))")
    public void onBeforeLogServiceAccess(JoinPoint joinPoint) {
        LOGGER.warn("######## ON BEFORE SERVICE ACCESS :: {}", CommonUtils.loggerReplace(joinPoint.toString()));
    }


    /**
     * On before log controller access
     *
     * @param joinPoint the join point
     */
    @Before("execution(* org.paasta.container.platform..*Controller.*(..))")
    public void onBeforeLogControllerAccess(JoinPoint joinPoint) {
        LOGGER.warn("#### DASHBOARD :: ON BEFORE CONTROLLER ACCESS :: {}", CommonUtils.loggerReplace(joinPoint));
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        RequestWrapper requestWrapper = new RequestWrapper(request);

        LOGGER.warn("## Entering in Method:  {}", CommonUtils.loggerReplace(joinPoint.getSignature().getName()));
        LOGGER.warn("## Class Name:  {}", CommonUtils.loggerReplace(joinPoint.getSignature().getDeclaringTypeName()));
        LOGGER.warn("## Arguments:  {}",  CommonUtils.loggerReplace(Stream.of(joinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(", "))));
        LOGGER.warn("## Target class:  {}", joinPoint.getTarget().getClass().getName());

        if (null != request) {
            LOGGER.warn("## Request Path info:  {}", CommonUtils.loggerReplace(request.getServletPath()));
            LOGGER.warn("## Method Type:  {}", request.getMethod());
            LOGGER.warn("================================================================================");
            LOGGER.warn("Start Header Section of request");
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                String headerValue = requestWrapper.getHeader(headerName);
                LOGGER.warn("  Header Name: {} || Header Value: {}", CommonUtils.loggerReplace(headerName), CommonUtils.loggerReplace(headerValue));
            }
            LOGGER.warn("End Header Section of request");
            LOGGER.warn("================================================================================");
        }
    }



/**
     * 활성화된 사용자인지 판별 (Check if user account is active)
     *
     * @param joinPoint
     * @return the object
     * @throws Throwable
     */
    @Around("execution(* org.paasta.container.platform..*Controller.*(..))" + "&& !@annotation(org.paasta.container.platform.web.user.config.NoAuth)")
    public Object inactiveUserAccessCheckAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        ModelAndView model = new ModelAndView();
        String inactiveUserRedirectView = Constants.REDIRECT_VIEW + Constants.URI_INACTIVE_USER_ACCESS;

        UsersLoginMetaData usersLoginMetaData = null;

        try {
            DashboardAuthenticationDetails authUserDetails = ((DashboardAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails());
            usersLoginMetaData = authUserDetails.getUsersLoginMetaData();

            if(usersLoginMetaData.getActive().equals(Constants.CHECK_N)) {
                LOGGER.warn("================================================================================");
                LOGGER.warn("## REDIRECT INACTIVE VIEW :: THE USER [" + CommonUtils.loggerReplace(authUserDetails.getUserid())+ "] IS INACTIVE");
                LOGGER.warn("================================================================================");

                model.setViewName(inactiveUserRedirectView);
                return model;
            }

        }
        catch(Exception e) {
            model.setViewName(Constants.REDIRECT_VIEW + "/error/403");
            return model;
        }

        return joinPoint.proceed();
    }



}
