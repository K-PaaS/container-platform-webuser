package org.container.platform.web.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ContainerPlatformWebuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerPlatformWebuserApplication.class, args);
    }

}
