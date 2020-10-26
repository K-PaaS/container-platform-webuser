package org.paasta.container.platform.web.user.managements.resourceQuotas;

import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hrjin
 * @version 1.0
 * @since 2020.10.24
 **/
@RestController
public class ResourceQuotasController {

    private final ResourceQuotasService resourceQuotasService;

    public ResourceQuotasController(ResourceQuotasService resourceQuotasService) {
        this.resourceQuotasService = resourceQuotasService;
    }

    /**
     * Namespaces resourceQuotas 정보 조회(Get Namespaces resourceQuotas)
     *
     * @param namespace the namespaces
     * @return the resourceQuotas list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_NAME_SPACES_RESOURCE_QUOTAS)
    public ResourceQuotasList getResourceQuotasList(@PathVariable String namespace) {
        return resourceQuotasService.getResourceQuotasList(namespace);
    }
}
