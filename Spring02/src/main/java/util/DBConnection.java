package util;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 < DB 연결 >
 - Connection클래스가 DB 연결 및 연결정보를 관리 함.
   즉, Connection 객체를 생성해야 함. 
*/

public class DBConnection {
	/*
	 < Connection 객체 생성 >
	 - 일반적인 객체 생성이 아닌 생성에 필요한 정보를 제공하고 생성해야 함.
	 
	 Connection cn = new Connection(); -> XXXXX
	 -> 생성 메서드를 통해 생성 후 그 주소를 전달받아야 함.
	 
	 < Error Message >
	 - 드라이버명 오류 : java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver1
	 - portNo 오류 : Communications link failure
	 - DBName 오류 : Unknown database 'mydb1'
	 - 계정, PW 오류 : Access denied for user 'root1'@'localhost' (using password: YES) 
	*/
	
	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 다른 DB 사용 시 위 Driver 주소만 변경 후 사용 가능
			
	        String url="jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
	        // localhost:3306 -> 내 PC(localhost)에서 MySQL 3306 포트번호를 사용한다는 의미.
	        // => allowPublicKeyRetrieval=true : local DB open하지 않아도 connection 허용
	        
	        System.out.println("=== JDBC Connection 성공 ===");
	        
	        return DriverManager.getConnection(url, "root", "choi0517");
	        // ID, PW 확인 필수!!
			
		} catch (Exception e) {
			System.out.println("=== JDBC Connection 실패 => " + e.toString());
			
			return null;
			
		} // t~c
		
	} // getConnection
	
} // class



