package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.BoardVO;

/*
< DAO(Data Access Object) >
- SQL구문 처리
- CRUD 구현
  C : Create -> insert
  R : Read -> selectList, selectOne
  U : Update -> update
  D : Delete -> delete
*/

//@Repository // Mybatis Test 위해 주석
public class BoardDAO {

	// ** 전역변수 정의 (main에서 부를 필요가 없기 때문에 static 사용 X)
	Connection cn = DBConnection.getConnection();
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	
//----------------------------------------------------------------------------------------
	
	// ** selectList
	public List<BoardVO> selectList() {
		
		sql = "select seq, id, title, regdate, cnt, root, step, indent from board order by root desc, step asc";
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			
			// => 출력자료가 있는지 확인
			//    출력자료가 존재하면 요청한 객체로 전달
			if (rs.next()) {
				// => 출력자료를 1 row 씩 -> vo에 set -> list에 add
				do {
					BoardVO vo = new BoardVO();
					
					vo.setSeq(rs.getInt(1));
					vo.setId(rs.getString(2));
					vo.setTitle(rs.getString(3));
					vo.setRegdate(rs.getString(4));
					vo.setCnt(rs.getInt(5));
					vo.setRoot(rs.getInt(6));
					vo.setStep(rs.getInt(7));
					vo.setIndent(rs.getInt(8));
					
					list.add(vo);
					
				} while(rs.next()); // rs에 next()가 있을 때 까지, 없으면 반복문 종료
				
				return list; // do ~ while 반복문 끝나면 list return
				
			} else {
				System.out.println("< B selectList : 출력자료가 한 건도 없음 >");
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("\n** B selectList Exception => " + e.toString());
			return null;
		} // t~c
		
		// return null; => t~c 밖에 사용해서 else이거나 catch로 넘어가는 경우 return null 한 번에 사용 가능
		
	} // selectList
	
//----------------------------------------------------------------------------------------------------------------------

	// ** selectOne ( 한 명의 글 자료 보기 )
	public BoardVO selectOne(BoardVO vo) {
		
		sql = "select * from board where seq = ?"; // title은 유일성 보장X, 글번호(seq)는 유일함
		
		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq()); // sql의 ?(바인딩 변수) 처리
			rs = pst.executeQuery();
			
			// => 결과 확인
			if (rs.next()) {
				
				vo.setSeq(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setRoot(rs.getInt(7));
				vo.setStep(rs.getInt(8));
				vo.setIndent(rs.getInt(9));

				return vo;
				
			} else {
				System.out.println("** seq에 해당하는 글 자료는 존재하지 않습니다. **");
				return null;
			} // if
			
		} catch (Exception e) {
			System.out.println("\n** B selectOne Exception => " + e.toString());
			return null;
		} // t~c
		
	} // selectOne

//----------------------------------------------------------------------------------------------------------------------
	
	// ** Insert
	// => 새 글 등록 -> Insert
	// => 댓글 등록 추가 후 원글 : root -> seq와 동일, step 과 indent는 0 
	
	// => MySQL : last_insert_id 함수는 테이블의 마지막 auto_increment 값을 리턴
	//    - Last_Insert_ID()는 동일 세션에서 제대로 동작하지만 세션이 다를 경우 0을 return함.
	//      그러므로 JDBC에서는 적용 불가능.
	
	// => MySQL : subQuery -> JDBC 적용 됨.
	//    - select IFNULL(max(seq), 0) + 1 from board : JDBC 적용 안 됨
	//    - (select * from (select IFNULL(max(seq), 0) + 1 from board) as temp)
	
	// => MySQL : auto_increment, root는 subQuery_IFNULL 적용 시
	//    - 마지막 글 삭제 후 바로 입력했을 때 두 개의 값이 다를 수 있기 때문에
	//      seq, root 모두 subQuery_IFNULL을 적용하는 것이 바람직 함.
	//    - 답글 등록 후 원글 입력 시 root 값 처리를 위해 
	public int insert(BoardVO vo) {
		
		// sql = "insert into board(id, title, content, root) "
		// 		+ "values (?, ?, ?, (select * from (select IFNULL(max(seq), 0) + 1 from board) as temp))";
		sql = "insert into board (seq, id, title, content, root) "
				+ "values((select * from (select IFNULL(max(seq), 0) + 1 from board) as temp),"
				+ "?, ?, ?, (select * from (select IFNULL(max(seq), 0) + 1 from board) as temp))";
		
		try {
			pst = cn.prepareStatement(sql);
			
			pst.setString(1, vo.getId());
			pst.setString(2, vo.getTitle());
			pst.setString(3, vo.getContent());
			
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row의 개수(int) return
			
		} catch (Exception e) {
			System.out.println("\n** Board_Insert Exception => " + e.toString());
			return 0;
		} // t ~ c
		
	} // insert
	
