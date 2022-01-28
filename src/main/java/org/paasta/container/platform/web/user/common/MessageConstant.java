package org.paasta.container.platform.web.user.common;


import org.paasta.container.platform.web.user.users.LocaleLang;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * MessageConstant 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.03.15
 */
public enum MessageConstant {


    INVALID_PASSWORD ("현재 비밀번호가 올바르지 않습니다.", "The passwords do not match." ),
    NAMESPACE_CHANGE_SUCCEEDED  ("네임스페이스 변경에 성공하였습니다.", "Namespaces have been changed successfully."),
    NAMESPACE_CHANGE_FAILED ("네임스페이스 변경에 실패하였습니다.", "Namespaces change failed.");

    private String ko_msg;
    private String eng_msg;

    MessageConstant(String ko_msg, String eng_msg) {
        this.ko_msg = ko_msg;
        this.eng_msg = eng_msg;
    }

    public String getKo_msg() {
        return ko_msg;
    }
    public String getEng_msg() {
        return eng_msg;
    }

    public String getMsg() {
        LocaleLang localeLang = null;
        String u_lang ="";
        try {
            localeLang= (LocaleLang) SecurityContextHolder.getContext().getAuthentication().getDetails();
            u_lang = localeLang.getULang();
        } catch (Exception e) {
            return getEng_msg();
        }
        if (u_lang.equals(Constants.LANG_KO)) {
            return getKo_msg();
        }
        return getEng_msg();
    }

}
