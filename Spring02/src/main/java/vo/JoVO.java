package vo;

import lombok.Data;

@Data
//Jo List의 Chief 옆에 조장이름(member_name)추가하기 위해 MemberVO 상속
public class JoVO extends MemberVO { 
	
	private int jno;
	private String chief;
	private String jname;
	private String note;

} // class
