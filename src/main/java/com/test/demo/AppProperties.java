package com.test.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * https://www.baeldung.com/configuration-properties-in-spring-boot
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "demo.security")
public class AppProperties {

    private String state;
    private String[] publicPaths;
    private List<PathAndRoles> roles;

    @Data
    public static class PathAndRoles {
        private String path;
        private String role;
    }

}
