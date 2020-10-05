package org.paasta.container.platform.web.user.persistentVolumeClaims;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.persistentVolumeClaims.support.PersistentVolumeClaimsSpec;
import org.paasta.container.platform.web.user.persistentVolumeClaims.support.PersistentVolumeClaimsStatus;

import java.util.Map;

/**
 * PersistentVolumeClaims Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@Data
public class PersistentVolumeClaims {
    private String resultCode;
    private String nextActionUrl;

    private CommonMetaData metadata;
    private PersistentVolumeClaimsSpec spec;
    private PersistentVolumeClaimsStatus status;

    private Map<String, Object> source;
    private String sourceTypeYaml;
}
