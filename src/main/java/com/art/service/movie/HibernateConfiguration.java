package com.art.service.movie;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
public class HibernateConfiguration {

    public String pass = "12345678";
    public String user = "root";
    public String url = "jdbc:mysql://localhost/movie";
    public int port = 3306;

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setHibernateProperties(hibernateProperties());
        bean.setPackagesToScan("com.art.service.movie.tables");
        return bean;
    }

    @Bean
    public DataSource dataSource(){
        MysqlDataSource data = new MysqlDataSource();
        data.setUser(user);
        data.setPassword(pass);
        data.setUrl(url);
        data.setPort(port);
        try {
            data.setUseSSL(false);
            data.setAllowPublicKeyRetrieval(true);
            data.setServerTimezone("UTC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("hibernate.jdbc.time_zone", "UTC");
        hibernateProperties.setProperty("spring.jpa.properties.hibernate.jdbc.time_zone", "UTC");
        hibernateProperties.setProperty("hibernate.current_session_context_class"
                ,"org.springframework.orm.hibernate5.SpringSessionContext");

        return hibernateProperties;
    }
}
