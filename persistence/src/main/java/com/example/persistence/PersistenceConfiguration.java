package com.example.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * PersistenceConfiguration.
 *
 * @author Bipin Thite
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.example.persistence.repositories"})
@EntityScan("com.example.persistence.models")
public class PersistenceConfiguration {}
