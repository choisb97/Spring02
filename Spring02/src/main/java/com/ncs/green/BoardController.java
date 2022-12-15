package com.ncs.green;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import lombok.extern.log4j.Log4j;
import service.BoardService;
import vo.BoardVO;
import vo.MemberVO;

@Log4j
@Controller
public class BoardController {
	
	// ** 전역변수 활용
	@Autowired
	BoardService service;
	
//---------------------------------------------------------------------------------------------------------------------------

	// ** Log4j Test
	@RequestMapping(value="/log4jTest")
	public String log4jTest() {
		
		// ** @Log4j Test
		// => Lombok, log4j dependency 필요(pom.xml 확인)
		// => 로깅레벨 단계 준수 함(log4j.xml의 아래 logger Tag의 level 확인)
		// 	  TRACE > DEBUG > INFO > WARN > ERROR > FATAL(치명적인)
		//    <logger name="com.ncs.green">
		//		 <level value="info" />
		//    </logger>
		log.debug("** @Log4j Test 1) debug **");
		log.info("** @Log4j Test 2) info **");
		log.warn("** @Log4j Test 3) warn **");
		log.error("** @Log4j Test 4) error **");
		
    	return "home";
    	
	} // log4jTest
	
//-----------------------------------------------------------------------------------------------------
	/*
	// ** Board_CheckList
	// => UI의 checkbox를 이용하여 mapper에 반복문 적용하기
	@RequestMapping(value = "/bchecklist")
	public ModelAndView bchecklist(ModelAndView mv, BoardVO vo) {
	
		// 1) Check_Box 처리
		// String[] check = request.getParameterValues("check");
		// => vo에 배열 Type의 check 컬럼을 추가하면 편리

		// 2) Service 실행
		// => 선택하지 않은 경우 : selectList()
		// => 선택을 한 경우 : 조건별 검색 checkList(vo) -> 추가
		// => 조건 선택여부 확인 : 배열 check의 길이, null
		// => 배열 Type의 경우 선택하지 않으면 check = null이므로 길이 비교 필요 없음.

		// ** view 화면에 요청 조건이 동일하게 표시되도록 하기 위해
		// => 요청 조건 재전달하기
		mv.addObject("check", vo.getCheck());

		if (vo.getCheck() != null && vo.getCheck().length > 0) { 
			
			// => 조건별 검색
			mv.addObject("banana", service.checkList(vo));

		} else
			mv.addObject("banana", service.selectList());

		mv.setViewName("/board/bCheckList");

		return mv;

	} // bchecklist
	*/
	
	// ** Board Check List ***************************
	// => ver01) UI의 checkbox를 이용하여 mapper에 반복문 적용하기
	// => ver02) SearchCriteria, PageMaker 적용하기 
	@RequestMapping(value="/bchecklist")
	public ModelAndView bchecklist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		
		// ** Paging 준비
		cri.setSnoEno();
		
		// ** @Log4j 적용 Test
		log.info("\n ** @Log4j 적용 Test => " + cri);
		
		// 1) Check_Box 처리
		// => check 선택이 없는 경우 check는 null 값으로 
		if ( cri.getCheck() != null && cri.getCheck().length < 1 ) {
			cri.setCheck(null);
		}
		// 2) Service 실행
		// => 선택하지 않은 경우, 선택한 경우 모두 mapper의 Sql로 처리
		mv.addObject("banana", service.checkList(cri));
		
		// 3) View 처리 => PageMaker
		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(service.checkCount(cri));   
		
    	mv.addObject("pageMaker", pageMaker);
		
		mv.setViewName("board/bCheckList");
    	return mv;
    	
	} //bchecklist
	
//-----------------------------------------------------------------------------------------------------
	
	// ** Ajax_jsonView BoardDetail
	@RequestMapping(value = "/jsbdetail")
	public ModelAndView jsbdetail(HttpServletResponse response, BoardVO vo, ModelAndView mv) {
		
		// ** jsonView 사용 시 response의 한글처리
		response.setContentType("text/html; charset = UTF-8");
		
		vo = service.selectOne(vo);
		
		mv.addObject("content", vo.getContent());
		mv.setViewName("jsonView");
		
		return mv;
			
	} // jsbdetail

//-----------------------------------------------------------------------------------------------------------------------

	// ** aidBList
	@RequestMapping(value = "/aidblist")
	public ModelAndView aidblist(HttpServletRequest request, ModelAndView mv, SearchCriteria cri) {
	
		// cri.setKeyword(request.getParameter("id"));
		// => ajax 요청 시 id 값을 keyword로 지정하면 자동으로 set cri 되므로 생략 가능
		// => SearchCriteria 사용을 위해서는
		//    searchType, keyword, rowsPerPage
		//    currPage : 1, sno : 0 (MySQL) => Default 값
		
		// cri.setCurrPage(1); => default 값이 1이기 때문에 사용하지 않아도 됨.
		cri.setSearchType("i");
		cri.setRowsPerPage(50); // Paging을 적용하지 않으므로 넉넉하게 50 지정
		
		List<BoardVO> list = new ArrayList<BoardVO>();

		list = service.searchList(cri);
		
		if (list != null) {
			mv.addObject("banana", list);

		} else {
			mv.addObject("message", "** 출력할 List가 한 건도 없습니다. **");
		}
		
		mv.setViewName("axTest/axBoardList");
		
		return mv;
	
	} // aidblist
	
