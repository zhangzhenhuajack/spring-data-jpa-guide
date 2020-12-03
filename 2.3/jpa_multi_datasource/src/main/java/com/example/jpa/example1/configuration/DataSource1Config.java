package com.example.jpa.example1.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
		entityManagerFactoryRef = "db1EntityManagerFactory",//改变数据源1的EntityManagerFactory的默认值，改为db1EntityManagerFactory
		transactionManagerRef = "db1TransactionManager",//改变数据源1的transactionManager的默认值，改为db1TransactionManager

		basePackages = {"com.example.jpa.example1.db1"}//数据源1的repository的包路径
		)
public class DataSource1Config {
	/**
	 * 指定数据源1的dataSource配置
	 * @return
	 */
	@Primary
	@Bean(name = "db1DataSourceProperties")
	@ConfigurationProperties("spring.datasource1") //数据源1的db配置前缀采用spring.datasource1
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	/**
	 * 可以选择不同的数据源，这里老师举例用HikariDataSource，创建数据源1
	 * @param db1DataSourceProperties
	 * @return
	 */
	@Bean(name = "db1DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.db1") //配置数据源1的hikari的配置key的前缀
	@Primary
	public HikariDataSource dataSource(@Qualifier("db1DataSourceProperties") DataSourceProperties db1DataSourceProperties) {
		HikariDataSource dataSource = db1DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		if (StringUtils.hasText(db1DataSourceProperties.getName())) {
			dataSource.setPoolName(db1DataSourceProperties.getName());
		}
		return dataSource;
	}

	/**
	 * 配置数据源1的entityManagerFactory命名为db1EntityManagerFactory，用来对实体进行一些操作
	 * @param entityManagerFactoryBuilder
	 * @param db1DataSource entityManager依赖db1DataSource
	 * @return
	 */
	@Primary
	@Bean(name = "db1EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("db1DataSource") DataSource db1DataSource) {
		return entityManagerFactoryBuilder.dataSource(db1DataSource)
				.packages("com.example.jpa.example1.db1") //数据1的实体所在的路径
				.persistenceUnit("db1").build();
	}

	/**
	 * 配置数据1的事务管理者，命名为db1TransactionManager依赖db1EntityManagerFactory
	 * @param db1EntityManagerFactory
	 * @return
	 */
	@Primary
	@Bean(name = "db1TransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("db1EntityManagerFactory") EntityManagerFactory db1EntityManagerFactory) {
		return new JpaTransactionManager(db1EntityManagerFactory);
	}

}
