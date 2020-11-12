package org.paasta.container.platform.web.user.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;

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

    @Value("${access.cp-token}")
    private String cpToken;

    /**
     * On before log service access
     *
     * @param joinPoint the join point
     */
    @Before("execution(* org.paasta.container.platform..*Service.*(..))")
    public void onBeforeLogServiceAccess(JoinPoint joinPoint) {
        LOGGER.warn("######## ON BEFORE SERVICE ACCESS :: {}", joinPoint);
    }


    /**
     * On before log controller access
     *
     * @param joinPoint the join point
     */
    @Before("execution(* org.paasta.container.platform..*Controller.*(..))")
    public void onBeforeLogControllerAccess(JoinPoint joinPoint) {
        LOGGER.warn("#### DASHBOARD :: ON BEFORE CONTROLLER ACCESS :: {}", joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        LOGGER.warn("## Entering in Method:  {}", joinPoint.getSignature().getName());
        LOGGER.warn("## Class Name:  {}", joinPoint.getSignature().getDeclaringTypeName());
        LOGGER.warn("## Arguments:  {}", Arrays.toString(joinPoint.getArgs()));
        LOGGER.warn("## Target class:  {}", joinPoint.getTarget().getClass().getName());

        if (null != request) {
            LOGGER.warn("## Request Path info:  {}", request.getServletPath());
            LOGGER.warn("## Method Type:  {}", request.getMethod());
            LOGGER.warn("================================================================================");
            LOGGER.warn("Start Header Section of request");
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                LOGGER.warn("  Header Name: {} || Header Value: {}", headerName, headerValue);
            }
            LOGGER.warn("End Header Section of request");
            LOGGER.warn("================================================================================");
        }
    }


    /**
     * Controller 호출 시 Acess Token 존재 유무 판별
     * <p>
     * Cookies 내 Access Token 미존재인 경우 로그아웃 처리
     *
     * @param joinPoint
     * @return the object
     * @throws Throwable
     */
    @Around("execution(* org.paasta.container.platform..*Controller.*(..))" + "&& !@annotation(org.paasta.container.platform.web.user.config.NoAuth)")
    public Object accessTokenCheckAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        ModelAndView model = new ModelAndView();
        String accessToken = null;

        accessToken = CommonUtils.getCookie(request, cpToken);

        if (accessToken == null) {
            LOGGER.warn("================================================================================");
            LOGGER.warn("## Move to UNAUTHORIZED VIEW :: Access Token does not exist");
            LOGGER.warn("================================================================================");
            model.setViewName(Constants.REDIRECT_VIEW + Constants.URI_UNAUTHORIZED);
            return model;
        }

        return joinPoint.proceed();
    }


}