//-----------------------------------------------------------------------------------------------------------------------
	
	// ** Ajax_BoardList
	@RequestMapping(value = "/axblist")
	public ModelAndView axblist(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, BoardVO vo) {
	
		// mv.addObject("banana", service.selectList()); 
		// => list가 null인 경우 message 처리를 UI에서

		List<BoardVO> list = new ArrayList<BoardVO>();

		list = service.selectList();
		
		if (list != null) {
			mv.addObject("banana", list);

		} else {
			mv.addObject("message", "** 출력할 List가 한 건도 없습니다. **");
		}
		
		mv.setViewName("axTest/axBoardList");
		
		return mv;
	
	} // axblist
	
//-----------------------------------------------------------------------------------------------------------------------
	
	// ** Criteria Page List
	// => ver01 : Criteria cri
	// => ver02 : SearchCriteria cri
	@RequestMapping(value = "/bcrilist")
	public ModelAndView bcrilist(HttpServletRequest request, HttpServletResponse response, 
									ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		
		// 1) Criteria 처리
		// => setCurrPage, setRowsPerPage는 Parameter로 전달되어,
		//    setCurrPage(...), setRowsPerPage(...)는 자동처리 됨(스프링에 의해)
		//    -> cri.setCurrPage(Integer.parseInt(request.getParameter("currPage")))
		// => 그러므로 currPage 이용해서 sno, eno 계산만 하면 됨.

		cri.setSnoEno(); // Criteria Class에 값이 저장되어 있음
		
		// ** ver02
		// => SrchCriteria : searchType, keyword Parameter로 전달되어 자동 set 됨.
		
		// 2) 서비스 처리
		// => List 처리
		//mv.addObject("banana", service.criList(cri)); // ver01
		mv.addObject("banana", service.searchList(cri)); // ver02

		
		// 3) View 처리 => PageMaker
		pageMaker.setCri(cri);
		//pageMaker.setTotalRowsCount(service.criTotalCount()); // ver01 - 전체 Rows 개수
		pageMaker.setTotalRowsCount(service.searchCount(cri)); // ver02 - 조건과 일치하는 Rows 개수
		
		mv.addObject("pageMaker", pageMaker);
		mv.setViewName("/board/bCriList");
		return mv;

	} // bcrilist
	
//-----------------------------------------------------------------------------------------------------------------------
	
	// ** BoardList
	@RequestMapping(value = "/blist")
	public ModelAndView mlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

		// BoardList
		List<BoardVO> list = new ArrayList<BoardVO>();

		list = service.selectList();
		
		if (list != null) {
			mv.addObject("banana", list); // request.setAttribute("banana", list)와 동일

		} else {
			mv.addObject("message", "** 출력할 List가 한 건도 없습니다. **");
		}

		mv.setViewName("/board/boardList");
		return mv;

	} // blist

//-----------------------------------------------------------------------------------------------------------------------

	// ** BoardDetail
	// => 글 내용 확인, 수정화면 요청 시(?jCode=U&seq=....)
	// => 조회수 증가
	//   - 증가조건 : 글보는 이(loginID)와 글쓴이가 다를 때 && jCode != U
	//   - 증가 메서드 : DAO, Service에 조회수 증가 메서드(countUp) 추가
	//   - 증가 시점 : selectOne 성공 후
	@RequestMapping(value = "/bdetail")
	public ModelAndView bdetail(HttpServletRequest request, HttpServletResponse response, BoardVO vo, ModelAndView mv) {
		
		// 1. 요청분석
		String uri = "/board/boardDetail";

		// 2. Service 처리
		vo = service.selectOne(vo);
		if (vo != null ) {
			// 2-1) ** 조회수 증가
			// => 로그인 ID 확인
			String loginID = (String)request.getSession().getAttribute("loginID"); // Type이 Object이기 때문에 String으로 맞춤
			
			// ID가 로그인 ID와 같지 않고, U와 jCode가 같지 않다면
			if (!vo.getId().equals(loginID) && !"U".equals(request.getParameter("jCode"))) {
				// => 조회수 증가
				if (service.countUp(vo) > 0)
					vo.setCnt(vo.getCnt() + 1);
			} // if_증가조건
			
			// 2-2) ** 수정요청인지 확인
			if ("U".equals(request.getParameter("jCode"))) // 찾은 자료에 "U"가 jCode와 같다면
				uri = "/board/bupdateForm"; // updateForm으로 이동
			
			// 2-3) 결과전달
			request.setAttribute("apple", vo);
			
		} else {
			mv.addObject("message", "=> 글 번호에 해당하는 자료가 없습니다.");
		} // if_vo
		
		// 3. View
		mv.setViewName(uri);
		return mv;
			
	} // bdetail

