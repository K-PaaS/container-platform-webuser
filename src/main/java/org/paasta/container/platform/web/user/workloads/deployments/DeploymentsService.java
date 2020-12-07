package org.paasta.container.platform.web.user.workloads.deployments;

import org.paasta.container.platform.web.user.common.CommonUtils;
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
     * Deployments 목록 조회(Get Deployments list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the deployments list
     */
    public DeploymentsList getDeploymentsList(String cluster, String namespace, int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_LIST
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace) + param, HttpMethod.GET, null, DeploymentsList.class);
    }

    /**
     * Deployments 상세 조회(Get Deployments detail)
     *
     * @param cluster    the cluster
     * @param namespace   the namespace
     * @param deploymentName the deployments name
     * @return the deployments detail
     */
    public Deployments getDeployments (String cluster, String namespace, String deploymentName ) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_DETAIL
                        .replace("{cluster:.+}", cluster)
                        .replace( "{namespace:.+}", namespace )
                        .replace( "{deploymentName:.+}", deploymentName )
                , HttpMethod.GET, null, Deployments.class);
    }

    /**
     * Deployments YAML 조회(Get Deployments yaml)
     *
     * @param cluster    the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return the deployments yaml
     */
    Deployments getDeploymentsYaml(String cluster, String namespace, String deploymentName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_YAML
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.GET, null, Deployments.class);
    }

    /**
     * Deployments 생성(Create Deployments)
     *
     * @param cluster    the cluster
     * @param namespace the namespace
     * @param yaml the yaml
     * @return return is succeeded
     */
    public Object createDeployments(String cluster, String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_CREATE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }

    /**
     * Deployments 수정(Update Deployments)
     *
     * @param cluster    the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @param yaml the yaml
     * @return return is succeeded
     */
    public Object updateDeployments(String cluster, String namespace, String deploymentName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_UPDATE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }

    /**
     * Deployments 삭제(Delete Deployments)
     *
     * @param cluster    the cluster
     * @param namespace the namespace
     * @param deploymentName the deployments name
     * @return return is succeeded
     */
    public Object deleteDeployments(String cluster, String namespace, String deploymentName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_DEPLOYMENTS_DELETE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{deploymentName:.+}", deploymentName),
                HttpMethod.DELETE, null, Object.class);
    }

}
