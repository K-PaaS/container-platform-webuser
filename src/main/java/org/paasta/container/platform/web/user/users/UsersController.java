package org.paasta.container.platform.web.user.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * User Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 **/
@RestController
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signUp")
    public ModelAndView signUpView() {
        ModelAndView model = new ModelAndView();

        model.setViewName("/signUp/signUp");
        return model;
    }


    // 사용자 회원가입
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody Users users) {
        usersService.registerUser(users);
        return "redirect:/container-platform/intro/overview";
    }



    // 운영자 회원가입
    @PostMapping("/admin/register")
    public Users registerAdminUser(@Valid @RequestBody Users users) {
        return usersService.registerAdminUser(users);
    }

//    @GetMapping("/container-platform/users")
//    public UsersList getUsersList() {
//        return usersService.getUsersList();
//    }

    @GetMapping("/container-platform/users")
    public List<String> getUsersList() {
        return usersService.getUsersNameList();
    }

}
