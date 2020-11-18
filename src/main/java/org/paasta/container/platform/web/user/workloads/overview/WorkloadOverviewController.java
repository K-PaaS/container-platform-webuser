package org.paasta.container.platform.web.user.workloads.overview;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Workload Overview Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Api(value = "WorkloadOverviewController v1")
@RestController
public class WorkloadOverviewController {

    private static final String VIEW_URL = "/workloads";
    private final CommonService commonService;
    private final WorkloadOverviewService workloadService;

    /**
     * Instantiates a new Workload overview controller
     *
     * @param commonService the common service
     */
    @Autowired
    public WorkloadOverviewController(CommonService commonService, WorkloadOverviewService workloadService) {
        this.commonService = commonService;
        this.workloadService = workloadService;

    }


    /**
     * Workload Overview 페이지 이동(Move Workload Overview page)
     *
     * @param httpServletRequest the http servlet request
     * @return the workload overview
     */
    @ApiOperation(value = "Workload Overview 페이지 이동(Move Workload Overview page)", nickname = "getWorkloadOverview")
    @GetMapping(value = Constants.URI_WORKLOAD_OVERVIEW)
    public ModelAndView getWorkloadOverview(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/overview", new ModelAndView());
    }

    /**
     * Workload Overview 조회(Get Overview)
     *
     * @param namespace  the namespace
     * @return the workload overview
     */
    @ApiOperation(value = "Workload Overview 조회(Get Overview)", nickname = "getOverviewStatistics")
    @GetMapping(value = Constants.API_URL + Constants.URI_WORKLOAD_RESOURCE_COUNT)
    public Overview getOverviewStatistics(@PathVariable(value = "namespace") String namespace) {
        return workloadService.getResourceCount(namespace);
    }

}
