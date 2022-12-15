package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.SearchCriteria;
import mapperInterface.MemberMapper;
import vo.MemberVO;

/*
 < ** Service > 
 => 요청클래스(컨트롤러)와 DAO클래스 사이의 연결(완충지대) 역할
 => 요청클래스와 DAO클래스 사이에서 변경사항이 생기더라도 서로 영향받지 않도록 해주는 역할
    결합도는 낮추고, 응집도는 높인다.


 < ** Interface 자동 완성 >
 => Alt + Shift + T  
 => 또는 마우스우클릭 PopUp Menu의  Refactor - Extract Interface...
*/

@Service
public class MemberServiceImpl implements MemberService {

	// ** axRSDetail
	@Override
	public MemberVO rsDetail(String id, Integer jno) {
		return mapper.rsDetail(id, jno);
	}
	
	// ** Member Check List
	@Override
	public List<MemberVO> checkList(MemberVO vo) {
		return mapper.checkList(vo);
	}

	// ** Mybatis interface 방식 적용
	// => MemberMapper의 인스턴스를 스프링이 생성해주고, 이를 주입받아 실행 함.
	//    단, 설정 파일에 <mybatis-spring:scan base-package="mapperInterface" /> 반드시 추가해야 함.
	//    MemberDAO => mapperInterface 사용으로 MemberMapper가 역할을 대신 함.
	@Autowired
	MemberMapper mapper; // Mybatis 적용 후(변수명 mapper로 변경 - ctrl + f)
	
	//MemberDAO dao; // Mybatis 적용 전
	
	
	// ** SearchCriteria Page List
	@Override
	public List<MemberVO> searchList(SearchCriteria cri) {
		return mapper.searchList(cri);
	} // searchList
	
	@Override
	public int searchCount(SearchCriteria cri) {
		return mapper.searchCount(cri);
	} // searchCount
	
	
	// ** selectList
	@Override
	public List<MemberVO> selectList() {
		return mapper.selectList();
	} // selectList
	
//--------------------------------------------------
	
	// ** selectOne ( 한 명의 정보 보기 )
	@Override
	public MemberVO selectOne(MemberVO vo) {
		return mapper.selectOne(vo);
	} // selectOne
	
//--------------------------------------------------
	
	// Insert
	@Override
	public int insert(MemberVO vo) {
		return mapper.insert(vo);
	} // insert
	
//--------------------------------------------------
	
	// Update
	@Override
	public int update(MemberVO vo) {
		return mapper.update(vo);
	} // update
	
//--------------------------------------------------

	// Delete
	@Override
	public int delete(MemberVO vo) {
		return mapper.delete(vo);
	} // delete

} // class
