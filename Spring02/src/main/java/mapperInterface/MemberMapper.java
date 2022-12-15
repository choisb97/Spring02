package mapperInterface;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import criTest.SearchCriteria;
import vo.MemberVO;

public interface MemberMapper {
	
	// ** axRSDetail
	// => @Param : mapper에서 #{} 적용 가능
	MemberVO rsDetail(@PathVariable("id") String id, @PathVariable("jno") Integer jno);
	
	// ** JUnit Test
	int totalCount();
	
	// ** @으로 SQL 구문 처리 Test (~Mapper.xml 필요없음)
	@Select("select * from member where id != 'admin'")
	List<MemberVO> selectList2();
	
	
	// ** Member Check List
	List<MemberVO> checkList(MemberVO vo);

	// ** SearchCriteria Page List
	List<MemberVO> searchList(SearchCriteria cri);
	
	int searchCount(SearchCriteria cri);

	// ** selectList
	List<MemberVO> selectList();
		
	// ** selectOne ( 한 명의 정보 보기 )
	MemberVO selectOne(MemberVO vo);
	
	// ** Insert
	// Join -> Insert
	int insert(MemberVO vo);

	// ** Update
	int update(MemberVO vo);

	// ** Delete
	int delete(MemberVO vo);
	
} // interface
