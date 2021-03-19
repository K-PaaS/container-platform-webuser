package org.paasta.container.platform.web.user.intro.accessInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.login.LoginService;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Access Token Controller 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.14
 */
@Api(value = "AccessTokenController v1")
@Controller
public class AccessTokenController {

    @Value("${access.cp-user-id}")
    private String cpUserId;

    private static final String VIEW_URL = "/intro";

    private final CommonService commonService;
    private final AccessTokenService accessTokenService;
    private final LoginService loginService;

    /**
     * Instantiates a new Access Token controller
     *
     * @param commonService
     * @param accessTokenService
     * @param loginService
     */
    @Autowired
    public AccessTokenController(CommonService commonService, AccessTokenService accessTokenService, LoginService loginService) {
        this.commonService = commonService;
        this.accessTokenService = accessTokenService;
        this.loginService = loginService;
    }



    /**
     * Intro access info 페이지 이동(Move Intro access info page)
     *
     * @param httpServletRequest the http servlet request
     * @return the view
     */
    @ApiOperation(value = "Intro access info 페이지 이동(Move Intro access info page)", nickname = "getIntroAccessInfo")
    @GetMapping(value = Constants.URI_INTRO_ACCESS_INFO)
    public ModelAndView getIntroAccessInfo(HttpServletRequest httpServletRequest) {

        UsersLoginMetaData usersLoginMetaData = loginService.getAuthenticationUserMetaData();
        String userId = usersLoginMetaData.getUserId();

        ModelAndView mv = new ModelAndView();
        mv.addObject("userId", userId);
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/accessInfo", mv);
    }


    /**
     * Secret 조회(Get Secret)
     *
     * @param cluster   the cluster
     * @param namespace the namespace
     * @param accessTokenName the access token name
     * @return the AccessToken
     */
    @ApiOperation(value = "Secret 조회(Get Secret)", nickname = "getSecret")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cluster", value = "클러스터 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "accessTokenName", value = "액세스 토큰 명",  required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_SECRETS_DETAIL)
    @ResponseBody
    public AccessToken getSecret(@PathVariable(value = "cluster") String cluster,
                                 @PathVariable(value = "namespace") String namespace,
                                 @PathVariable(value = "accessTokenName") String accessTokenName) {
        return accessTokenService.getToken(cluster, namespace, accessTokenName);
    }

}
