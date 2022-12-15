package service;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;

public interface BoardService {
	
	// ** Board Check List
	List<BoardVO> checkList(SearchCriteria cri);
	int checkCount(SearchCriteria cri);
	
	// ** Criteria Page List
	// ** ver02
	List<BoardVO> searchList(SearchCriteria cri);
	int searchCount(SearchCriteria cri);
	
	// ** ver01
	List<BoardVO> criList(Criteria cri);
	int criTotalCount(); // ** Total Rows 개수 Count
	

	// ** selectList
	List<BoardVO> selectList(); // selectList

	// ** selectOne ( 한 명의 게시글 보기 )
	BoardVO selectOne(BoardVO vo); // selectOne

	// Insert
	int insert(BoardVO vo); // insert

	// Update
	int update(BoardVO vo); // update

	// Delete
	int delete(BoardVO vo); // delete

	// 조회수 증가
	int countUp(BoardVO vo); // countUp

	// 답글 등록
	int rinsert(BoardVO vo); // rinsert

} // i_BoardService