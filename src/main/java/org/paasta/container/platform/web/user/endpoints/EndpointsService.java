package org.paasta.container.platform.web.user.endpoints;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Endpoints Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Service
public class EndpointsService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Endpoints service.
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public EndpointsService(RestTemplateService restTemplateService) {this.restTemplateService = restTemplateService;}


    /**
     * Endpoints 상세 정보를 조회한다.
     *
     * @param namespace   the namespace
     * @param serviceName the service name
     * @return the endpoints
     */
    Endpoints getEndpoints(String namespace, String serviceName) {
        String TARGET_CP_API = Constants.TARGET_CP_API;
        return restTemplateService.send(TARGET_CP_API, Constants.URI_API_ENDPOINTS_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.GET, null, Endpoints.class);
    }

}
