package org.container.platform.web.user.intro.privateRegistryInfo;

import org.container.platform.web.user.common.Constants;
import org.container.platform.web.user.common.RestTemplateService;
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
     * @param imageName the imageName
     * @return the private registry
     */
    public PrivateRegistry getPrivateRegistry(String imageName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_PRIVATE_REGISTRY_DETAIL
                        .replace("{imageName:.+}", imageName)
                , HttpMethod.GET, null, PrivateRegistry.class);
    }


}
