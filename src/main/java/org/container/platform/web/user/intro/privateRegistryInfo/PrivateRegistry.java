package org.container.platform.web.user.intro.privateRegistryInfo;

import lombok.Data;

/**
 * Private Registry 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2020.12.01
 */
@Data
public class PrivateRegistry {

    private String resultCode;
    private String resultMessage;
    private String nextActionUrl;


    private long seq;
    private String repositoryUrl;
    private String repositoryName;
    private String imageName;
    private String imageVersion;

}
