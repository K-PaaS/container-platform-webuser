package org.paasta.container.platform.web.user.roles;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Roles List Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.10.13
 */
@Data
public class RolesList {

    private String resultCode;
    private String resultMessage;
    private Map<String,Object> metadata;
    private List<Roles> items;

}
