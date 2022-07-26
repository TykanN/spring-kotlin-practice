package dev.tykan.springtest.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
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
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager",
    basePackages = ["dev.tykan.springtest.repository.h2"]
)
@EnableConfigurationProperties(H2DataSourceProperties::class)
class H2DataSourceConfiguration {

    @Bean(name = ["h2DataSource"])
    fun h2DataSource(h2DataSourceProperties: H2DataSourceProperties): DataSource {
        return h2DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["h2EntityManagerFactory"])
    fun h2EntityManagerFactory(
        h2EntityManagerFactoryBuilder: EntityManagerFactoryBuilder,
        @Qualifier("h2DataSource") h2DataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean {
        val h2JpaProperties: MutableMap<String, String?> = HashMap()
        h2JpaProperties["hibernate.dialect"] = "org.hibernate.dialect.H2Dialect"
        h2JpaProperties["hibernate.hbm2ddl.auto"] = "create"
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