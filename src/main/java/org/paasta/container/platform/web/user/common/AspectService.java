package org.paasta.container.platform.web.user.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.paasta.container.platform.web.user.login.LoginService;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
     * Controller 호출 시 Access Token 존재 유무 판별 (Check the presence or absence of an access token on before controller access)
     *
     * Redis 내 해당 사용자 Access Token 미존재인 경우 unauthorized 페이지로 이동
     *
     * @param joinPoint
     * @return the object
     * @throws Throwable
     */
    @Around("execution(* org.paasta.container.platform..*Controller.*(..))" + "&& !@annotation(org.paasta.container.platform.web.user.config.NoAuth)")
    public Object accessTokenCheckAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        ModelAndView model = new ModelAndView();
        String accessToken = null;
        UsersLoginMetaData usersLoginMetaData = new UsersLoginMetaData();
        String tokenExpiredRedirectView = Constants.REDIRECT_VIEW + Constants.URI_LOGOUT + "?timeout=" + Constants.CHECK_TRUE;

        try {

            usersLoginMetaData = loginService.getAuthenticationUserMetaData();
            accessToken = usersLoginMetaData.getAccessToken();
        }
        catch(NullPointerException e) {
            model.setViewName(tokenExpiredRedirectView);
            return model;
        }


        if (accessToken.isEmpty() || accessToken == null) {
            LOGGER.warn("================================================================================");
            LOGGER.warn("## Move to UNAUTHORIZED VIEW :: Access Token does not exist");
            LOGGER.warn("================================================================================");
            model.setViewName(tokenExpiredRedirectView);
            return model;
        }


        return joinPoint.proceed();
    }


}
