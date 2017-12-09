package com.enote.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.enote.config", "com.enote.repo"})
public class AppConfig {

}
