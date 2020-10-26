package org.paasta.container.platform.web.user.workloads.deployments;

import org.paasta.container.platform.web.user.common.CommonUtils;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.paasta.container.platform.web.user.workloads.pods.Pods;
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
     * Deployments 목록 조회(Get Deployments list)
     * @param namespace the namespace
     * @return the deployments list
     */
    public DeploymentsList getDeploymentsList (String namespace, int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send( Constants.TARGET_CP_API,
                Constants.URI_API_DEPLOYMENTS_LIST
                        .replace( "{namespace:.+}", namespace ) + param
                ,HttpMethod.GET, null, DeploymentsList.class);
    }

    /**
     * Deployments 상세 조회(Get Deployments detail)
     *
     * @param namespace   the namespace
     * @param deploymentName the deployments name
     * @return the deployments detail
     */
    public Deployments getDeployments (String namespace, String deploymentName ) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_DETAIL
                        .replace( "{namespace:.+}", namespace )
                        .replace( "{deploymentName:.+}", deploymentName )
                , HttpMethod.GET, null, Deployments.class);
    }

    /**
     * Deployments YAML 조회(Get Deployments yaml)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return the deployments yaml
     */
    Deployments getDeploymentsYaml(String namespace, String deploymentName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.GET, null, Deployments.class);
    }

    /**
     * Deployments 생성(Create Deployments)
     *
     * @param namespace the namespace
     * @param yaml the yaml
     * @return
     */
    public Object createDeployments(String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_CREATE
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }

    /**
     * Deployments 수정(Update Deployments)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @param yaml the yaml
     * @return
     */
    public Object updateDeployments(String namespace, String deploymentName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_UPDATE
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }

    /**
     * Deployments 삭제(Delete Deployments)
     *
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return
     */
    public Object deleteDeployments(String namespace, String deploymentName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_DELETE
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.DELETE, null, Object.class);
    }

}
