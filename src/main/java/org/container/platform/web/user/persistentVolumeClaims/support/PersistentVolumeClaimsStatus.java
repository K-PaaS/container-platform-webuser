package org.container.platform.web.user.persistentVolumeClaims.support;

import lombok.Data;
import org.container.platform.web.user.common.model.CommonCondition;

import java.util.List;
import java.util.Map;

/**
 * PersistentVolumeClaims Status Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@Data
public class PersistentVolumeClaimsStatus {
    private List<String> accessModes;
    private Map<String, Object> capacity;
    private List<CommonCondition> conditions;
    private String phase;
}
