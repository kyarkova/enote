package com.enote.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.enote.repos.impl", "com.enote.repos.utils"})
public class AppConfig {

}
