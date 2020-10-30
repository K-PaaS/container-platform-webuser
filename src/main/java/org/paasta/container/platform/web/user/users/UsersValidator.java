package org.paasta.container.platform.web.user.users;

import com.sun.xml.internal.ws.client.sei.MethodHandler;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandler.class);

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
            LOGGER.info("Response error");
            e.printStackTrace();
        }
        return browser;
    }

    protected String getLocalServerIp(HttpServletRequest req) {
        String clientIp = null;
        try {
            clientIp = req.getHeader("HTTP_X_FORWARDED_FOR");
            if (null == clientIp || clientIp.length() == 0
                    || clientIp.toLowerCase().equals("unknown")) {
                clientIp = req.getHeader("REMOTE_ADDR");
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
