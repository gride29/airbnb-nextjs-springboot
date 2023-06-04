package com.gride29.airbnb.clone.backend;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;

@SpringBootConfiguration
@AutoConfigurationPackage
@ComponentScan("com.gride29.airbnb.clone.backend.security.services")
public class TestConfig {
}