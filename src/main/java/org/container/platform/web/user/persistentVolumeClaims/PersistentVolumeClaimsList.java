package org.container.platform.web.user.persistentVolumeClaims;

import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private String resultMessage;
    private Map<String,Object> metadata;
    private Map<String, Object> itemMetaData;
    private List<PersistentVolumeClaims> items;
}
