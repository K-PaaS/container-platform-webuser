package org.paasta.container.platform.web.user.login.model;

import lombok.Data;

@Data
public class UsersLoginMetaDataItem {
    private String namespace;
    private String userType;
}
