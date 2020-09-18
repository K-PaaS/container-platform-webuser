package org.paasta.container.platform.web.user.persistentVolumeClaims;

import lombok.Data;

import java.util.List;

/**
 * PersistentVolumeClaims List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.16
 */
@Data
public class PersistentVolumeClaimsList {
    private String resultCode;
    private List<PersistentVolumeClaims> items;
}
