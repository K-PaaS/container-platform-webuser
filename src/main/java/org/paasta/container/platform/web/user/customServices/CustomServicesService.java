package org.paasta.container.platform.web.user.customServices;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Custom Services Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.10
 */
@Service
public class CustomServicesService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Custom services service.
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public CustomServicesService(RestTemplateService restTemplateService) {this.restTemplateService = restTemplateService;}


    /**
     * Services 목록을 조회한다.
     *
     * @param namespace the namespace
     * @param limit the limit
     * @param continueToken the continueToken
     * @return the custom services list
     */
    CustomServicesList getCustomServicesList(String namespace, int limit, String continueToken) {

        String param = "";

        if(continueToken != null) {
            param = "&continue=" + continueToken;
        }

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_LIST
                        .replace("{namespace:.+}", namespace) + "?limit=" + limit + param
                ,HttpMethod.GET, null, CustomServicesList.class);
    }


    /**
     * Services 상세 정보를 조회한다.
     *
     * @param namespace   the namespace
     * @param serviceName the service name
     * @return the custom services
     */
    CustomServices getCustomServices(String namespace, String serviceName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.GET, null, CustomServices.class);
    }


    /**
     * Services YAML을 조회한다.
     *
     * @param namespace   the namespace
     * @param serviceName the service name
     * @return the custom services yaml
     */
    CustomServices getCustomServicesYaml(String namespace, String serviceName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.GET, null, CustomServices.class);
    }



    /**
     * Services를 생성한다.
     *
     * @param namespace the namespace
     * @param yaml the yaml
     * @return
     */
    public Object createCustomServices(String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_CREATE
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }


    /**
     * Services를 수정한다.
     *
     * @param namespace the namespace
     * @param serviceName the service name
     * @param yaml the yaml
     * @return
     */
    public Object updateCustomServices(String namespace, String serviceName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_UPDATE
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }


    /**
     * Services를 삭제한다.
     *
     * @param namespace   the namespace
     * @param serviceName   the service name
     * @return
     */
    public Object deleteCustomServices(String namespace, String serviceName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_DELETE
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.DELETE, null, Object.class);
    }

}
