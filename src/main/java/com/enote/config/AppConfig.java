package com.enote.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.enote.config", "com.enote.repo", "com.enote.service","com.enote.controller"})
public class AppConfig {

}
