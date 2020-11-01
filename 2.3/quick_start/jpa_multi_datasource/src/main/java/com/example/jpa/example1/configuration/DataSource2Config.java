package com.example.jpa.example1.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement//开启事务
//利用EnableJpaRepositories配置哪些包下面的Repositories采用哪个EntityManagerFactory和哪个trannsactionManager
@EnableJpaRepositories(
        basePackages = {"com.example.jpa.example1.db2"},//数据源2的repository的包路径
        entityManagerFactoryRef = "db2EntityManagerFactory",//改变数据源2的EntityManagerFactory的默认值，改为db2EntityManagerFactory
        transactionManagerRef = "db2TransactionManager"//改变数据源2的transactionManager的默认值，改为db2TransactionManager
)
public class DataSource2Config {
    /**
     * 指定数据源2的dataSource配置
     *
     * @return
     */
    @Bean(name = "db2DataSourceProperties")
    @ConfigurationProperties("spring.datasource2") //数据源2的db配置前缀采用spring.datasource2
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 可以选择不同的数据源，这里老师举例用HikariDataSource，创建数据源2
     *
     * @param db2DataSourceProperties
     * @return
     */
    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.db2") //配置数据源2的hikari的配置key的前缀
    public HikariDataSource dataSource(@Qualifier("db2DataSourceProperties") DataSourceProperties db2DataSourceProperties) {
        HikariDataSource dataSource = db2DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(db2DataSourceProperties.getName())) {
            dataSource.setPoolName(db2DataSourceProperties.getName());
        }
        return dataSource;
    }

    /**
     * 配置数据源2的entityManagerFactory命名为db2EntityManagerFactory，用来对实体进行一些操作
     *
     * @param builder
     * @param db2DataSource entityManager依赖db2DataSource
     * @return
     */
    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("db2DataSource") DataSource db2DataSource) {
        return builder.dataSource(db2DataSource)
				.packages("com.example.jpa.example1.db2") //数据2的实体所在的路径
				.persistenceUnit("db2")// persistenceUnit的名字采用db2
				.build();
    }

    /**
     * 配置数据2的事务管理者，命名为db2TransactionManager依赖db2EntityManagerFactory
     *
     * @param db2EntityManagerFactory
     * @return
     */
    @Bean(name = "db2TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2EntityManagerFactory) {
        return new JpaTransactionManager(db2EntityManagerFactory);
    }

}
