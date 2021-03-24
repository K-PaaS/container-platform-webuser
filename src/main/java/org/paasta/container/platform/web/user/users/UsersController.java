package org.paasta.container.platform.web.user.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.MessageConstant;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.paasta.container.platform.web.user.config.NoAuth;
import org.paasta.container.platform.web.user.login.LoginService;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

    @Value("${access.cp-token}")
    private String cpToken;

    @Value("${access.cp-user-id}")
    private String cpUserId;

    @Autowired
    UsersValidator userValidator;

    @Autowired
    public UsersController(UsersService usersService, CommonService commonService, LoginService loginService) {
        this.usersService = usersService;
        this.commonService = commonService;
        this.loginService = loginService;
    }

    /**
     * Users 회원가입 페이지 이동(Move Users sing up page)
     *
     * @return the view
     */
    @ApiOperation(value = "Users 회원가입 페이지 이동(Move Users sing up page)", nickname = "signUpView")
    @NoAuth
    @GetMapping(value = "/signUp")
    public ModelAndView signUpView() {
        ModelAndView model = new ModelAndView();

        model.setViewName("/signUp/signUp");
        return model;
    }


    /**
     * Users 회원가입(Put Users sign up)
     *
     * @param users
     * @param bindingResult
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 회원가입(Put Users sign up)", nickname = "registerUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @NoAuth
    @PostMapping(value = "/register")
    @ResponseBody
    public ResultStatus registerUser(@Valid @RequestBody Users users,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errFieldList = new ArrayList<>();

            for (FieldError err : bindingResult.getFieldErrors()) {
                errFieldList.add(err.getField());
            }

            return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_FAIL)
                    .detailMessage("회원가입에 실패했습니다. 다음" + errFieldList.toString() + "항목을 재 확인 바랍니다.").build();
        }

        return usersService.registerUser(users);
    }


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
                                     @PathVariable(value = "userId") String userId) {
        return usersService.getUsers(cluster, namespace, userId);
    }


    /**
     * 로그인 페이지 이동(Move login page)
     *
     * @return the view
     */
    @ApiOperation(value = "로그인 페이지 이동(Move login page)", nickname = "loginView")
    @NoAuth
    @GetMapping("/login")
    public ModelAndView loginView() {

        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();
        String userToken = null;
        ModelAndView model = new ModelAndView();
        model.setViewName(Constants.URI_LOGIN);

        try {
            userToken = usersLoginMetaData.getAccessToken();
        } catch (NullPointerException e) {
            return model;
        }


        if (userToken != null) {
            model.setViewName(Constants.REDIRECT_VIEW + Constants.URI_INTRO_OVERVIEW);
        }

        return model;
    }


    /**
     * Users 로그인(Post Users login)
     *
     * @param users   the users
     * @param request the request
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 로그인(Post Users login)", nickname = "loginUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PostMapping("/login")
    @NoAuth
    @ResponseBody
    public ResultStatus loginUser(@RequestBody Users users,
                                  HttpServletRequest request) {

        userValidator.getUsersValidate(request, users);
        ResultStatus rs = usersService.loginUser(users);

        if (rs.getResultCode().equals(Constants.RESULT_STATUS_SUCCESS)) {
            loginService.userAuthenticationHandler(users, rs);
        }

        return rs;
    }


    /**
     * Users 로그아웃(Get Users logout)
     *
     * @param session the HttpSession
     * @return the view
     */
    @ApiOperation(value = "Users 로그아웃(Get Users logout)", nickname = "logout")
    @NoAuth
    @GetMapping(value = Constants.URI_LOGOUT)
    public ModelAndView logout(HttpSession session,
                               @RequestParam(required = false, defaultValue = Constants.CHECK_FALSE) String timeout) {

        loginService.userLogoutHandler(session);

        ModelAndView model = new ModelAndView();

        //토큰 만료로 인한 자동 로그아웃의 경우 처리
        if(timeout.toLowerCase().equals(Constants.CHECK_TRUE)) {
            model.setViewName(Constants.REDIRECT_VIEW + "/common/error/unauthorized");
            return model;
        }
        model.setViewName(Constants.REDIRECT_VIEW + "/login");
        return model;
    }

    /**
     * Users 마이 페이지로 이동(Move Users my page)
     *
     * @param httpServletRequest the httpServletRequest
     * @return the view
     */
    @ApiOperation(value = "Users 마이 페이지로 이동(Move Users my page)", nickname = "getUserInfoMain")
    @GetMapping(value = Constants.URI_USERS_INFO)
    public ModelAndView getUserInfoMain(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {

        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();

        String userId = usersLoginMetaData.getUserId();
        String selectedNs = usersLoginMetaData.getSelectedNamespace();
        String cluster = usersLoginMetaData.getClusterName();

        Users user = usersService.getUsers(cluster, selectedNs, userId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);

        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/info", mv);
    }


    /**
     * Users 정보 수정(Put Users info)
     *
     * @param cluster the cluster
     * @param userId  the userId
     * @param users   the users
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 정보 수정(Put Users info)", nickname = "updateUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "유저 Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_USERS_INFO)
    public ResultStatus updateUsers(@PathVariable(value = "cluster") String cluster,
                                    @PathVariable(value = "userId") String userId,
                                    @RequestBody Users users) {

        ResultStatus resultStatus = usersService.updateUsers(cluster, userId, users);

        if (resultStatus.getResultCode().equals(Constants.RESULT_STATUS_SUCCESS)) {
            loginService.updateAuthenticationUser(users);
        }

        return resultStatus;
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

        UsersLoginMetaData usersLoginMetaDataNew = loginService.updateUsersLoginMetaData(usersLoginMetaData);

        if (usersLoginMetaDataNew.getSelectedNamespace().isEmpty() || usersLoginMetaDataNew.getSelectedNamespace() == null) {
            return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_FAIL).detailMessage(MessageConstant.NAMESPACE_CHANGE_FAILED).build();
        }

        return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_SUCCESS)
                .detailMessage(MessageConstant.NAMESPACE_CHANGE_SUCCEEDED).userId(usersLoginMetaDataNew.getUserId()).build();

    }


    /**
     * Users 비밀번호 검증 (Users password verification)
     *
     * @param users the users
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 비밀번호 검증 (Users password verification)", nickname = "verifyPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PostMapping(value = Constants.URI_USERS_VERIFY_PASSWORD)
    @ResponseBody
    public ResultStatus verifyPassword(@RequestBody Users users) {

        String credentials = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        if (users.getPassword().equals(credentials)) {
            return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_SUCCESS).build();
        }
        return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_FAIL).detailMessage(MessageConstant.INVALID_PASSWORD).build();
    }

}
