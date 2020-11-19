package org.paasta.container.platform.web.user.clusters.limitRanges;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * LimitRanges Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.11.18
 **/
@Api(value = "LimitRangesController v1")
@RestController
public class LimitRangesController {

    private final LimitRangesService limitRangesService;

    public LimitRangesController( LimitRangesService limitRangesService) {
        this.limitRangesService = limitRangesService;
    }

    /**
     * LimitRanges namespaces 정보 조회(Get LimitRanges namespaces)
     *
     * @param namespace the namespace
     * @return the limitRanges list
     */
    @ApiOperation(value = "LimitRanges namespaces 정보 조회(Get LimitRanges namespaces)", nickname = "getLimitRangesList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_NAME_SPACES_LIMIT_RANGES)
    public LimitRangesTemplateList getLimitRangesList(@PathVariable String namespace) {
        return limitRangesService.getLimitRangesList(namespace);
    }


}
