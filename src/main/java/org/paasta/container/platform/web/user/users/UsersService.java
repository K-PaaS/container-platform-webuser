package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.paasta.container.platform.web.user.common.Constants.*;

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

    /**
     * Users 회원가입(Post Users sign up)
     *
     * @param users the users
     * @return the ResultStatus
     */
    public ResultStatus registerUser(Users users) {
        return restTemplateService.send(TARGET_CP_API, "/signUp", HttpMethod.POST, users, ResultStatus.class);
    }


    /**
     * 각 Namespace 별 사용자 목록 조회(Get Users namespaces list)
     *
     * @param namespace the namespace
     * @return the UsersList
     */
    public UsersList getUsersListByNamespace(String namespace) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_LIST_BY_NAMESPACE.replace("{namespace:.+}", namespace), HttpMethod.GET, null, UsersList.class);
    }



    /**
     * 등록 Users 이름 목록 조회(Get Users name list)
     *
     * @return the Map
     */
    public Map<String, List> getUsersNameListByNamespace(String namespace) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_NAMES_LIST_BY_NAMESPACE.replace("{namespace:.+}", namespace), HttpMethod.GET, null, Map.class);
    }

    /**
     * Users 로그인(Post Users login)
     *
     * @return the ResultStatus
     */
    public ResultStatus loginUser(Users users) {
        return restTemplateService.send(TARGET_CP_API, Constants.URL_API_LOGIN, HttpMethod.POST, users, ResultStatus.class);
    }


    /**
     * Namespace, User id를 통한 사용자 단건 조회(Get Users id namespaces detail)
     *
     * @param namespace the namespace
     * @param userId the user id
     * @return the Users
     */
    public Users getUsers(String namespace, String userId) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_DETAIL.replace("{namespace:.+}", namespace).replace("{userId:.+}", userId), HttpMethod.GET, null, Users.class);
    }


    /**
     * 전체 사용자 목록을 조회한다.
     *
     * @return the UsersList
     */
    public UsersList getUsersList(String namespace) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_LIST + "?namespace=" + namespace, HttpMethod.GET, null, UsersList.class);
    }


    /**
     * Users 정보 수정(Put Users info)
     *
     * @param userId the userId
     * @param users the users
     * @return the ResultStatus
     */
    public ResultStatus updateUsers(String userId, Users users) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_INFO.replace("{userId:.+}", userId), HttpMethod.PUT, users, ResultStatus.class);
    }


    /**
     * Users 권한 설정(Put Users authority setting)
     *
     * @param namespace the namespace
     * @param users the users
     * @return the resultStatus
     */
    public ResultStatus modifyUsersConfig(String namespace, List<Users> users) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_CONFIG.replace("{namespace:.+}", namespace), HttpMethod.PUT, users, ResultStatus.class);
    }
}
