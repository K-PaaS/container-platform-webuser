package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.22
 **/
@Service
public class AdminService {

    private final RestTemplateService restTemplateService;

    @Autowired
    public AdminService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public Users registerAdmin(Users users) {
        return null;
    }
}
