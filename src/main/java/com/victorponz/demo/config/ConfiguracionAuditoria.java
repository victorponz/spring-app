package com.victorponz.demo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.victorponz.demo.upload.StorageProperties;

@Configuration
@EnableJpaAuditing
@EnableConfigurationProperties(StorageProperties.class)
public class ConfiguracionAuditoria {
    
}
