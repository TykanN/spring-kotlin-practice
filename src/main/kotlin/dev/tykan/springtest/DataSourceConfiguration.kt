package dev.tykan.springtest

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager",
    basePackages = ["dev.tykan.springtest.repository"]
)
class MysqlDataSourceConfiguration {
    @Primary
    @Bean(name = ["mysqlDataSourceProperties"])
    @ConfigurationProperties("spring.datasource-mysql")
    fun mysqlDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Primary
    @Bean(name = ["mysqlDataSource"])
    @ConfigurationProperties("spring.datasource-mysql.configuration")
    fun mysqlDataSource(@Qualifier("mysqlDataSourceProperties") mysqlDataSourceProperties: DataSourceProperties): DataSource {
        return mysqlDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Primary
    @Bean(name = ["mysqlEntityManagerFactory"])
    fun mysqlEntityManagerFactory(
        mysqlEntityManagerFactoryBuilder: EntityManagerFactoryBuilder,
        @Qualifier("mysqlDataSource") mysqlDataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean {
        val mysqlJpaProperties: MutableMap<String, String> = HashMap()
        mysqlJpaProperties["hibernate.dialect"] = "org.hibernate.dialect.MySQL5Dialect"
        mysqlJpaProperties["hibernate.hbm2ddl.auto"] = "create-drop"
        return mysqlEntityManagerFactoryBuilder
            .dataSource(mysqlDataSource)
            .packages("dev.tykan.springtest.domain")
            .persistenceUnit("mysqlDataSource")
            .properties(mysqlJpaProperties)
            .build()
    }

    @Primary
    @Bean(name = ["mysqlTransactionManager"])
    fun mysqlTransactionManager(
        @Qualifier("mysqlEntityManagerFactory") mysqlEntityManagerFactory: EntityManagerFactory?
    ): PlatformTransactionManager {
        return JpaTransactionManager(mysqlEntityManagerFactory!!)
    }
}

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager",
    basePackages = ["dev.tykan.springtest.repository.h2"]
)
class H2DataSourceConfiguration {
    @Bean(name = ["h2DataSourceProperties"])
    @ConfigurationProperties("spring.datasource-h2")
    fun h2DataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["h2DataSource"])
    @ConfigurationProperties("spring.datasource-h2.configuration")
    fun h2DataSource(@Qualifier("h2DataSourceProperties") h2DataSourceProperties: DataSourceProperties): DataSource {
        return h2DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["h2EntityManagerFactory"])
    fun h2EntityManagerFactory(
        h2EntityManagerFactoryBuilder: EntityManagerFactoryBuilder,
        @Qualifier("h2DataSource") h2DataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean {
        val h2JpaProperties: MutableMap<String, String?> = HashMap()
        h2JpaProperties["hibernate.dialect"] = "org.hibernate.dialect.H2Dialect"
        h2JpaProperties["hibernate.hbm2ddl.auto"] = "create-drop"
        return h2EntityManagerFactoryBuilder
            .dataSource(h2DataSource)
            .packages("dev.tykan.springtest.domain")
            .persistenceUnit("h2DataSource")
            .properties(h2JpaProperties)
            .build()
    }

    @Bean(name = ["h2TransactionManager"])
    fun h2TransactionManager(
        @Qualifier("h2EntityManagerFactory") h2EntityManagerFactory: EntityManagerFactory?
    ): PlatformTransactionManager {
        return JpaTransactionManager(h2EntityManagerFactory!!)
    }
}