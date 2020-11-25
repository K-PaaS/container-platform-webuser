package org.paasta.container.platform.web.user.endpoints;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Endpoints Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Api(value = "EndpointsController v1")
@Controller
public class EndpointsController {

    private final EndpointsService endpointsService;

    /**
     * Instantiates a new Endpoints controller
     *
     * @param endpointsService the endpoints service
     */
    @Autowired
    public EndpointsController(EndpointsService endpointsService) {
        this.endpointsService = endpointsService;
    }


    /**
     * Endpoints 상세 조회(Get Endpoints detail)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param serviceName the service name
     * @return the endpoints detail
     */
    @ApiOperation(value = "Endpoints 상세 조회(Get Endpoints detail)", nickname = "getEndpoints")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "serviceName", value = "서비스 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_ENDPOINTS_DETAIL)
    @ResponseBody
    public Endpoints getEndpoints(@PathVariable(value = "cluster") String cluster,
                                  @PathVariable(value = "namespace") String namespace,
                                  @PathVariable(value = "serviceName") String serviceName) {
        return endpointsService.getEndpoints(cluster, namespace, serviceName);
    }

}
