package org.paasta.container.platform.web.user.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Common utils 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.09.25
 */
public class CommonUtils {

    /**
     * 요청 파라미터들의 빈값 또는 null값 확인을 하나의 메소드로 처리할 수 있도록 생성한 메소드
     * 요청 파라미터 중 빈값 또는 null값인 파라미터가 있는 경우, false를 리턴한다.
     *
     * @return boolean
     */
    public static boolean paramCheck(String... params) {
        return Arrays.stream(params).allMatch(param -> null != param && !param.equals(""));
    }


    /**
     * 요청 파라미터들 중 빈 값 또는 null인 파라미터를 추출한다.
     *
     * @param obj the Object
     * @return the List<String>
     */
    public static List<String> stringNullCheck(Object obj) {
        List<String> checkParamList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.convertValue(obj, Map.class);

        for (String key : map.keySet()) {
            if (StringUtils.hasText(map.get(key))) {
                checkParamList.add(key);
            }
        }

        return checkParamList;
    }


    /**
     * Cookies 추가 (HttpOnly = true, 만료 1시간 )
     */
    public static void addCookies(HttpServletResponse response, String name, String value) {

        CookieGenerator cookie = new CookieGenerator();
        cookie.setCookieName(name);
        cookie.setCookieMaxAge(60 * 60); // 1hours
        cookie.setCookieHttpOnly(true);
        cookie.addCookie(response, value);
    }


    /**
     * Cookies 삭제
     */
    public static void removeCookies(HttpServletResponse response, String name) {

        CookieGenerator cookie = new CookieGenerator();

        cookie.setCookieName(name);
        cookie.setCookieMaxAge(0);
        cookie.addCookie(response, null);

    }


    /**
     * Cookies 가져오기
     */
    public static String getCookie(HttpServletRequest request, String name) {

        String cookieValue = null;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                cookieValue = cookies[i].getValue();
            }
        }
        return cookieValue;
    }

}
