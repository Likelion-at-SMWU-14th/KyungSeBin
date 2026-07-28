package com.likelion.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={
        "com.likelion.proxy",
        "com.likelion.service",
        "com.likelion.repository"
})
public class ProjectConfig {
}
