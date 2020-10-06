package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
     * 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_USERS_NAME_LIST)
    public Map<String, List> getUsersNameList() {
        return usersService.getUsersNameList();
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

}
