package org.paasta.container.platform.web.user.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

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

        for(String key : map.keySet()) {
            if(StringUtils.hasText(map.get(key))) {
                checkParamList.add(key);
            }
        }

        return checkParamList;
    }
}