//----------------------------------------------------------------------------------------------------------------------

	// ** Update
	// => 글 수정 : title과 content 컬럼만 수정가능
	public int update(BoardVO vo) {

		sql = "update board set title = ?, content = ? where seq = ?";

		try {
			pst = cn.prepareStatement(sql);

			pst.setString(1, vo.getTitle());
			pst.setString(2, vo.getContent());
			pst.setInt(3, vo.getSeq());

			return pst.executeUpdate();
			// executeUpdate() => 처리된 row의 개수(int) return

		} catch (Exception e) {
			System.out.println("\n** Board_Update Exception => " + e.toString());
			return 0;
		} // t ~ c

	} // update
	
//----------------------------------------------------------------------------------------------------------------------

	// ** Delete
	// => 답글 추가후
	//    - 원글 삭제 시 : 하위글 모두 삭제
	//    - 댓글 삭제 시 : 해당 댓글만 삭제
	// => 이를 위해서는 vo에 root 값이 담겨있어야 함.
	public int delete(BoardVO vo) {
		
		// 원글삭제 or 댓글 삭제 확인
		if (vo.getSeq() == vo.getRoot()) {
			// 원글 -> 동일 root 모두 삭제
			// 		   원글은 seq와 root가 동일하므로 where의 비교 컬럼만 root이고,
			//         ?는 seq로 가능.
			sql = "delete from board where root = ?";
			
		} else { // 댓글 -> 해당하는 seq만 삭제
			sql = "delete from board where seq = ?";
		}
		
		try {
			pst = cn.prepareStatement(sql);
			
			pst.setInt(1, vo.getSeq());
			
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row의 개수(int) return
			
		} catch (Exception e) {
			System.out.println("\n** Board_Delete Exception => " + e.toString());
			return 0;
		} // t ~ c
		
	} // delete
	
//----------------------------------------------------------------------------------------------------------------------

		// ** 조회수 증가
		public int countUp(BoardVO vo) {

			sql = "update board set cnt = cnt + 1 where seq = ?";

			try {
				pst = cn.prepareStatement(sql);

				pst.setInt(1, vo.getSeq());

				return pst.executeUpdate();
				// executeUpdate() => 처리된 row의 개수(int) return

			} catch (Exception e) {
				System.out.println("\n** Board_countUp Exception => " + e.toString());
				return 0;
			} // t ~ c

		} // countUp
	
//----------------------------------------------------------------------------------------------------------------------

		// ** Reply Insert
		// => 댓글 달기 -> rinsert
		// => 댓글 등록 시에는 stepUpdate가 필요함.
		//    댓글 입력 성공 후 stepUpdate 실행
		//    -> 현재 입력된 댓글의 step 값은 변경되지 않도록 sql구문의 조건을 주의해야 함
		public int stepUpdate(BoardVO vo) {
			
			sql = "update board set step = step + 1 where root = ? and step >= ?"
					+ " and seq <> (select * from (select max(seq) from board) as temp)";

			try {
				pst = cn.prepareStatement(sql);

				pst.setInt(1, vo.getRoot());
				pst.setInt(2, vo.getStep());

				return pst.executeUpdate();
				// executeUpdate() => 처리된 row의 개수(int) return

			} catch (Exception e) {
				System.out.println("\n** Board_stepUpdate Exception => " + e.toString());
				return 0;
			} // t ~ c
			
		} // stepUpdate
		
	//------------------------------------------------------------------------------------------------
		
		public int rinsert(BoardVO vo) {
			
			sql = "insert into board(seq, id, title, content, root, step, indent) "
					+ "values((select * from (select IFNULL(max(seq), 0) + 1 from board) as temp), "
					+ "?, ?, ?, ?, ?, ?)";
			
			int result = 0;
			
			try {
				pst = cn.prepareStatement(sql);
				
				pst.setString(1, vo.getId());
				pst.setString(2, vo.getTitle());
				pst.setString(3, vo.getContent());
				pst.setInt(4, vo.getRoot());
				pst.setInt(5, vo.getStep());
				pst.setInt(6, vo.getIndent());
				
				result = pst.executeUpdate();
				// => executeUpdate() => 처리된 row의 개수(int) return
				// => 댓글 입력 성공 후 stepUpdate
				//    자신의 step 값은 변경되지 않도록 stepUpdate sql 구문의 조건 주의!
				
				if (result > 0) {        // 댓글 몇 개인지 출력해보기
					System.out.println("** stepUpdate Count => " + stepUpdate(vo));
				} // if
				
			} catch (Exception e) {
				System.out.println("\n** Board_Reply Insert Exception => " + e.toString());
			} // t ~ c
			
			return result;
			
		} // rinsert
		
} // class
