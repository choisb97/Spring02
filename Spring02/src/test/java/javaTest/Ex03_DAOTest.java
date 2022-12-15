package javaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import util.MemberDAO;
import vo.MemberVO;

/*
 < DAO(Java) 테스트 시나리오 > 
 Test 1) DAO 테스트 시나리오
 => Detail 정확하게 구현했는지 Test
 -> 정확한 id를 넣었을 때는 NotNull : green  
 -> 없는 id를 넣었을 때는 Null : red(오류)

 Test 2) 테스트 시나리오
 => Insert 구현 정확성 Test 
 -> 입력 가능 데이터 적용 시 성공, 1 return : green
 -> 입력 불가능 데이터 적용 시 실패, 0 return : red 
*/

public class Ex03_DAOTest {
	
	MemberDAO dao = new MemberDAO();
	MemberVO vo = new MemberVO();
	
	// Test 1) Detail 정확하게 구현했는지 Test   
	// => 정확한 id는 NotNull : green, 없는 id는 Null : red(오류)
	//@Test // Test 주석
	public void detailTest() {
		
		//vo.setId("CHOI"); // NotNull : Green / Test 주석
		vo.setId("teacher"); // Null : Red (없는 ID) / Test 주석
		assertNotNull(dao.selectOne(vo));
		System.out.println("** vo => " + vo);
		
	} // detailTest
	
	// Test 2) Insert Test
	@Test // Test 주석
	public void insertTest() {
		
		vo.setId("unitTest");
		vo.setPassword("12345!");
		vo.setName("가나다");
		vo.setInfo("JUnit Test");
		vo.setJno(5);
		vo.setBirthday("1990-10-14");
		vo.setAge(20);
		vo.setPoint(1000);
		
		// => 성공 : 1, 실패 : 0
		assertEquals(dao.insert(vo), 1); // True_Green : 입력 성공
		
	} // insertTest

} // class
