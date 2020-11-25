package org.paasta.container.platform.web.user.roles;

import org.paasta.container.platform.web.user.common.CommonUtils;
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
     * Instantiates a new Roles service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public RolesService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * Roles 목록 조회(Get Roles list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the roles list
     */
    RolesList getRolesList(String cluster, String namespace, int offset, int limit, String orderBy, String order, String searchName) {
        
        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_ROLES_LIST
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace) + param
                , HttpMethod.GET, null, RolesList.class);
    }


    /**
     * Roles 상세 조회(Get Roles detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param roleName  the roles name
     * @return the roles detail
     */
    Roles getRoles(String cluster, String namespace, String roleName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_ROLES_DETAIL
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{roleName:.+}", roleName),
                HttpMethod.GET, null, Roles.class);
    }


    /**
     * Roles YAML 조회(Get Roles yaml)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param roleName  the roles name
     * @return the roles yaml
     */
    Roles getRolesYaml(String cluster, String namespace, String roleName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_ROLES_YAML
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{roleName:.+}", roleName),
                HttpMethod.GET, null, Roles.class);
    }

}
