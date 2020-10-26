package org.paasta.container.platform.web.user.clusters.resourceQuotas;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * @author hrjin
 * @version 1.0
 * @since 2020.10.24
 **/
@Service
public class ResourceQuotasService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Namespace service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public ResourceQuotasService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    /**
     * Namespaces ResourceQuotas 조회(Get Namespaces resourceQuotas)
     *
     * @param namespace the namespaces
     * @return the resourceQuotas list
     */
    ResourceQuotasList getResourceQuotasList(String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_NAME_SPACES_RESOURCE_QUOTAS
                .replace("{namespace:.+}", namespace), HttpMethod.GET, null, ResourceQuotasList.class);
    }
}
