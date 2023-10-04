package org.container.platform.web.user.workloads.overview;

import org.container.platform.web.user.common.Constants;
import org.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * WorkloadOverview Service
 *
 * @author suslmk
 * @version 1.0
 * @since 2020.11.18
 */
@Service
public class WorkloadOverviewService {
    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new WorkloadOverview service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public WorkloadOverviewService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    /**
     * WorkloadOverview Resource Count 조회(Get WorkloadOverview Resource Count)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @return the pods list
     */
    Overview getResourceCount(String cluster, String namespace) {

        return restTemplateService.send(Constants.TARGET_CP_API,
                Constants.URI_WORKLOAD_RESOURCE_COUNT
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                , HttpMethod.GET, null, Overview.class);
    }

}
