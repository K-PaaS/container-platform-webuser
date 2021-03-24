package org.paasta.container.platform.web.user.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Dashboard Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.24
 */
@Api(value = "DashboardController v1")
@Controller
public class DashboardController {


    /**
     * 권한없음 페이지로 이동(Move to an unauthorized page)
     *
     * @return the view
     */
    @ApiOperation(value = "권한없음 페이지로 이동(Move to an unauthorized page)", nickname = "pageError401")
    @NoAuth
    @GetMapping(value = "/common/error/unauthorized")
    public ModelAndView pageError401() {
        ModelAndView model = new ModelAndView();

        model.setViewName("/common/unauthorized");
        return model;
    }


    /**
     * 토큰 만료로 인한 자동 로그아웃 안내 페이지로 이동(Move to auto logout page by token expired)
     *
     * @return the view
     */
    @ApiOperation(value = "토큰 만료로 인한 자동 로그아웃 안내 페이지로 이동(Move to auto logout page by token expired)", nickname = "pageTokenExpired")
    @NoAuth
    @GetMapping(value = "/common/tokenExpired")
    public ModelAndView pageTokenExpired() {
        ModelAndView model = new ModelAndView();

        model.setViewName("/common/tokenExpired");
        return model;
    }
}
