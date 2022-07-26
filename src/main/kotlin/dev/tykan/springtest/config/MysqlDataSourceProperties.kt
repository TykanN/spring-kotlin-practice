package dev.tykan.springtest.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "spring.datasource.mysql")
class MysqlDataSourceProperties : DataSourceProperties()