//-----------------------------------------------------------------------------------------------------------------------

	// ** InsertF : 새 게시글 등록
	@RequestMapping(value = "/binsertf")
	public ModelAndView binsertf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

		mv.setViewName("/board/binsertForm");

		return mv;

	} // binsertf

//-----------------------------------------------------------------------------------------------------------------------

	// ** Insert : 새 게시글 등록
	@RequestMapping(value = "/binsert", method = RequestMethod.POST)
	// => 매핑네임과 method가 일치하는 요청만 처리함
	public ModelAndView binsert(HttpServletRequest request, 
								HttpServletResponse response, ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// 1. 요청분석
	    // => insert 성공 : blist (redirect 요청, message 전달)
	    //    insert 실패 : binsertForm.jsp
		String uri = "redirect:blist";
		
		// 2. Service 처리
		if (service.insert(vo) > 0) {
			rttr.addFlashAttribute("message", "=> 새 게시글 등록 성공!!");
			
		} else {
			mv.addObject("message", "=> 게시글 등록 실패, 다시 시도 해주세용.");
			uri = "/board/binsertForm";
		}
		
		// 3. 결과(View -> Forward) 처리
		mv.setViewName(uri);
		return mv;

	} // binsert
	
//-----------------------------------------------------------------------------------------------------------------------

	// ** Update : 게시글 수정하기

	@RequestMapping(value = "/bupdate", method = RequestMethod.POST)
	public ModelAndView bupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, BoardVO vo) {
		
		// 1. 요청분석
		// => Update 성공 : boardDetail.jsp
		//           실패 : 재수정 유도 -> bupdateForm.jsp
		String uri = "/board/boardDetail";
		mv.addObject("apple", vo);
		// => Update 성공/실패 모두 출력 시 필요하므로
		
		// 2. Service 처리
		if(service.update(vo) > 0) {
			mv.addObject("message", "=> 게시글 수정 성공!!");
			
		} else {
			mv.addObject("message", "=> 게시글 수정 실패, 다시 시도 해주세용.");
			uri = "/board/bupdateForm";
		}
		
		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;

	} // bupdate
	
//-----------------------------------------------------------------------------------------------------------------------

	// ** Delete : 게시글 삭제
	@RequestMapping(value = "/bdelete")
	public ModelAndView bdelete(HttpServletRequest request, HttpServletResponse response, 
								ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		
		// 1. 요청분석
		// => Delete 성공 : redirect:blist
		//           실패 : message, redirect:bdetail
		String uri = "redirect:blist";
        	
		// 2. Service 처리
		if (service.delete(vo) > 0) {
			rttr.addFlashAttribute("message", "=> 게시글 삭제 성공!!");

		} else {
			rttr.addFlashAttribute("message", "=> 게시글 삭제 실패, 다시 시도해주세요.");
			uri = "redirect:bdetail?seq=" + vo.getSeq();
		} // Service
		
		
		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;

	} // bdelete
	
//-----------------------------------------------------------------------------------------------------------------------

	// ** Reply_Insert  : 답글 등록
	@RequestMapping(value = "/rinsertf")
	public ModelAndView rinsertf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, BoardVO vo) {
		
		// => vo에는 전달된 부모글의 root, step, indent가 담겨있음.
		// => 매핑메서드의 인자로 정의된 vo는 request.setAttribute와 동일 scope
		//    단, 클래스명의 첫 글자를 소문자로 -> ex) ${boardVO.root}

		mv.setViewName("/board/rinsertForm");

		return mv;

	} // rinsertf
		
//-----------------------------------------------------------------------------------------------------------------------
	
	// ** Reply_Insert  : 답글 등록
	@RequestMapping(value = "/rinsert", method = RequestMethod.POST)
	// => 매핑네임과 method가 일치하는 요청만 처리함
	public ModelAndView rinsert(HttpServletRequest request, HttpServletResponse response, 
								ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// 1. 요청분석
		// => Join 성공 : blist
		//         실패 : 재입력 유도 (rinsertForm.jsp)
	    // => set vo
	    //	        - root : 부모와 동일 
	    //	        - step : 부모 step + 1
	    //	        - indent : 부모 indent + 1
	    //	        - 그러므로 rinsertForm에 부모값을 보관(hidden으로)해서 전달 받음
	    //	          이를 위해 boardDetail에서 요청 시 쿼리스트링으로 전달 -> rinsertf
		String uri = "redirect:blist";
		
		vo.setStep(vo.getStep() + 1); // 부모 step + 1
		vo.setIndent(vo.getIndent() + 1); // 부모 indent + 1

		// 2. Service 처리
		if (service.rinsert(vo) > 0) {
			rttr.addFlashAttribute("message", "=> 댓글 등록 성공!!");

		} else {
			rttr.addFlashAttribute("message", "=> 댓글 등록 실패, 다시 시도 해주세용.");
			uri = "/board/rinsertForm";
		}

		// 3. 결과(View -> Forward) 처리

		mv.setViewName(uri);
		return mv;

	} // rinsert
	
} // class
