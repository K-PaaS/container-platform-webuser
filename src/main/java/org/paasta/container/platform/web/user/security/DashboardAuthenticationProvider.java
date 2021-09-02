package org.paasta.container.platform.web.user.security;

import org.paasta.container.platform.web.user.common.CommonUtils;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.PropertyService;
import org.paasta.container.platform.web.user.common.RequestWrapper;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.paasta.container.platform.web.user.login.LoginService;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.paasta.container.platform.web.user.users.Users;
import org.paasta.container.platform.web.user.users.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * {@link AuthenticationProvider} used to make the link between an OAuth user
 * and an internal User.
 *
 * @author Sebastien Gerard
 */
public class DashboardAuthenticationProvider implements AuthenticationProvider {


    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardAuthenticationProvider.class);
    private final UsersService usersService;
    private final LoginService loginService;
    private final PropertyService propertyService;


    @Autowired
    private HttpServletRequest request;

    public DashboardAuthenticationProvider(UsersService usersService,LoginService loginService, PropertyService propertyService)
    {
        this.usersService = usersService;
        this.loginService = loginService;
        this.propertyService = propertyService;
    }



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final Object details = authentication.getDetails();


        if (!(details instanceof DashboardAuthenticationDetails)) {
            throw new InternalAuthenticationServiceException("The authentication details [" + details
                    + "] are not an instance of " + DashboardAuthenticationDetails.class.getSimpleName());
        }

        DashboardAuthenticationDetails dashboardAuthenticationDetails = (DashboardAuthenticationDetails) details;
        RequestWrapper requestWrapper = new RequestWrapper(request);

        final String userId=dashboardAuthenticationDetails.getUserid();
        final String userAuthId =dashboardAuthenticationDetails.getId();
        final String serviceInstanceId = requestWrapper.getParameter(Constants.SERVICEINSTANCE_ID);


        LOGGER.info("###############################################################");
        LOGGER.info(CommonUtils.loggerReplace("SESSION INFOMATION SETTING [" + name + "]" + " [" + userId + ","+userAuthId+"]" + " [" + authentication.getPrincipal() + "] "));
        LOGGER.info("###############################################################");


        // 1. 사용자 계정 생성(KEYCLOAK, CP USER 에 해당 ID, AUTH_ID를 가진 사용자 계정이 이미 등록되어있다면 생성 건너띔, 없다면 생성 진행)
        try {
            LOGGER.info("###############################################################");
            LOGGER.info(CommonUtils.loggerReplace("[REGISTRATION] CREATE USER  [" + userId + ","+userAuthId+"]"));
            LOGGER.info("###############################################################");

            // 사용자 계정 생성
            Users newUser = new Users();
            newUser.setUserId(userId);
            newUser.setUserAuthId(userAuthId);
            newUser.setServiceInstanceId(serviceInstanceId);
            newUser.setCpProviderType(propertyService.getCpProviderType());

            ResultStatus resultStatus =  usersService.registerUser(newUser);

            if(resultStatus.getResultCode().equals(Constants.RESULT_STATUS_FAIL)) {

                if(resultStatus.getResultMessage().equals(Constants.INVALID_SERVICE_INSTANCE_ID)) {
                    LOGGER.info("*****[PROVIDER AS SERVICE] SERVICE INSTANCE ID IS NOT VALID.");
                    throw new InternalAuthenticationServiceException(Constants.INVALID_SERVICE_INSTANCE_ID);
                }
                else if(resultStatus.getResultMessage().equals(Constants.USER_NOT_REGISTERED_IN_KEYCLOAK)) {
                    LOGGER.info("***** THIS ACCOUNT IS NOT REGISTERED WITH KEYCLOAK.");
                    throw new InternalAuthenticationServiceException(Constants.USER_NOT_REGISTERED_IN_KEYCLOAK);
                }
                else if(resultStatus.getResultMessage().equals(Constants.LOGIN_USER_ALREADY_REGISTERED)) {
                    // 이미 등록된 사용자 계정이므로 생성 건너띔
                    LOGGER.info(CommonUtils.loggerReplace("***** [DENY REGISTRATION] USER ACCOUNT ["+ userId +"] ALREADY EXISTS, THE USER ["+ userId +"] REGISTRATION IS DENIED."));
                }
                else {
                    LOGGER.info("***** EXCEPTION OCCURRED DURING USER CREATED...LOOK AT THE LOGS ON THE CP API, COMMON API.");
                    throw new InternalAuthenticationServiceException("EXCEPTION OCCURRED DURING USER CREATED.");
                }
            }

        }
        catch(Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage());
        }


        // 2. CP API 사용자 로그인 및 사용자 메타데이터 받아오기

        try {
            Users loginUser = new Users();
            loginUser.setUserId(userId);
            loginUser.setUserAuthId(userAuthId);

            ResultStatus resultStatus = usersService.loginUser(loginUser);

            if(resultStatus.getResultCode().equals(Constants.RESULT_STATUS_SUCCESS)){
                LOGGER.info("###############################################################");
                LOGGER.info("CP API LOGIN SUCCESSFUL ");
                LOGGER.info("###############################################################");
                UsersLoginMetaData usersLoginMetaData = loginService.setAuthDetailsLoginMetaData(resultStatus);
                usersLoginMetaData.setServiceInstanceId(serviceInstanceId);
                dashboardAuthenticationDetails.setUsersLoginMetaData(usersLoginMetaData);
            }
            else {
                if(resultStatus.getResultMessage().equals(Constants.LOGIN_INACTIVE_USER)) {
                    // 비활성화 사용자인 경우 (네임스페이스 및 롤을 할당받지 못한 사용자)
                    LOGGER.info("###############################################################");
                    LOGGER.info("[INACTIVE USER ACCESS] USER WITH NO NAMESPACE AND ROLE ASSIGNED");
                    LOGGER.info("###############################################################");
                    UsersLoginMetaData usersLoginMetaData = new UsersLoginMetaData();
                    usersLoginMetaData.setActive(Constants.CHECK_N);
                    dashboardAuthenticationDetails.setUsersLoginMetaData(usersLoginMetaData);
                }
                else if(resultStatus.getResultMessage().equals(Constants.LOGIN_FAIL)) {
                    // 인증 실패 사용자
                    LOGGER.info(CommonUtils.loggerReplace("***** [UNAUTHORIZED] THE USER ["+ userId +"] NO PERMISSIONS DURING THE AUTHENTICATION CHECK OF ID AND AUTH ID(KEYCLOAK ID)"));
                    throw new InternalAuthenticationServiceException(Constants.LOGIN_FAIL);
                }
                else {
                    LOGGER.info("EXCEPTION OCCURRED DURING CP API LOGIN...LOOK AT THE LOGS ON THE CP API, COMMON API.");
                    throw new InternalAuthenticationServiceException("EXCEPTION OCCURRED DURING CP API LOGIN");
                }
            }

        }
        catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }

        try {

            List authorities = new ArrayList();

            //이상없으면 세션
            authentication = new OAuth2Authentication(((OAuth2Authentication) authentication).getOAuth2Request(), new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "N/A", authorities));
            ((OAuth2Authentication) authentication).setDetails(dashboardAuthenticationDetails);

        } catch (Exception e) {
            e.printStackTrace();
            // 세션 초기화
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            throw new InternalAuthenticationServiceException("Permission Error on [" + name + "]", e);
        }


        return authentication;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2Authentication.class.isAssignableFrom(authentication);
    }


}

