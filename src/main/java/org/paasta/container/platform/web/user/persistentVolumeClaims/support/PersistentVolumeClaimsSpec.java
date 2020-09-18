package org.paasta.container.platform.web.user.persistentVolumeClaims.support;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonLabelSelector;
import org.paasta.container.platform.web.user.common.model.CommonResourceRequirement;
import org.paasta.container.platform.web.user.common.model.CommonTypedLocalObjectReference;

import java.util.List;

/**
 * PersistentVolumeClaimsSpec Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@Data
public class PersistentVolumeClaimsSpec {
    private List<String> accessModes;
    private String volumeName;
    private String storageClassName;
    private String volumeMode;
    private CommonTypedLocalObjectReference dataSource;
    private CommonResourceRequirement resources;
    private CommonLabelSelector selector;
}
