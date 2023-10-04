package org.container.platform.web.user.roles;

import lombok.Data;
import org.container.platform.web.user.common.model.CommonMetaData;

import java.util.ArrayList;


/**
 * Roles Model 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.10.13
 */
@Data
public class Roles {
    private String resultCode;
    private String resultMessage;
    private String nextActionUrl;

    private CommonMetaData metadata;
    private ArrayList rules;

    private String sourceTypeYaml;

}