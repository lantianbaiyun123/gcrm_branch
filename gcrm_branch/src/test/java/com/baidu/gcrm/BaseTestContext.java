package com.baidu.gcrm;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-applicationContext-test.xml"})
@TransactionConfiguration(defaultRollback = true)
public class BaseTestContext extends AbstractTransactionalJUnit4SpringContextTests {
	protected @Autowired EntityManagerFactory entityFactory;
	protected @Autowired DataSource dataSource;
	protected Logger logger = LoggerFactory.getLogger(getClass());
}
