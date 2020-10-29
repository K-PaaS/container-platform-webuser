package org.paasta.container.platform.web.user.customServices;

import org.paasta.container.platform.web.user.common.CommonUtils;
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
     * Instantiates a new Custom services service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public CustomServicesService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * Services 목록 조회(Get Services list)
     *
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the custom services list
     */
    CustomServicesList getCustomServicesList(String namespace, int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_LIST
                        .replace("{namespace:.+}", namespace) + param
                , HttpMethod.GET, null, CustomServicesList.class);
    }


    /**
     * Services 상세 조회(Get Services detail)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return the custom services detail
     */
    CustomServices getCustomServices(String namespace, String serviceName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.GET, null, CustomServices.class);
    }


    /**
     * Services YAML 조회(Get Services yaml)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return the custom services yaml
     */
    CustomServices getCustomServicesYaml(String namespace, String serviceName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.GET, null, CustomServices.class);
    }

    /**
     * Services 생성(Create Services)
     *
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    public Object createCustomServices(String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_CREATE
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }


    /**
     * Services 수정(Update Services)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @param yaml        the yaml
     * @return return is succeeded
     */
    public Object updateCustomServices(String namespace, String serviceName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_UPDATE
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }


    /**
     * Services 삭제(Delete Services)
     *
     * @param namespace   the namespace
     * @param serviceName the services name
     * @return return is succeeded
     */
    public Object deleteCustomServices(String namespace, String serviceName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_SERVICES_DELETE
                        .replace("{namespace:.+}", namespace)
                        .replace("{serviceName:.+}", serviceName),
                HttpMethod.DELETE, null, Object.class);
    }

}
