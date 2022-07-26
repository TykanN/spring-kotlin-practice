package dev.tykan.springtest.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
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
@EnableConfigurationProperties(MysqlDataSourceProperties::class)
class MysqlDataSourceConfiguration {

    @Primary
    @Bean(name = ["mysqlDataSource"])
    fun mysqlDataSource(mysqlDataSourceProperties: MysqlDataSourceProperties): DataSource {
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
        mysqlJpaProperties["hibernate.hbm2ddl.auto"] = "create"
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
