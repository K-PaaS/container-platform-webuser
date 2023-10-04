package org.container.platform.web.user.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.container.platform.web.user.common.CommonService;
import org.container.platform.web.user.common.Constants;
import org.container.platform.web.user.common.CustomIntercepterService;
import org.container.platform.web.user.common.MessageConstant;
import org.container.platform.web.user.common.model.ResultStatus;
import org.container.platform.web.user.login.LoginService;
import org.container.platform.web.user.login.model.UsersLoginMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * User Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 **/
@Api(value = "UsersController v1")
@RestController
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    private static final String BASE_URL = "/managements/users";
    private final UsersService usersService;
    private final CommonService commonService;
    private final LoginService loginService;
    private final CustomIntercepterService customIntercepterService;


    @Autowired
    public UsersController(UsersService usersService, CommonService commonService, LoginService loginService, CustomIntercepterService customIntercepterService) {
        this.usersService = usersService;
        this.commonService = commonService;
        this.loginService = loginService;
        this.customIntercepterService = customIntercepterService;
    }

    @Autowired
    LocaleResolver localeResolver;

    /**
     * Users 목록 페이지 이동(Move Users list page)
     *
     * @param httpServletRequest
     * @return the view
     */
    @ApiOperation(value = "Users 목록 페이지 이동(Move Users list page)", nickname = "getUserMain")
    @GetMapping(value = Constants.URI_USERS)
    public ModelAndView getUserMain(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView();
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/main", mv);
    }

    /**
     * Users 설정 페이지 이동(Move Users setting page)
     *
     * @param httpServletRequest
     * @return the view
     */
    @ApiOperation(value = "Users 설정 페이지 이동(Move Users setting page)", nickname = "getUserDetail")
    @GetMapping(value = Constants.URI_USERS_CONFIG)
    public ModelAndView getUserDetail(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView();
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/config", mv);
    }


    /**
     * Users 권한 설정(Put Users authority setting)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param users     the users
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 권한 설정(Put Users authority setting)", nickname = "modifyUsersConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "users", value = "유저 정보 목록", required = true, dataType = "List<Users>", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_USERS_CONFIG)
    public ResultStatus modifyUsersConfig(@PathVariable(value = "cluster") String cluster,
                                          @PathVariable(value = "namespace") String namespace,
                                          @RequestBody List<Users> users) {

        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();
        String userId = usersLoginMetaData.getUserId();

        return usersService.modifyUsersConfig(cluster, namespace, users, userId);
    }


    /**
     * 전체 Users 목록 조회(Get All Users list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "전체 Users 목록 조회(Get All Users list)", nickname = "getUsersList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST)
    public UsersList getUsersList(@PathVariable(value = "cluster") String cluster,
                                  @RequestParam(name = "namespace") String namespace) {

        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();
        String userId = usersLoginMetaData.getUserId();

        return usersService.getUsersList(cluster, namespace, userId);
    }

    /**
     * 각 Namespace 별 Users 목록 조회(Get Users namespaces list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "각 Namespace 별 Users 목록 조회(Get Users namespaces list)", nickname = "getUsersListByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST_BY_NAMESPACE)
    public UsersList getUsersListByNamespace(@PathVariable(value = "cluster") String cluster,
                                             @PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersListByNamespace(cluster, namespace);
    }


    /**
     * 각 Namespace 별 등록 Users 이름 목록 조회(Get Users name namespaces list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "각 Namespace 별 등록 Users 이름 목록 조회(Get Users name namespaces list)", nickname = "getUsersNameListByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_NAMES_LIST_BY_NAMESPACE)
    public Map<String, List> getUsersNameListByNamespace(@PathVariable(value = "cluster") String cluster,
                                                         @PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersNameListByNamespace(cluster, namespace);
    }


    /**
     * Namespace, User id를 통한 사용자 단건 조회(Get Users id namespaces detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param userId    the user id
     * @return the users detail
     */
    @ApiOperation(value = "Namespace, User id를 통한 사용자 단건 조회(Get Users id namespaces detail)", nickname = "getUsersByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "유저 Id", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_DETAIL)
    public Users getUsersByNamespace(@PathVariable(value = "cluster") String cluster,
                                     @PathVariable(value = "namespace") String namespace,
                                     @RequestParam(required = false, defaultValue = "") String userId) {
        return usersService.getUsers(cluster, namespace, userId);
    }



    /**
     * Users 선택한 네임스페이스 변경 (Modify the Selected Namespace)
     *
     * @param namespace namespace
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 선택한 네임스페이스 변경 (Modify the Selected Namespace)", nickname = "UpdateSelectedNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.URI_UPDATE_SELECTED_NAMESPACE)
    @ResponseBody
    public ResultStatus UpdateSelectedNamespace(@PathVariable(value = "namespace") String namespace) {

        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();
        usersLoginMetaData.setSelectedNamespace(namespace);
        UsersLoginMetaData usersLoginMetaDataNew = loginService.updateAuthenticationUserMetaData(usersLoginMetaData);

        if (usersLoginMetaDataNew.getSelectedNamespace().isEmpty() || usersLoginMetaDataNew.getSelectedNamespace() == null) {
            return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_FAIL).detailMessage(MessageConstant.NAMESPACE_CHANGE_FAILED.getMsg()).build();
        }

        return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_SUCCESS)
                .detailMessage(MessageConstant.NAMESPACE_CHANGE_SUCCEEDED.getMsg()).userId(usersLoginMetaDataNew.getUserId()).build();

    }



    /**
     * User 로그아웃 (User Logout)
     *
     */
    @ApiOperation(value = "User 로그아웃 (User Logout)", nickname = "logoutUsers")
    @GetMapping(value = Constants.URI_LOGOUT)
    public void logoutUsers(HttpServletRequest request, HttpServletResponse response) {

        try {
            customIntercepterService.logout();
            request.getSession().invalidate();
            response.sendRedirect(Constants.URI_SESSION_OUT);
            return ;
        }
       catch (Exception e) {
       }
    }



    /**
     * Users 로그인 메타데이터 조회(Get User login metadata)
     *
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 로그인 메타데이터 조회(Get User login metadata)", nickname = "getUsersLoginMetadata")
    @GetMapping(value = Constants.API_URL + Constants.URI_USER_LOGIN_METADATA)
    public UsersLoginMetaData getUsersLoginMetadata() {
        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();
        return usersLoginMetaData;
    }


    /**
     * Locale 언어 변경 (Change Locale Language)
     */
    @ApiOperation(value = "Locale 언어 변경 (Change Locale Language)", nickname = "changeLocaleLang")
    @PutMapping(value = Constants.URL_API_LOCALE_LANGUAGE)
    public void changeLocaleLang(@RequestParam(required = false, name = Constants.URL_API_CHANGE_LOCALE_PARAM, defaultValue = Constants.LANG_EN) String language,
                                 HttpServletRequest request, HttpServletResponse response) {
        try {
            Locale locale = new Locale(language);
            localeResolver.setLocale(request, response, locale);
        } catch (Exception e) {
            LOGGER.info("EXCEPTION OCCURRED IN LOCALE LANGUAGE CHANGE..");
        }
    }


    /**
     * Locale 언어 조회 (Get Locale Language)
     */
    @ApiOperation(value = "Locale 언어 조회 (Get Locale Language)", nickname = "getLocaleLang")
    @GetMapping(value = Constants.URL_API_LOCALE_LANGUAGE)
    public String getLocaleLang() {
        try {
            Locale locale = LocaleContextHolder.getLocale();

            if (locale.toString().equalsIgnoreCase(Constants.LANG_KO)) {
                return Constants.LANG_KO;
            }

            if (locale.toString().toLowerCase().startsWith(Constants.LANG_KO_START_WITH)) {
                return Constants.LANG_KO;
            }

        } catch (Exception e) {
            return Constants.LANG_EN;
        }

        return Constants.LANG_EN;
    }
}
