package org.paasta.container.platform.web.user.clusters.limitRanges;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * @author jjy
 * @version 1.0
 * @since 2020.11.18
 **/
@Service
public class LimitRangesService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new ResourceQuotas service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public LimitRangesService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    /**
     * Namespaces LimitRangesList 조회(Get limitRangesList namespaces)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @return the limitRangesList list
     */
    public LimitRangesTemplateList getLimitRangesList(String cluster, String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_NAME_SPACES_LIMIT_RANGES
                .replace("{cluster:.+}", cluster)
                .replace("{namespace:.+}", namespace), HttpMethod.GET, null, LimitRangesTemplateList.class);
    }
}
