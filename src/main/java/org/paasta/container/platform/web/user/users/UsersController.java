package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * 사용자 회원가입 페이지로 이동한다.
     *
     * @return
     */
    @GetMapping("/signUp")
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
    @PostMapping("/register")
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


//    @GetMapping("/container-platform/users")
//    public UsersList getUsersList() {
//        return usersService.getUsersList();
//    }


    /**
     * 등록돼있는 사용자들의 이름 목록 조회
     *
     * @return the Map
     */
    @GetMapping("/container-platform/users")
    public Map<String, List> getUsersList() {
        return usersService.getUsersNameList();
    }

}
