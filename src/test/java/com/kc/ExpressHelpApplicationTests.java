package com.kc;


import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kc.eh.dao.SyslogDao;
import com.kc.test.DefaultCache;
@RunWith(SpringRunner.class)  //@RunWith() 运行器
//SpringRunner 继承了SpringJUnit4ClassRunner，没有扩展任何功能；使用前者，名字简短而已
@SpringBootTest
class ExpressHelpApplicationTests {

	@Autowired
	private ApplicationContext act;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SyslogDao syslogdao;
	@Test
	void contextLoads() {
	}
	@Test
	public void testCache() {
		DefaultCache cache = act.getBean("defaultCache",DefaultCache.class);
		System.out.println(cache);
	}
	
	@Test
	public void testDataSource() throws Exception {
		//测试数据库连接池 使用默认的HikarCP
		System.out.println(dataSource.getConnection());
	}
	
	@Test
	public void testDeleteObject() {
		System.out.println(syslogdao.deleteObject(1));
	}
	
	@Test
	public void testFindObject() {
		Map<String, Object> resultMap =  syslogdao.findObject(1);
		System.out.println(resultMap);
	}
}
