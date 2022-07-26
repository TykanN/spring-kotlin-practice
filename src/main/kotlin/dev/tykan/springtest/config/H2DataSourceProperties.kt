package dev.tykan.springtest.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.datasource.h2")
class H2DataSourceProperties : DataSourceProperties() {
}