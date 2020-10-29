package org.paasta.container.platform.web.user.users;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Component
public class UsersValidator {

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

    protected String getBrowserInfo(HttpServletRequest req) {
        String browser = null;
        try {
            String browserInfo = req.getHeader("User-Agent"); // 사용자 User-Agent 값 얻기

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
            e.printStackTrace();
        }
        return browser;
    }

    protected String getLocalServerIp(HttpServletRequest req) {
        String sIP = null;
        try {
            String clientIp = req.getHeader("HTTP_X_FORWARDED_FOR");
            if (null == clientIp || clientIp.length() == 0
                    || clientIp.toLowerCase().equals("unknown")) {
                sIP = req.getHeader("REMOTE_ADDR");
            }
            if (null == clientIp || clientIp.length() == 0
                    || clientIp.toLowerCase().equals("unknown")) {
                sIP = req.getRemoteAddr();
            }
        } catch (Exception e) {

        }
        return sIP;
    }
}
