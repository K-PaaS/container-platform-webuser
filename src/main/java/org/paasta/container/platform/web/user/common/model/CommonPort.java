package org.paasta.container.platform.web.user.common.model;

import lombok.Data;

/**
 * Common Ports Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.26
 * */
@Data
public class CommonPort {
    private String name;
    private String port;
    private String protocol;
}
