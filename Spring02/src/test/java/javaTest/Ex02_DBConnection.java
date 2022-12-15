package javaTest;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

/*
 < ** @ 종류 >
  => @Before - @Test - @After
  => @ 적용 메서드 : non static, void로 정의해야 함. 
*/

public class Ex02_DBConnection {
	
	public static Connection getConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Test 주석 - Green Line
			//Class.forName("com.mysql.cj.jdbc.Driver1"); // Test 주석 - Red Line

			String url="jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

			System.out.println("=== JDBC Connection 성공 ===");

			return DriverManager.getConnection(url, "root", "choi0517");
			// ID, PW 확인 필수!!

		} catch (Exception e) {
			System.out.println("=== JDBC Connection 실패 => " + e.toString());

			return null;

		} // t~c

	} // getConnection
	
	//@Test // Test 주석
	public void connectionTest() {
		
		System.out.println("** Connection => " + getConnection());
		// 주석 없는 경우 Connection 성공 메시지 두 개 출력
		// => 연결 성공 : 주소제공(Not_Null) / 연결 실패 : null
		
		assertNotNull(getConnection()); // 멀쩡히 Test 시 Green Line
		
	} // connectionTest
	
	
	// 2) non static, void로 정의
	// => 그러나 아래의 코드로만 Test하면 항상 Green Line이고, console 메시지로 확인 가능.
	@Test // Test 주석
	public void getConnectionTest() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url="jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

			Connection cn = DriverManager.getConnection(url, "root", "choi0517"); // Test 주석
			//Connection cn = DriverManager.getConnection(url, "root", "jy0605"); // Test 주석
			// console은 실패로 나오지만 Green Line 확인 됨.

			System.out.println("=== JDBC Connection 성공, cn = " + cn);
			// ID, PW 확인 필수!!

		} catch (Exception e) {
			
			System.out.println("=== JDBC Connection 실패 => " + e.toString());

		} // t~c

	} // getConnectionTest

} // class
