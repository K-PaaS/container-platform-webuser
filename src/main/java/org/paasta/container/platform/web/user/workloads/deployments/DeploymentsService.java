package org.paasta.container.platform.web.user.workloads.deployments;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Deployments Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.07
 */
@Service
public class DeploymentsService {

    private final RestTemplateService restTemplateService;

    @Autowired
    public DeploymentsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * Deployments 목록을 조회한다.
     * @param namespace   the namespace
     * @return the deployments list
     */
    public DeploymentsList getDeploymentsList (String namespace ) {
        return restTemplateService.send( Constants.TARGET_CP_API,
                Constants.URI_API_DEPLOYMENTS_LIST
                        .replace( "{namespace:.+}", namespace ),
                HttpMethod.GET, null, DeploymentsList.class);
    }

    /**
     * Deployments 상세 정보를 조회한다.
     *
     * @param namespace   the namespace
     * @param deploymentName the deployments name
     * @return the deployments
     */
    public Deployments getDeployments (String namespace, String deploymentName ) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_DETAIL
                        .replace( "{namespace:.+}", namespace )
                        .replace( "{deploymentName:.+}", deploymentName )
                , HttpMethod.GET, null, Deployments.class);
    }

    /**
     * Deployments YAML을 조회한다.
     *
     * @param namespace   the namespace
     * @param deploymentName the deployments name
     * @return the deployments yaml
     */
    Deployments getDeploymentsYaml(String namespace, String deploymentName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.GET, null, Deployments.class);
    }

}
