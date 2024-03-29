package com.niit.collaborationbackend.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.niit.collaborationbackend.model.ForumCategory;
import com.niit.collaborationbackend.model.Friends;
import com.niit.collaborationbackend.model.Job;
import com.niit.collaborationbackend.model.JobApplication;
import com.niit.collaborationbackend.model.UserBlog;
import com.niit.collaborationbackend.model.UserForum;
import com.niit.collaborationbackend.model.UserForumComments;
import com.niit.collaborationbackend.model.UserProfile;


@Configuration
@ComponentScan("com.niit.collaborationbackend")
@EnableTransactionManagement
public class ApplicationConfig {

	@Bean(name="dataSource")
	public DataSource getDataSource()
	{
    	BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("SEC");       //Workspace = SEC;
		dataSource.setPassword("123");
		return dataSource;
	}
	
	private Properties getHibernateProperties()
	{
		Properties properties=new Properties();
		properties.put("hibernate.show", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBuilder sessionBuilder=new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.addAnnotatedClass(UserBlog.class);
		sessionBuilder.addAnnotatedClass(UserProfile.class);
		sessionBuilder.addAnnotatedClass(ForumCategory.class); 
		sessionBuilder.addAnnotatedClass(UserForum.class);
		sessionBuilder.addAnnotatedClass(UserForumComments.class);
		sessionBuilder.addAnnotatedClass(Friends.class);
		sessionBuilder.addAnnotatedClass(Job.class);
		sessionBuilder.addAnnotatedClass(JobApplication.class);
		return sessionBuilder.buildSessionFactory();
	}
	
	@Autowired
	@Bean(name="transcationManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager transactionManager=new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
}
