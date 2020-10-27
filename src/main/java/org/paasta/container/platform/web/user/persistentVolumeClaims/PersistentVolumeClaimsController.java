package org.paasta.container.platform.web.user.persistentVolumeClaims;

import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * PersistentVolumeClaims Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@RestController
@RequestMapping
public class PersistentVolumeClaimsController {

    private static final String VIEW_URL = "/persistentVolumeClaims";
    private final CommonService commonService;
    private final PersistentVolumeClaimsService persistentVolumeClaimsService;

    /**
     * Instantiates a new persistentVolumeClaims controller
     *
     * @param commonService                     the common service
     * @param persistentVolumeClaimsService  the persistentVolumeClaims Service
     */
    @Autowired
    public PersistentVolumeClaimsController(CommonService commonService, PersistentVolumeClaimsService persistentVolumeClaimsService) {
        this.commonService = commonService;
        this.persistentVolumeClaimsService = persistentVolumeClaimsService;
    }


    /**
     * PersistentVolumeClaim main 페이지 이동(Move PersistentVolumeClaim main page)
     *
     * @param httpServletRequest the http servlet request
     * @return the persistentVolumeClaims main
     */
    @GetMapping(value = Constants.URI_STORAGES)
    public ModelAndView getStoragesMain(HttpServletRequest httpServletRequest) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/main", new ModelAndView());
    }


    /**
     * PersistentVolumeClaim detail 페이지 이동(Move PersistentVolumeClaim detail page)
     *
     * @param httpServletRequest the http servlet request
     * @return the persistentVolumeClaims detail
     */
    @GetMapping(value = Constants.URI_STORAGES + "/{persistentVolumeClaimName:.+}")
    public ModelAndView getStoragesDetail(HttpServletRequest httpServletRequest, @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/detail", new ModelAndView());
    }


    /**
     * PersistentVolumeClaim event 페이지 이동(Move PersistentVolumeClaim event page)
     *
     * @param httpServletRequest the http servlet request
     * @return the persistentVolumeClaims event
     */
    @GetMapping(value = Constants.URI_STORAGES + "/{persistentVolumeClaimName:.+}/events")
    public ModelAndView getPersistentVolumeClaimEvent(HttpServletRequest httpServletRequest, @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }


    /**
     * PersistentVolumeClaim yaml 페이지 이동(Move PersistentVolumeClaim event yaml)
     *
     * @param httpServletRequest the http servlet request
     * @return the persistentVolumeClaims yaml
     */
    @GetMapping(value = Constants.URI_STORAGES + "/{persistentVolumeClaimName:.+}/yaml")
    public ModelAndView getPersistentVolumeClaimYaml(HttpServletRequest httpServletRequest, @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/yaml", new ModelAndView());
    }


    /**
     * PersistentVolumeClaims 목록 조회(Get PersistentVolumeClaims list)
     *
     * @param namespace the namespace
     * @param limit the limit
     * @param continueToken the continueToken
     * @return the persistentVolumeClaims list
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_LIST)
    @ResponseBody
    public PersistentVolumeClaimsList getPersistentVolumeClaimsList(@PathVariable(value = "namespace") String namespace,
                                                                    @RequestParam(required = false, defaultValue = "0") int limit,
                                                                    @RequestParam(required = false, name = "continue") String continueToken) {

        return persistentVolumeClaimsService.getPersistentVolumeClaimsList(namespace, limit, continueToken);
    }


    /**
     * PersistentVolumeClaims 상세 조회(Get PersistentVolumeClaims detail)
     *
     * @param namespace the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return the persistentVolumeClaims detail
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_DETAIL)
    public PersistentVolumeClaims getPersistentVolumeClaims(@PathVariable(value = "namespace") String namespace, @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return persistentVolumeClaimsService.getPersistentVolumeClaims(namespace, persistentVolumeClaimName);
    }


    /**
     * PersistentVolumeClaims YAML 조회(Get PersistentVolumeClaims yaml)
     *
     * @param namespace the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return the persistentVolumeClaims yaml
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_YAML)
    public PersistentVolumeClaims getPersistentVolumeClaimYaml(@PathVariable(value = "namespace") String namespace,
                                                               @PathVariable(value = "persistentVolumeClaimName") String persistentVolumeClaimName) {
        return persistentVolumeClaimsService.getPersistentVolumeClaimYaml(namespace, persistentVolumeClaimName);
    }

    /**
     * PersistentVolumeClaims 생성(Create PersistentVolumeClaims)
     *
     * @param namespace the namespace
     * @param yaml the yaml
     * @return return is succeeded
     */
    @PostMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_CREATE)
    @ResponseBody
    public Object createPersistentVolumeClaims(@PathVariable(value = "namespace") String namespace, @RequestBody String yaml) {
        return persistentVolumeClaimsService.createPersistentVolumeClaims(namespace,yaml);

    }

    /**
     * PersistentVolumeClaims 수정(Update PersistentVolumeClaims)
     *
     * @param namespace the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @param yaml the yaml
     * @return return is succeeded
     */
    @PutMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_UPDATE)
    @ResponseBody
    public Object updatePersistentVolumeClaims(@PathVariable(value = "namespace") String namespace, @PathVariable("persistentVolumeClaimName") String persistentVolumeClaimName, @RequestBody String yaml) {
        return persistentVolumeClaimsService.updatePersistentVolumeClaims(namespace, persistentVolumeClaimName, yaml);
    }

    /**
     * PersistentVolumeClaims 삭제(Delete PersistentVolumeClaims)
     *
     * @param namespace the namespace
     * @param persistentVolumeClaimName the persistentVolumeClaims name
     * @return return is succeeded
     */
    @DeleteMapping(value = Constants.API_URL + Constants.URI_API_STORAGES_DELETE)
    @ResponseBody
    public Object deletePersistentVolumeClaims(@PathVariable(value = "namespace") String namespace, @PathVariable("persistentVolumeClaimName") String persistentVolumeClaimName ){
        return persistentVolumeClaimsService.deletePersistentVolumeClaims(namespace, persistentVolumeClaimName);
    }
}
