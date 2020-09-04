package org.paasta.container.platform.web.user.managements;

import lombok.Data;

import java.util.Map;

/**
 * ResourceQuotaStatus Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.03
 */
@Data
public class ResourceQuotaStatus {

    private Map<String, String> hard;
    private Map<String, String> used;

}
