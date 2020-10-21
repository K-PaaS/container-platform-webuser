package org.paasta.container.platform.web.user.users;

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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @Value("${access.cp-namespace}")
    private String cpNamespace;


    @Autowired
    public UsersController(UsersService usersService, CommonService commonService) {
        this.usersService = usersService;
        this.commonService = commonService;
    }

    /**
     * 사용자 회원가입 페이지로 이동한다.
     *
     * @return
     */
    @GetMapping(value = "/signUp")
    public ModelAndView signUpView() {
        ModelAndView model = new ModelAndView();

        model.setViewName("/signUp/signUp");
        return model;
    }


    /**
     * 사용자 회원가입
     *
     * @param users
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "사용자 회원가입", httpMethod = "POST", hidden = true)
    @PostMapping(value = "/register")
    @ResponseBody
    public ResultStatus registerUser(@Valid @RequestBody Users users, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errFieldList = new ArrayList<>();

            for (FieldError err : bindingResult.getFieldErrors()) {
                errFieldList.add(err.getField());
            }

            return ResultStatus.builder().resultCode(Constants.RESULT_STATUS_FAIL)
                    .detailMessage("Failed Sign Up. Re Confirm " + errFieldList.toString()).build();
        }

        return usersService.registerUser(users);
    }


    /**
     * 사용자 목록 페이지로 이동한다.
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = Constants.URI_USERS)
    public ModelAndView getUserMain(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView();
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/main", mv);
    }

    /**
     * 사용자 설정 페이지로 이동한다. (todo ::: Namespace 관리자만 접근 가능하도록)
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = Constants.URI_USERS_CONFIG)
    public ModelAndView getUserDetail(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView();
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/config", mv);
    }


    /**
     * 전체 사용자 목록을 조회한다.
     *
     * @return
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST)
    public UsersList getUsersList() {
        return usersService.getUsersList();
    }

    /**
     * 각 namespace별 사용자 목록을 조회한다.
     *
     * @param namespace
     * @return
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST_BY_NAMESPACE)
    public UsersList getUsersListByNamespace(@PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersListByNamespace(namespace);
    }


    /**
     * 각 namespace별 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_NAMES_LIST_BY_NAMESPACE)
    public Map<String, List> getUsersNameListByNamespace(@PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersNameListByNamespace(namespace);
    }


    /**
     * 로그인 페이지 이동
     *
     * @return the view
     */
    @GetMapping("/login")
    public ModelAndView loginView(HttpServletResponse response) {

        CommonUtils.removeCookies(response,cpToken);
        CommonUtils.removeCookies(response,cpUserId);
        CommonUtils.removeCookies(response,cpNamespace);

        ModelAndView model = new ModelAndView();
        model.setViewName("/signUp/login");
        return model;
    }


    /**
     * 사용자 로그인
     *
     * @return the ResultStatus
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultStatus loginUser(@RequestBody Users users, HttpServletRequest request, HttpServletResponse response) {

        ResultStatus rs = usersService.loginUser(users);

        if (rs.getResultCode().equals(Constants.RESULT_STATUS_SUCCESS)) {
            CommonUtils.addCookies(response,cpToken, rs.getToken() );
            CommonUtils.addCookies(response,cpUserId, rs.getUserId() );
        }

        return rs;
    }


    /**
     * 사용자 로그아웃
     *
     * @return the view
     */
    @GetMapping("/logout")
    public RedirectView logoutUser(HttpServletRequest request, HttpServletResponse response) {

        CommonUtils.removeCookies(response,cpToken);
        CommonUtils.removeCookies(response,cpUserId);
        CommonUtils.removeCookies(response,cpNamespace);
        return new RedirectView("/login");

    }


    /**
     * 로그인 페이지 이동
     *
     * @return the view
     */
    @GetMapping("/")
    public RedirectView indexView() {
        return new RedirectView("/login");
    }


    /**
     * 사용자 마이 페이지로 이동한다.
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = Constants.URI_USERS_INFO)
    public ModelAndView getUserInfoMain(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        String userId = CommonUtils.getCookie(httpServletRequest, cpUserId);
        String nsListString = CommonUtils.getCookie(httpServletRequest, cpNamespace);
        String decodeNsListStr = URLDecoder.decode(nsListString, "euc-kr");
        String[] nsList = CommonUtils.stringReplace(decodeNsListStr).split(",");

        Users user = usersService.getUsers(nsList[0], userId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        return commonService.setPathVariables(httpServletRequest, BASE_URL + "/info", mv);
    }


    /**
     * 사용자 정보를 수정한다.
     *
     * @param userId  the userId
     * @param users   the users
     * @return        the ResultStatus
     */
    @PutMapping(value = Constants.API_URL + Constants.URI_API_USERS_INFO)
    public ResultStatus updateUsers(@PathVariable(value = "userId") String userId, @RequestBody Users users) {
        return usersService.updateUsers(userId, users);
    }
}
