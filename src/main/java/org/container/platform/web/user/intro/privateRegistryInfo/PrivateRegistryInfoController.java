package org.container.platform.web.user.intro.privateRegistryInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.container.platform.web.user.common.CommonService;
import org.container.platform.web.user.common.Constants;
import org.container.platform.web.user.common.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Private Registry Info Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.15
 */
@Api(value = "PrivateRegistryInfoController v1")
@Controller
public class PrivateRegistryInfoController {

    private static final String VIEW_URL = "/intro";
    private final CommonService commonService;
    private final PropertyService propertyService;
    private final PrivateRegistryService privateRegistryService;

    /**
     * Instantiates a private registry controller
     *
     * @param commonService          the common service
     * @param propertyService        the property service
     * @param privateRegistryService the private registry service
     */
    @Autowired
    public PrivateRegistryInfoController(CommonService commonService, PropertyService propertyService, PrivateRegistryService privateRegistryService) {
        this.commonService = commonService;
        this.propertyService = propertyService;
        this.privateRegistryService = privateRegistryService;
    }


    /**
     * Private Registry info 페이지 이동(Move Private Registry info page)
     *
     * @param httpServletRequest the http servlet request
     * @return the model and view
     */
    @ApiOperation(value = "Private Registry info 페이지 이동(Move Private Registry info page)", nickname = "getIntroAccessInfo")
    @GetMapping(value = Constants.URI_INTRO_PRIVATE_REGISTRY_INFO)
    public ModelAndView getIntroAccessInfo(HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("privateRegistryImageName", propertyService.getPrivateRegistryImageName());

        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/privateRegistryInfo", mv);
    }


    /**
     * Private Registry 조회(Get Private Registry)
     *
     * @param imageName the imageName
     * @return the private registry
     */
    @ApiOperation(value = " Private Registry 조회(Get Private Registry)", nickname = "getPrivateRegistry")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageName", value = "이미지 명", required = true, dataType = "string", paramType = "path"),
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_PRIVATE_REGISTRY_DETAIL)
    @ResponseBody
    public PrivateRegistry getPrivateRegistry(@PathVariable("imageName") String imageName) {
        return privateRegistryService.getPrivateRegistry(imageName);
    }
}
