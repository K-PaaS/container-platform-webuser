package org.paasta.container.platform.web.user.roles;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Roles Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.10.13
 */
@Service
public class RolesService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Roles service.
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public RolesService(RestTemplateService restTemplateService) 
    {this.restTemplateService = restTemplateService;}


    /**
     * Roles 목록을 조회한다.
     *
     * @param namespace the namespace
     * @return the roles list
     */
    RolesList getRolesList(String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_ROLES_LIST
                        .replace("{namespace:.+}", namespace),
                HttpMethod.GET, null, RolesList.class);
    }


    /**
     * Roles 상세 정보를 조회한다.
     *
     * @param namespace the namespace
     * @param roleName the role name
     * @return the roles
     */
    Roles getRoles(String namespace, String roleName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_ROLES_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{roleName:.+}", roleName),
                HttpMethod.GET, null, Roles.class);
    }


    /**
     * Roles YAML을 조회한다.
     *
     * @param namespace  the namespace
     * @param roleName the role name
     * @return the  Roles yaml
     */
    Roles getRolesYaml(String namespace, String roleName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_ROLES_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{roleName:.+}", roleName),
                HttpMethod.GET, null, Roles.class);
    }



}
