package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.paasta.container.platform.web.user.common.Constants.TARGET_CP_API;

/**
 * User Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 **/
@Service
public class UsersService {

    private final RestTemplateService restTemplateService;

    @Autowired
    public UsersService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public Users registerUser(Users users) {
        return restTemplateService.send(TARGET_CP_API, "/users", HttpMethod.POST, users, Users.class);
    }

    public Users registerAdminUser(Users users) {
        return null;
    }

    public UsersList getUsersList() {
        return restTemplateService.send(TARGET_CP_API, "/users", HttpMethod.GET, null, UsersList.class);
    }

    public List<String> getUsersNameList() {
        return restTemplateService.send(TARGET_CP_API, "/users", HttpMethod.GET, null, List.class);
    }
}
