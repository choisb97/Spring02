package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.Criteria;
import criTest.SearchCriteria;
import mapperInterface.BoardMapper;
import vo.BoardVO;

/*
< ** Interface 자동 완성 >
=> Alt + Shift + T  
=> 또는 마우스우클릭 PopUp Menu의  Refactor - Extract Interface...
*/

@Service
public class BoardServiceImpl implements BoardService {
	
	// ** Mybatis 적용 (interface 방식)
	// => interface BoardMapper를 통해서
	//    BoardMapper.xml의 SQL구문 접근
	@Autowired
	BoardMapper mapper; // Mybatis 적용 후 (변수명 mapper로 변경 -> ctrl + f)
	
//	BoardDAO dao; // Mybatis 적용 전
	// = new BoardDAO(); -> "="이 @Repository 역할
	
	// ** Board Check List
	@Override
	public List<BoardVO> checkList(SearchCriteria cri) {
		return mapper.checkList(cri);
	}
	
	@Override
	public int checkCount(SearchCriteria cri) {
		return mapper.checkCount(cri);
	}
	
	// ** Criteria Page List
	// ** ver02
	@Override
	public List<BoardVO> searchList(SearchCriteria cri) {
		return mapper.searchList(cri);
	} // criList
	
	
	// ** Total Rows 개수 Count
	public int searchCount(SearchCriteria cri) {
		return mapper.searchCount(cri);
	} // criTotalCount
	
//--------------------------------------------------
	
	// ** ver01
	@Override
	public List<BoardVO> criList(Criteria cri) {
		return mapper.criList(cri);
	} // criList
	
	
	// ** Total Rows 개수 Count
	public int criTotalCount() {
		return mapper.criTotalCount();
	} // criTotalCount
	
//--------------------------------------------------
	
	// ** selectList
	@Override
	public List<BoardVO> selectList() {
		return mapper.selectList();
	} // selectList
	
//--------------------------------------------------
	
	// ** selectOne ( 한 명의 게시글 보기 )
	@Override
	public BoardVO selectOne(BoardVO vo) {
		return mapper.selectOne(vo);
	} // selectOne
	
//--------------------------------------------------
	
	// Insert
	@Override
	public int insert(BoardVO vo) {
		return mapper.insert(vo);
	} // insert
	
//--------------------------------------------------
	
	// Update
	@Override
	public int update(BoardVO vo) {
		return mapper.update(vo);
	} // update
	
//--------------------------------------------------

	// Delete
	@Override
	public int delete(BoardVO vo) {
		return mapper.delete(vo);
	} // delete

//--------------------------------------------------

	// 조회수 증가
	@Override
	public int countUp(BoardVO vo) {
		return mapper.countUp(vo);
	} // countUp

//--------------------------------------------------

	// 답글 등록
	@Override
	public int rinsert(BoardVO vo) {
		int result = mapper.rinsert(vo);
		
		if (result > 0)
			System.out.println("** stepUpdate Count => " + mapper.stepUpdate(vo));
		else
			result = 0;
		
		return result;
	} // rinsert

} // class
