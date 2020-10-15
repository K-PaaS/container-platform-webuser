package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.CommonService;
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
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @Value("${access.token-name}")
    private String tokenName;

    @Autowired
    public UsersController(UsersService usersService, CommonService commonService) {
        this.usersService = usersService;
        this.commonService = commonService;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
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
    @PostMapping(value = "/register")
    @ResponseBody
    public ResultStatus registerUser(@Valid @RequestBody Users users, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
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
     * 각 namespace별 사용자 목록을 조회한다.
     *
     * @param namespace
     * @return
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_LIST)
    public UsersList getUsersList(@PathVariable(value = "namespace") String namespace) {
        return usersService.getUsersList(namespace);
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

        //remove stored token
        CookieGenerator cookie = new CookieGenerator();

        cookie.setCookieName(tokenName);
        cookie.setCookieMaxAge(0);
        cookie.addCookie(response, null);


        cookie.setCookieName("namespace");
        cookie.setCookieMaxAge(0);
        cookie.addCookie(response, null);


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

            String token = rs.getToken();

            CookieGenerator cookie = new CookieGenerator();
            cookie.setCookieName(tokenName);
            cookie.setCookieMaxAge(60 * 60); // 1hours
            cookie.setCookieHttpOnly(true);
            cookie.addCookie(response, token);

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

        //remove stored token
        CookieGenerator cookie = new CookieGenerator();

        cookie.setCookieName(tokenName);
        cookie.setCookieMaxAge(0);
        cookie.addCookie(response, null);


        cookie.setCookieName("namespace");
        cookie.setCookieMaxAge(0);
        cookie.addCookie(response, null);

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



}
