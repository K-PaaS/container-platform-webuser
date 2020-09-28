package org.paasta.container.platform.web.user.common;

import java.util.Arrays;

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
     * @param params
     * @return boolean
     */
    public static boolean stringNullCheck(String... params) {
        return Arrays.stream(params).allMatch(param -> null != param && !param.equals(""));
    }
}
