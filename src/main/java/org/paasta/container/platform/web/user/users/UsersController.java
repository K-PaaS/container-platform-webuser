package org.paasta.container.platform.web.user.users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.CommonUtils;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Value("${access.cp-token}")
    private String cpToken;

    @Value("${access.cp-user-id}")
    private String cpUserId;

    @Autowired
    UsersValidator userValidator;

    @Autowired
    public UsersController(UsersService usersService, CommonService commonService) {
        this.usersService = usersService;
        this.commonService = commonService;
    }

    /**
     * Users 회원가입 페이지 이동(Move Users sing up page)
     *
     * @return the view
     */
    @ApiOperation(value = "Users 회원가입 페이지 이동(Move Users sing up page)", nickname = "signUpView")
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
     * (todo ::: Namespace 관리자만 접근 가능하도록)
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
     * @param namespace the namespace
     * @param users the users
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 권한 설정(Put Users authority setting)", nickname = "modifyUsersConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "users", value = "유저 정보 목록", required = true, dataType = "List<Users>", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_USERS_CONFIG)
    public ResultStatus modifyUsersConfig(@PathVariable(value = "namespace") String namespace,
                                          @RequestBody List<Users> users) {
        return usersService.modifyUsersConfig(namespace, users);
    }


    /**
     * 전체 Users 목록 조회(Get All Users list)
     *
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "전체 Users 목록 조회(Get All Users list)", nickname = "getUsersList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST)
    public UsersList getUsersList(@RequestParam(name = "namespace") String namespace) {
        return usersService.getUsersList(namespace);
    }

    /**
     * 각 Namespace 별 Users 목록 조회(Get Users namespaces list)
     *
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "각 Namespace 별 Users 목록 조회(Get Users namespaces list)", nickname = "getUsersListByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST_BY_NAMESPACE)
    public UsersList getUsersListByNamespace(@PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersListByNamespace(namespace);
    }


    /**
     * 각 Namespace 별 등록 Users 이름 목록 조회(Get Users name namespaces list)
     *
     * @param namespace the namespace
     * @return the users list
     */
    @ApiOperation(value = "각 Namespace 별 등록 Users 이름 목록 조회(Get Users name namespaces list)", nickname = "getUsersNameListByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_NAMES_LIST_BY_NAMESPACE)
    public Map<String, List> getUsersNameListByNamespace(@PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersNameListByNamespace(namespace);
    }


    /**
     * Namespace, User id를 통한 사용자 단건 조회(Get Users id namespaces detail)
     *
     * @param namespace the namespace
     * @param userId the user id
     * @return the users detail
     */
    @ApiOperation(value = "Namespace, User id를 통한 사용자 단건 조회(Get Users id namespaces detail)", nickname = "getUsersByNamespace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "유저 Id", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_DETAIL)
    public Users getUsersByNamespace(@PathVariable(value = "namespace") String namespace,
                                     @PathVariable(value = "userId") String userId) {
        return usersService.getUsers(namespace, userId);
    }


    /**
     * 로그인 페이지 이동(Move login page)
     *
     * @param request the request
     * @param response the response
     * @return the view
     */
    @ApiOperation(value = "로그인 페이지 이동(Move login page)", nickname = "loginView")
    @GetMapping("/login")
    public ModelAndView loginView(HttpServletRequest request,
                                  HttpServletResponse response) {

        String userToken = null;
        userToken = CommonUtils.getCookie(request, cpToken);

        ModelAndView model = new ModelAndView();
        model.setViewName(Constants.URI_LOGIN);

        if(userToken != null) {
            model.setViewName("redirect:"+ Constants.URI_INTRO_OVERVIEW);
        }

        return model;
    }


    /**
     * Users 로그인(Post Users login)
     *
     * @param users the users
     * @param request the request
     * @param response the response
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 로그인(Post Users login)", nickname = "loginUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PostMapping("/login")
    @ResponseBody
    public ResultStatus loginUser(@RequestBody Users users,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        userValidator.getUsersValidate(request, users);
        ResultStatus rs = usersService.loginUser(users);

        if (rs.getResultCode().equals(Constants.RESULT_STATUS_SUCCESS)) {
            CommonUtils.addCookies(response,cpToken, rs.getToken() );
            CommonUtils.addCookies(response,cpUserId, rs.getUserId() );
        }

        return rs;
    }


    /**
     * Users 로그아웃(Get Users logout)
     *
     * @param response the response
     * @return the view
     */
    @ApiOperation(value = "Users 로그아웃(Get Users logout)", nickname = "logout")
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response) {

        CommonUtils.removeCookies(response,cpToken);
        CommonUtils.removeCookies(response,cpUserId);
        CommonUtils.removeCookies(response,Constants.CP_USER_METADATA_KEY);
        CommonUtils.removeCookies(response,Constants.CP_SELECTED_NAMESPACE_KEY);

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/login");
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
        String userId = CommonUtils.getCookie(httpServletRequest, cpUserId);
        String selectedNs = CommonUtils.getCookie(httpServletRequest, Constants.CP_SELECTED_NAMESPACE_KEY);

        Users user = usersService.getUsers(selectedNs, userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/info", mv);
    }


    /**
     * Users 정보 수정(Put Users info)
     *
     * @param userId the userId
     * @param users the users
     * @return the resultStatus
     */
    @ApiOperation(value = "Users 정보 수정(Put Users info)", nickname = "updateUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 Id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "users", value = "유저 정보", required = true, dataType = "Users", paramType = "body")
    })
    @PutMapping(value = Constants.API_URL + Constants.URI_API_USERS_INFO)
    public ResultStatus updateUsers(@PathVariable(value = "userId") String userId,
                                    @RequestBody Users users) {
        return usersService.updateUsers(userId, users);
    }
}
