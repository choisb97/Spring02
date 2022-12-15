package javaTest;

import static org.junit.Assert.*;
//import static org.junit.Assert.assertEquals;

import org.junit.Test;


// ** Book class

// => 멤버필드 3개 정의, 멤버필드 3개를 초기화하는 생성자 만들기
// => 접근 범위를 default

class Book {
	
	String author;
	String title;
	int price;
	
	// Default => public, privat 없이 사용
	Book(String author, String title, int price) {
		
		this.author = author;
		this.title = title;
		this.price = price;
		
	} // Book 초기화 생성자
	
	public boolean isBook(boolean b) {
		return b;
	} // isBook
	
} // Book

/*
 < ** @ 종류 >
  => @Before - @Test - @After
  => @ 적용 메서드 : non static, void로 정의해야 함.

  ** org.junit.Assert가 지원하는 다양한 Test용 Method 
  1) assertEquals(a,b) : a와 b의 값(Value)이 같은지 확인
  2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소)을 확인
  3) assertTrue(a) : a가 참인지 확인
  4) assertNotNull(a) : a객체가 Null이 아님을 확인
  5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인
*/

// ** 실행 : 우클릭 -> Run As -> JUnit Test
public class Ex01_BookTest {
	
	// => @Test : Adds the JUnit 4 library to the build path.
	
	// 1) assertEquals(a,b) : a와 b의 값(Value)이 같은지 확인
	//@Test // Test 주석
	public void equalsTest() {
		
		Book b1 = new Book("엄미현", "Java 정복", 9900);
		
		//assertEquals(b1.author, "엄미현"); // true : Green Line
		assertEquals(b1.author, "홍길동"); // false : Red Line
		
	} // equalsTest
	
//----------------------------------------------------------------------------------------
	
	// 2) assertSame(a,b) : 객체 a와 b가 같은 객체임(같은 주소 = 인스턴스)을 확인
	@Test // Test 주석
	public void sameTest() {
		
		Book b1 = new Book("엄미현", "Java 정복", 9900);
		Book b2 = new Book("엄미현", "Java 정복", 9900);
		Book b3 = new Book("홍길동", "아부지", 15000);
		
		b3 = b1; // true ??
		assertSame(b1, b3); // Green Line
		//assertSame(b1, b2); // Red Line
		
	} // sameTest
	
//----------------------------------------------------------------------------------------
	
	// 3) assertTrue(a) : a가 참인지 확인
	//@Test // Test 주석
	public void trueTest() {
		Book b1 = new Book("홍길동", "아부지", 15000);
		assertTrue(b1.isBook(false)); // Red Line
	} // trueTest
	
//----------------------------------------------------------------------------------------
	
	// 4) assertNotNull(a) : a객체가 Null이 아님을 확인
	//@Test // Test 주석
	public void notNullTest() {
		
		Book b1 = new Book("홍길동", "아부지", 15000);
		
		System.out.println("** b1 = " + b1);
		
		assertNotNull(b1); // Green Line
		
	} // notNullTest
	
//----------------------------------------------------------------------------------------
	
	// 5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인(배열의 동일성)
	//@Test // Test 주석
	public void arrayEqualsTest() {
		
		String[] a1 = new String[] {"가", "나", "다"};
		String[] a2 = new String[] {"가", "나", "다"};
		String[] a3 = new String[] {"가", "다", "나"};
		String[] a4 = new String[] {"가", "다", "라"};
		
		// 5-1) 두 배열의 순서, 값 모두 동일 (a1, a2)
		assertArrayEquals(a1, a2); // Green Line
		
		// 5-2) 두 배열의 순서는 다르고, 값 모두 동일 (a1, a3)
		//assertArrayEquals(a1, a3); // Red Line
		
		// 5-3) 모두 다른 경우 (a1, a4)
		//assertArrayEquals(a1, a4); // Red Line
		
	} // arrayEqualsTest

} // class
