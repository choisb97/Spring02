package springTest;

import static org.junit.Assert.assertNotNull;


import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 < ** DataSource Test >
 => pom.xml에 <dependency> spring-jdbc 추가
 => 인터페이스 DataSource 구현객체 DriverManagerDataSource를 bean 등록하고 (servlet-context.xml 또는 root-context.xml에)
 => DB Connection 생성 확인
*/

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
														// ** -> 없는 것 포함 모든 폴더
public class Ex02_DataSourceTest {

	@Autowired
	DataSource dataSource;
	/* < ** 계층도 확인 (Ctrl + T)
	   => DataSource (interface)
          -> AbstractDataSource
          -> AbstractDriverBasedDataSource
          -> DriverManagerDataSource 
              org.springframework.jdbc.datasource.DriverManagerDataSource */
	
	@Test
	public void connectionTest() {
		
		try {
			// 1) 항상 Green Line, 실패해도 catch에서 처리.
			//Connection cn = dataSource.getConnection(); // Test 주석
			//System.out.println("=== JDBC Connection 성공, cn = " + cn); // Test 주석
			
			// 2) 비교
			// => Null -> Connection 실패 메시지 출력, Green Line
			assertNotNull(dataSource.getConnection());
			System.out.println("=== JDBC Connection 성공, cn => " + dataSource.getConnection());
			
		} catch (Exception e) {
			System.out.println("** Connection 실패 => " + e.toString());
		} // t~c
		
	} // connectionTest
	
} // class
