package criTest; // DTO(Data Transfer Object)의 특성을 가지고 있음

import lombok.Getter;
import lombok.ToString;

/*
 < ** Paging ** >
 => 1 Page당 출력할 Row 개수 : 5개
 => 현재 출력 Page
 => 출력할 List(Rows)
 	 -> Start Row 순서(몇 번부터인지) : 계산 필요
 ==> Criteria Class에 정의
 	 
 	 
 => 1 Page당 출력 PageNo 개수 : 3개
	 -> Page Block의 First Page No.
	 -> Page Block의 Last Page No.
	 -> 전, 후 아이콘 표시 여부
	 -> 전체의 First Page로 이동 표시 여부
	 -> 전체의 Last Page로 이동 표시 여부
 ==> PageMaker Class로 처리
 
 
 < ** Criteria : (판단이나 결정을 위한) 기준 ** >
 => 출력할 Row를 select 하기 위한 클래스
 => 이 것을 위한 기준 값들을 관리
 
 < ** PageMaker : UI에 필요한 정보 완성 ** >
*/

// Getter, toString / Setter는 검증이 필요하기 때문에 직접 생성
@Getter
@ToString
public class Criteria {

	private int rowsPerPage; // 1 Page당 출력할 Row 개수
	private int currPage; // 현재 출력(요청 받은) Page
	private int sno; // Start RowNo.
	private int eno; // End RowNo. => MySQL은 없어도 됨
	// int의 default = 0
	
	
	 // 1) 생성자를 이용해서 기본값 초기화 
	public Criteria() {
		this.rowsPerPage = 5;
		this.currPage = 1;
	} // 초깃값
	
	
	// 2) setCurrPage : 요청받은(출력할) PageNo set
	public void setCurrPage(int currPage) {
		if (currPage > 1)
			this.currPage = currPage;
		else
			this.currPage = 1;
	} // setCurrPage
	
	
	// 3) setRowsPerPage
	// => 1페이지당 출력할 Row(Record, 튜플) 개수 확인
	// => 제한조건 점검 (50개까지만 허용)
	// => 당장은 사용하지 않지만 사용 가능하도록 작성
	public void setRowsPerPage(int rowsPerPage) {
		if (rowsPerPage > 5 && rowsPerPage <= 50)
			this.rowsPerPage = rowsPerPage;
		else
			this.rowsPerPage = 5; // 초깃값
	} // setRowsPerPage
	
	
	// 4) setSnoEno : sno, eno 계산
	// => currPage, rowsPerPage를 이용해 계산
	// => Oracle 검색조건 : Between(sno, eno) -> sno부터 eno까지
	// => MySQL 검색조건 : limit sno, n -> sno 제외하고 그 다음부터 n개
	public void setSnoEno() {
		if (this.sno < 1) // sno가 1보다 작은 경우(맨 처음 초깃값(0)일 때)
			this.sno = 1; // sno에 1을 넣어준다
		
		// this.sno = (this.currPage - 1) * this.rowsPerPage + 1; => Oracle 적합
		this.sno = (this.currPage - 1) * this.rowsPerPage; // MySQL 적합
		this.eno = this.sno + this.rowsPerPage - 1; // MySQL에서는 필요 없음.
	} // setSnoEno
	
} // class
