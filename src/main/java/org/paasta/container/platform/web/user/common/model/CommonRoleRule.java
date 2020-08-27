package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

import java.util.List;

/**
 * Common Role Rule Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.26
 */
@Data
public class CommonRoleRule {
    private List<String> apiGroups;
    private List<String> resources;
    private List<String> verbs;
}
