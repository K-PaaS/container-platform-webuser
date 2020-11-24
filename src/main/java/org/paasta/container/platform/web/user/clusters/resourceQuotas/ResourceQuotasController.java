package org.paasta.container.platform.web.user.clusters.resourceQuotas;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ResourceQuotas Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.24
 **/
@Api(value = "ResourceQuotasController v1")
@RestController
public class ResourceQuotasController {

    private final ResourceQuotasService resourceQuotasService;

    public ResourceQuotasController(ResourceQuotasService resourceQuotasService) {
        this.resourceQuotasService = resourceQuotasService;
    }

    /**
     * ResourceQuotas namespaces 정보 조회(Get ResourceQuotas namespaces)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the resourceQuotas list
     */
    @ApiOperation(value = "ResourceQuotas namespaces 정보 조회(Get ResourceQuotas namespaces)", nickname = "getResourceQuotasList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_NAME_SPACES_RESOURCE_QUOTAS)
    public ResourceQuotasList getResourceQuotasList(@PathVariable String cluster, @PathVariable String namespace) {
        return resourceQuotasService.getResourceQuotasList(cluster, namespace);
    }
}
