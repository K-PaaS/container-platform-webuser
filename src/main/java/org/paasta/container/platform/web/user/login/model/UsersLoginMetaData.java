package org.paasta.container.platform.web.user.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersLoginMetaData implements Serializable {

    private String userId;
    private String accessToken;
    private String userMetaData;
    private String selectedNamespace;
    private String clusterName;

}
