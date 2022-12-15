package service;

import java.util.List;

import criTest.SearchCriteria;
import vo.MemberVO;

public interface MemberService {
	
	// ** axRSDetail
	MemberVO rsDetail(String id, Integer jno);
	
	// ** Member Check List
	List<MemberVO> checkList(MemberVO vo);
	
	// ** Criteria Page List
	List<MemberVO> searchList(SearchCriteria cri);
	
	int searchCount(SearchCriteria cri);

	// ** selectList
	List<MemberVO> selectList(); // selectList

	// ** selectOne ( 한 명의 정보 보기 )
	MemberVO selectOne(MemberVO vo); // selectOne

	// Insert
	int insert(MemberVO vo); // insert

	// Update
	int update(MemberVO vo); // update

	// Delete
	int delete(MemberVO vo); // delete

} // i_MemberService