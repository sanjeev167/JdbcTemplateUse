/**
 * 
 */
package com.nse.db.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author sanjeevkumar 
 * 12-Sep-2024
 * 11:43:00 am 
 * Objective:
 */

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = {
		JpaConstants.PKG_REPO }, entityManagerFactoryRef = "jpaEntityManager", transactionManagerRef = "jpaTransactionManager")

public class JpaPersistenceConfig {

	@Autowired
	private Environment env;// Contains Properties loaded by @PropertySources.
							// This can be loaded using @Value; but for loading it from a specific file, we use this approach

	/**
	 * Author: sanjeevkumar <p>
	 * Date 12-Sep-2024<p>
	 * Objective : This bean has been configured for creating a data-source for
	 * apiService. <p>
	 * Using a data-source, we could get a connection for doing database related
	 * operation.
	 *
	 */
	@Bean(name = "jpaDataSource")
	public DataSource jpaDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		return dataSource;

	}// End of jpaDataSource

	/**
	 * Author: sanjeevkumar
	 * <p>
	 * Date 12-Sep-2024
	 * <p>
	 * Objective: This bean has been configured for creating an jpaEntityManager for
	 * the apiService. This will manage all those entities which have been defined
	 * in JPA_PKG_ENTITIES_ARRAY under JPA_UNIT. An entity-manager is used for doing
	 * operation through entities.
	 *
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean jpaEntityManager() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(jpaDataSource());
		// Scan Entities in Package:
		em.setPackagesToScan(JpaConstants.JPA_PKG_ENTITIES_ARRAY);
		em.setPersistenceUnitName(JpaConstants.JPA_UNIT);// Important !!

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		// JPA & Hibernate
		final HashMap<String, Object> jpaProperties = new HashMap<String, Object>();

		// Configures the used database dialect. This allows Hibernate to create SQL
		// that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

		// Solved Error: PostGres createClob() is not yet implemented.
		// PostGres Only:
		jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

		// jpaProperties.put("hibernate.hbm2ddl.auto",
		// env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		em.setJpaPropertyMap(jpaProperties);
		em.afterPropertiesSet();

		return em;
	}// End of jpaEntityManager

	/**
	 * Author: sanjeevkumar <p>
	 * Date 12-Sep-2024 <p>
	 * Objective : This method will be used for creating an jpaTransactionManager
	 * for jpaEntityManager. It is used for managing a transaction.
	 * <p>
	 *
	 */

	@Bean(name = "jpaTransactionManager")
	public PlatformTransactionManager jpaTransactionManager() {

		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(jpaEntityManager().getObject());
		return transactionManager;
	}// End of jpaTransactionManager

}// End of jpaPersistenceConfig
