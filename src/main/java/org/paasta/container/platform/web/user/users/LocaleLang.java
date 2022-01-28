package org.paasta.container.platform.web.user.users;

import lombok.Data;

@Data
public class LocaleLang {
    private String uLang ;

    public LocaleLang(String uLang) {
        this.uLang = uLang;
    }
}
