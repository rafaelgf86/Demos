package mx.com.intx.config;

import static org.hibernate.cfg.AvailableSettings.C3P0_ACQUIRE_INCREMENT;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_SIZE;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_STATEMENTS;
import static org.hibernate.cfg.AvailableSettings.C3P0_MIN_SIZE;
import static org.hibernate.cfg.AvailableSettings.C3P0_TIMEOUT;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mx.com.intx.utils.LoggerIntx;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
@ComponentScans(value = { 
		@ComponentScan({"mx.com.intx.repository" }),
		@ComponentScan({"mx.com.intx.service"}),
		@ComponentScan({"mx.com.intx.service.mail"})
	})
public class AppConfig {
	 
	  @Autowired
	  private Environment env;
 
	  @Bean 
	  public LocalSessionFactoryBean getSessionFactory() {
		System.out.println("Connecting to DataBase...");
		LoggerIntx.printLine("Connecting to DataBase...");
	    LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	    
	    Properties props = new Properties();
	    
	    // Propiedades JDBC
	    props.put(DRIVER, env.getProperty("sql.driver"));
	    props.put(URL,    env.getProperty("sql.url"));
	    props.put(USER,   env.getProperty("sql.username"));
	    props.put(PASS,   env.getProperty("sql.password"));

	    // Propiedades Hibernate
	    props.put(SHOW_SQL,  env.getProperty("hibernate.show_sql"));
	    props.put(DIALECT,  env.getProperty("hibernate.dialect"));
	    //props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

	    // Propiedades C3P0
	    props.put(C3P0_MIN_SIZE,          env.getProperty("c3p0.min.size"));
	    props.put(C3P0_MAX_SIZE,          env.getProperty("c3p0.max.size"));
	    props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("c3p0.acquire.increment"));
	    props.put(C3P0_TIMEOUT,           env.getProperty("c3p0.timeout"));
	    props.put(C3P0_MAX_STATEMENTS,    env.getProperty("c3p0.max.statements"));

	    // Aplicar propiedades
	    factoryBean.setHibernateProperties(props);
	    
	    // Estableces clases mapeadas de Hibernate
	    factoryBean.setPackagesToScan("mx.com.intx.domain");
	    
	    System.out.println("Connected to DataBase...");
	    LoggerIntx.printLine("Connected to DataBase...");
	    return factoryBean;
	  }

	  @Bean
	  public HibernateTransactionManager getTransactionManager() {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	    transactionManager.setSessionFactory(getSessionFactory().getObject());
	    return transactionManager;
	  }
	  
	  /*** PARA MANTENER SESSIÓN***/
	  @Bean
	  public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName(env.getProperty("sql.driver"));
	    dataSource.setUrl(env.getProperty("sql.url"));
	    dataSource.setUsername(env.getProperty("sql.username"));
	    dataSource.setPassword(env.getProperty("sql.password"));
	    return dataSource;
	  }
}
