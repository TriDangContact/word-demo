package com.tridang.assignment.config;/*
 * @author Tri Dang
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfiguration {
    @NotBlank
    private String urlMerriam;
    @NotBlank
    private String keyMerriam;

}
