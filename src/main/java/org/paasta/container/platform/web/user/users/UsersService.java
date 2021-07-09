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
     * Users 등록(Post Users sign up)
     *
     * @param users the users
     * @return the resultStatus
     */
    public ResultStatus registerUser(Users users) {
        return restTemplateService.send(TARGET_CP_API, "/signUp", HttpMethod.POST, users, ResultStatus.class);
    }


    /**
     * 각 Namespace 별 사용자 목록 조회(Get Users namespaces list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the user list
     */
    public UsersList getUsersListByNamespace(String cluster, String namespace) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_LIST_BY_NAMESPACE
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace), HttpMethod.GET, null, UsersList.class);
    }


    /**
     * 등록 Users 이름 목록 조회(Get Users name list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the users list
     */
    public Map<String, List> getUsersNameListByNamespace(String cluster, String namespace) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_NAMES_LIST_BY_NAMESPACE
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace), HttpMethod.GET, null, Map.class);
    }

    /**
     * Users 로그인(Post Users login)
     *
     * @param users the users
     * @return the resultStatus
     */
    public ResultStatus loginUser(Users users) {
        return restTemplateService.send(TARGET_CP_API, Constants.URL_API_LOGIN, HttpMethod.POST, users, ResultStatus.class);
    }


    /**
     * Namespace, User id를 통한 사용자 단건 조회(Get Users id namespaces detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param userId    the userId
     * @return the user detail
     */
    public Users getUsers(String cluster, String namespace, String userId) {
         Users users = restTemplateService.send(TARGET_CP_API, URI_API_USERS_DETAIL
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace)
                .replace("{userId:.+}", userId), HttpMethod.GET, null, Users.class);

         users.setClusterToken(Constants.EMPTY_VALUE);
         users.setSaToken(Constants.EMPTY_VALUE);
         users.setPassword(Constants.EMPTY_VALUE);


        return users;
    }


    /**
     * 전체 Users 목록 조회(Get All Users list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param userId    the user Id
     * @return the users list
     */
    public UsersList getUsersList(String cluster, String namespace, String userId) {

        String param = "?namespace=" + namespace + "&userId=" + userId;

        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_LIST.replace("{cluster:.+}", cluster) + param,
                HttpMethod.GET, null, UsersList.class);
    }


    /**
     * Users 정보 수정(Put Users info)
     *
     * @param cluster the cluster
     * @param userId  the userId
     * @param users   the users
     * @return the resultStatus
     */
    public ResultStatus updateUsers(String cluster, String userId, Users users) {
        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_INFO
                .replace("{cluster:.+}", cluster)
                .replace("{userId:.+}", userId), HttpMethod.PUT, users, ResultStatus.class);
    }


    /**
     * Users 권한 설정(Put Users authority setting)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param users     the users
     * @return the resultStatus
     */
    public ResultStatus modifyUsersConfig(String cluster,String namespace, List<Users> users, String userId) {

        String param = "?userId="+ userId;

        return restTemplateService.send(TARGET_CP_API, URI_API_USERS_CONFIG
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace) + param, HttpMethod.PUT, users, ResultStatus.class);
    }


    /**
     * 사용자 User Id 등록 여부 확인(Check for registered User Id)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param userId    the userId
     * @return the user detail
     */
    public ResultStatus checkRegisteredUser(String cluster, String namespace, String userId) {
        ResultStatus resultStatus = restTemplateService.send(TARGET_CP_API, URI_API_USERS_CHECK_REGISTER
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace)
                .replace("{userId:.+}", userId), HttpMethod.GET, null, ResultStatus.class);

        return resultStatus;
    }


}
