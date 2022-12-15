package vo;

import lombok.Data;

/*
 < VO 작성 >
 - private 변수 : Table의 컬럼명과 동일
 - setter, getter, toString() 
*/

@Data
public class BoardVO {
	
	private int seq;
	private String id;
	private String title;
	private String content;
	private String regdate; // 날짜는 Java에서 String으로 넣는 것이 편하다.
	private int cnt;
	private int root;
	private int step;
	private int indent;
	
	//private String[] check; => SearchCriteria로 옮김
} // class
