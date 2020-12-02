package org.paasta.container.platform.web.user.intro.privateRegistryInfo;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Private Registry Service 클래스
 *
 * @author kjh
 * @version 1.0
 * @since 2020.12.01
 */
@Service
public class PrivateRegistryService {

    private final RestTemplateService restTemplateService;

    @Autowired
    public PrivateRegistryService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    /**
     * PrivateRegistry 상세 조회(Get PrivateRegistry detail)
     *
     * @param cluster        the cluster
     * @param namespace      the namespace
     * @param repositoryName the repositoryName
     * @return the private registry
     */
    public PrivateRegistry getPrivateRegistry(String cluster, String namespace, String repositoryName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PRIVATE_REGISTRY_DETAIL
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{repositoryName:.+}", repositoryName)
                , HttpMethod.GET, null, PrivateRegistry.class);
    }


}
