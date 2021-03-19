package org.paasta.container.platform.web.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PaasTaContainerPlatformWebuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasTaContainerPlatformWebuserApplication.class, args);
    }

}
