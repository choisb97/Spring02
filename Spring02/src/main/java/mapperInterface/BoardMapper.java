package mapperInterface;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;

public interface BoardMapper {
	
	List<BoardVO> checkList(SearchCriteria cri);
	
	int checkCount(SearchCriteria cri);

//----------------------------------------------------
	
	// ** Criteria Page List
	// ** ver02
	List<BoardVO> searchList(SearchCriteria cri);
	
	int searchCount(SearchCriteria cri);
	
	// ** ver01
	List<BoardVO> criList(Criteria cri);
	
	int criTotalCount();
	
//----------------------------------------------------
	
	// ** selectList
	List<BoardVO> selectList();
	
	// ** selectOne ( 한 명의 글 자료 보기 )
	BoardVO selectOne(BoardVO vo);

	// ** Insert
	// => 새 글 등록 -> Insert
	int insert(BoardVO vo);

	// ** Update
	int update(BoardVO vo);

	// ** Delete
	int delete(BoardVO vo);
	
	// ** 조회수 증가
	int countUp(BoardVO vo);

	// ** Reply Insert
	int stepUpdate(BoardVO vo);
	int rinsert(BoardVO vo);

} // interface
