package org.paasta.container.platform.web.user.users;

import org.paasta.container.platform.web.user.common.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * User Validator(IP / Browser Info)
 *
 * @author suslmk
 * @version 1.0
 * @since 2020.10.30
 **/
@Component
public class UsersValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersValidator.class);

    /**
     * 로그인 요청한 User의 Browser 및 IP 정보 추가(Add User's Browser, IP info)
     *
     * @param req  the req
     * @param user the user
     */
    public void getUsersValidate(HttpServletRequest req, Users user) {

        try{
            String sBrowser = getBrowserInfo(req);
            String sClientIP = getLocalServerIp(req);

            user.setBrowser(sBrowser);
            user.setClientIp(sClientIP);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Browser 정보 조회(Get Browser info)
     *
     * @param req the req
     * @return the string
     */
    protected String getBrowserInfo(HttpServletRequest req) {
        String browser = null;
        RequestWrapper requestWrapper = new RequestWrapper(req);
        try {
            String browserInfo = requestWrapper.getHeader("User-Agent"); // 사용자 User-Agent 값 얻기

            if (browserInfo != null) {
                if (browserInfo.indexOf("Trident") > -1) {
                    browser = "MSIE";
                } else if (browserInfo.indexOf("Chrome") > -1) {
                    browser = "Chrome";
                } else if (browserInfo.indexOf("Opera") > -1) {
                    browser = "Opera";
                } else if (browserInfo.indexOf("iPhone") > -1
                        && browserInfo.indexOf("Mobile") > -1) {
                    browser = "iPhone";
                } else if (browserInfo.indexOf("Android") > -1
                        && browserInfo.indexOf("Mobile") > -1) {
                    browser = "Android";
                }
            }
        } catch (Exception e) {
            LOGGER.info("Response error");
            e.printStackTrace();
        }
        return browser;
    }


    /**
     * Local 서버 IP 조회(Get Local Server Ip info)
     *
     * @param req the req
     * @return the string
     */
    protected String getLocalServerIp(HttpServletRequest req) {
        RequestWrapper requestWrapper = new RequestWrapper(req);
        String clientIp = null;
        try {
            clientIp = requestWrapper.getHeader("HTTP_X_FORWARDED_FOR");
            if (null == clientIp || clientIp.length() == 0
                    || clientIp.toLowerCase().equals("unknown")) {
                clientIp = requestWrapper.getHeader("REMOTE_ADDR");
            }
            if (null == clientIp || clientIp.length() == 0
                    || clientIp.toLowerCase().equals("unknown")) {
                clientIp = req.getRemoteAddr();
            }
            if(clientIp.equals("0:0:0:0:0:0:0:1"))
                clientIp = "127.0.0.1";
        } catch (Exception e) {
            LOGGER.info("Response error");
            e.printStackTrace();
        }
        return clientIp;
    }
}
