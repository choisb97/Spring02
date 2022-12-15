package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.MemberVO;

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
public class MemberDAO {

	// ** 전역변수 정의 (main에서 부를 필요가 없기 때문에 static 사용 X)
	Connection cn = DBConnection.getConnection();
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	
//----------------------------------------------------------------------------------------
	
	// ** selectList
	public List<MemberVO> selectList() {
		
		sql = "select * from member";
		
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			
			// => 출력자료가 있는지 확인
			//    출력자료가 존재하면 요청한 객체로 전달
			if (rs.next()) {
				// => 출력자료를 1 row 씩 -> vo에 set -> list에 add
				do {
					MemberVO vo = new MemberVO();
					
					vo.setId(rs.getString(1));
					vo.setPassword(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setInfo(rs.getString(4));
					vo.setBirthday(rs.getString(5));
					vo.setJno(rs.getInt(6));
					vo.setAge(rs.getInt(7));
					vo.setPoint(rs.getDouble(8));
					
					list.add(vo);
					
				} while(rs.next()); // rs에 next()가 있을 때 까지, 없으면 반복문 종료
				
				return list; // do ~ while 반복문 끝나면 list return
				
			} else {
				System.out.println("< selectList : 출력자료가 한 건도 없음 >");
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("\n** selectList Exception => " + e.toString());
			return null;
		} // t~c
		
		// return null; => t~c 밖에 사용해서 else이거나 catch로 넘어가는 경우 return null 한 번에 사용 가능
		
	} // selectList
	
//----------------------------------------------------------------------------------------------------------------------

	// ** selectOne ( 한 명의 정보 보기 )
	public MemberVO selectOne(MemberVO vo) {
		
		sql = "select * from member where id = ?";
		
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getId()); // sql의 ?(바인딩 변수) 처리
			rs = pst.executeQuery();
			
			// => 결과 확인
			if (rs.next()) {
				
				vo.setId(rs.getString(1));
				vo.setPassword(rs.getString(2));
				vo.setName(rs.getString("name"));
				vo.setInfo(rs.getString(4));
				vo.setBirthday(rs.getString(5));
				vo.setJno(rs.getInt(6));
				vo.setAge(rs.getInt("age"));
				vo.setPoint(rs.getDouble(8));

				return vo;
				
			} else {
				System.out.println("** ID에 해당하는 Member가 존재하지 않습니다. **");
				return null;
			} // if
			
		} catch (Exception e) {
			System.out.println("\n** selectOne Exception => " + e.toString());
			return null;
		} // t~c
		
	} // selectOne

//----------------------------------------------------------------------------------------------------------------------
	
	// ** Insert
	// Join => Insert
	public int insert(MemberVO vo) {
		
		sql = "insert into member values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pst = cn.prepareStatement(sql);
			
			pst.setString(1, vo.getId());
			pst.setString(2, vo.getPassword());
			pst.setString(3, vo.getName());
			pst.setString(4, vo.getInfo());
			pst.setString(5, vo.getBirthday());
			pst.setInt(6, vo.getJno());
			pst.setInt(7, vo.getAge());
			pst.setDouble(8, vo.getPoint());
			
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row의 개수(int) return
			
		} catch (Exception e) {
			System.out.println("\n** Member_Insert Exception => " + e.toString());
			return 0;
		} // t ~ c
		
	} // insert
	
//----------------------------------------------------------------------------------------------------------------------

	// ** Update
	// => P.Key인 ID를 제외한 모든 컬럼 수정
	public int update(MemberVO vo) {

		sql = "update member set password = ?, name = ?, info = ?, birthday = ?, "
				+ "jno = ?, age = ?, point = ? where id = ?";

		try {
			pst = cn.prepareStatement(sql);

			pst.setString(1, vo.getPassword());
			pst.setString(2, vo.getName());
			pst.setString(3, vo.getInfo());
			pst.setString(4, vo.getBirthday());
			pst.setInt(5, vo.getJno());
			pst.setInt(6, vo.getAge());
			pst.setDouble(7, vo.getPoint());
			pst.setString(8, vo.getId());

			return pst.executeUpdate();
			// executeUpdate() => 처리된 row의 개수(int) return

		} catch (Exception e) {
			System.out.println("\n** Member_Update Exception => " + e.toString());
			return 0;
		} // t ~ c

	} // update
	
//----------------------------------------------------------------------------------------------------------------------

	// ** Delete
	public int delete(MemberVO vo) {
		
		sql = "delete from member where id = ?";
		
		try {
			pst = cn.prepareStatement(sql);
			
			pst.setString(1, vo.getId());
			
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row의 개수(int) return
			
		} catch (Exception e) {
			System.out.println("\n** Member_Delete Exception => " + e.toString());
			return 0;
		} // t ~ c
		
	} // delete
	
	
} // class
