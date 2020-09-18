package org.paasta.container.platform.web.user.persistentVolumeClaims;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * PersistentVolumeClaims Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@Service
public class PersistentVolumeClaimsService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new deployments service.
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public PersistentVolumeClaimsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * PersistentVolumeClaims 목록을 조회한다.
     *
     * @param namespace the namespace
     * @return the Persistent Volume Claims List
     */
    PersistentVolumeClaimsList getPersistentVolumeClaimsList(String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_LIST
                        .replace("{namespace:.+}", namespace),
                HttpMethod.GET, null, PersistentVolumeClaimsList.class);
    }

    /**
     * PersistentVolumeClaims 상세 정보를 조회한다.
     *
     * @param namespace                     the namespace
     * @param persistentVolumeClaimName  the Persistent Volume Claim name
     * @return the Persistent Volume Claims
     */
    PersistentVolumeClaims getPersistentVolumeClaims(String namespace, String persistentVolumeClaimName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_DETAIL
                        .replace("{namespace:.+}", namespace)
                        .replace("{persistentVolumeClaimName:.+}", persistentVolumeClaimName),
                HttpMethod.GET, null, PersistentVolumeClaims.class);
    }


    /**
     * PersistentVolumeClaims YAML 을 조회한다.
     *
     * @param namespace                    the namespace
     * @param persistentVolumeClaimName the Persistent Volume Claim name
     * @return the Persistent Volume Claims
     */
    public PersistentVolumeClaims getPersistentVolumeClaimYaml(String namespace, String persistentVolumeClaimName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_YAML
                        .replace("{namespace:.+}", namespace)
                        .replace("{persistentVolumeClaimName:.+}", persistentVolumeClaimName),
                HttpMethod.GET, null, PersistentVolumeClaims.class);
    }
}
