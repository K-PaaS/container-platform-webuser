package org.paasta.container.platform.web.user.clusters.namespaces;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Namespaces Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.02
 */
@Service
public class NamespacesService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Namespace service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public NamespacesService(RestTemplateService restTemplateService) {this.restTemplateService = restTemplateService;}

    /**
     * Namespaces 상세 조회(Get Namespaces detail)
     *
     * @param namespace the namespaces
     * @return the namespaces detail
     */
    Namespaces getNamespaces(String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_NAME_SPACES_DETAIL
                .replace("{namespace:.+}", namespace), HttpMethod.GET, null, Namespaces.class);
    }

}
