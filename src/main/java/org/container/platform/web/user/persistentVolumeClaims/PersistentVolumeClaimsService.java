package org.container.platform.web.user.persistentVolumeClaims;

import org.container.platform.web.user.common.CommonUtils;
import org.container.platform.web.user.common.Constants;
import org.container.platform.web.user.common.RestTemplateService;
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
     * Instantiates a new deployments service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public PersistentVolumeClaimsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * PersistentVolumeClaims 목록 조회(Get PersistentVolumeClaims list)
     *
     * @param cluster    the cluster
     * @param namespace  the namespace
     * @param offset     the offset
     * @param limit      the limit
     * @param orderBy    the orderBy
     * @param order      the order
     * @param searchName the searchName
     * @return the persistentVolumeClaims list
     */
    PersistentVolumeClaimsList getPersistentVolumeClaimsList(String cluster,String namespace, int offset, int limit, String orderBy, String order, String searchName) {

        String param = CommonUtils.makeResourceListParamQuery(offset, limit, orderBy, order, searchName);

        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_LIST
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace) + param
                , HttpMethod.GET, null, PersistentVolumeClaimsList.class);
    }


    /**
     * PersistentVolumeClaims 상세 정보(Get PersistentVolumeClaims detail)
     *
     * @param cluster    the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return the persistentVolumeClaims detail
     */
    PersistentVolumeClaims getPersistentVolumeClaims(String cluster, String namespace, String persistentVolumeClaimName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_DETAIL
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{persistentVolumeClaimName:.+}", persistentVolumeClaimName),
                HttpMethod.GET, null, PersistentVolumeClaims.class);
    }


    /**
     * PersistentVolumeClaims YAML 조회(Get PersistentVolumeClaims yaml)
     *
     * @param cluster    the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return the persistentVolumeClaims yaml
     */
    public PersistentVolumeClaims getPersistentVolumeClaimYaml(String cluster, String namespace, String persistentVolumeClaimName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_YAML
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{persistentVolumeClaimName:.+}", persistentVolumeClaimName),
                HttpMethod.GET, null, PersistentVolumeClaims.class);
    }


    /**
     * PersistentVolumeClaims 생성(Create PersistentVolumeClaims)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param yaml      the yaml
     * @return return is succeeded
     */
    public Object createPersistentVolumeClaims(String cluster, String namespace, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_CREATE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace),
                HttpMethod.POST, yaml, Object.class, "application/yaml");
    }

    /**
     * PersistentVolumeClaims 수정(Update PersistentVolumeClaims)
     *
     * @param cluster     the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @param yaml                      the yaml
     * @return return is succeeded
     */
    public Object updatePersistentVolumeClaims(String cluster, String namespace, String persistentVolumeClaimName, String yaml) {
        return restTemplateService.sendYaml(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_UPDATE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{persistentVolumeClaimName:.+}", persistentVolumeClaimName),
                HttpMethod.PUT, yaml, Object.class, "application/yaml");
    }

    /**
     * PersistentVolumeClaims 삭제(Delete PersistentVolumeClaims)
     *
     * @param cluster     the cluster
     * @param namespace                 the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return return is succeeded
     */
    public Object deletePersistentVolumeClaims(String cluster, String namespace, String persistentVolumeClaimName) {
        return restTemplateService.send(Constants.TARGET_CP_API, Constants.URI_API_STORAGES_DELETE
                        .replace("{cluster:.+}", cluster)
                        .replace("{namespace:.+}", namespace)
                        .replace("{persistentVolumeClaimName:.+}", persistentVolumeClaimName),
                HttpMethod.DELETE, null, Object.class);
    }
}
