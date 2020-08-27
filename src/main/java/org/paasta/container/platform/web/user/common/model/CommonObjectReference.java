package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

/**
 * ObjectReference Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.26
 */
@Data
public class CommonObjectReference {
    private String apiVersion;
    private String fieldPath;
    private String kind;
    private String name;
    private String namespace;
    private String resourceVersion;
    private String uid;
}
