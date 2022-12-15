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

import criTest.SearchCriteria;
import service.JoService;
import service.MemberService;
import vo.JoVO;

@Controller
public class JoController {
	
	// ** 전역변수 활용
	@Autowired
	JoService service;
	
	@Autowired
	MemberService mservice; // Detail에서 MemberList 불러오기 위해
	
	// ** JoList
	@RequestMapping(value = "/jlist")
	public ModelAndView jlist(HttpServletRequest request, HttpServletResponse response, 
											ModelAndView mv, RedirectAttributes rttr) {
		
		// ** RedirectAttributes의 addFlashAttribute로 전달된 값 확인
		// => Insert - rttr.addFlashAttribute("mytest", "rttr.addFlashAttribute 메서드 Test");
		//System.out.println("********* Test 1 => " + rttr.getFlashAttributes());
		//System.out.println("********* Test 2 => " + request.getSession().getAttribute("mytest"));
		// ==> 좀 더 확인 필요함
		

		List<JoVO> list = new ArrayList<JoVO>();

		list = service.selectList();
		if (list != null) {
			mv.addObject("banana", list); // request.setAttribute("banana", list)와 동일

		} else {
			mv.addObject("message", "** 출력 자료가 없습니다 **");
			// request.setAttribute("message", "** 출력 자료가 없습니다 **")
		}

		mv.setViewName("/jo/joList");
		return mv;

	} // jlist

//-----------------------------------------------------------------------------------------------------------------------

	// ** Detail
	// => 아래에 조원 목록 출력
	// => memjo Table에서 selectOne -> apple
	// => member Table에서 조건검색 jno = #{jno} -> banana
	@RequestMapping(value = "/jdetail", method=RequestMethod.GET)
	public ModelAndView jdetail(HttpServletRequest request, HttpServletResponse response, 
								JoVO vo, SearchCriteria cri, ModelAndView mv) {
		
		// ** 수정 성공 후 redirect 요청으로 전달된 경우 message 처리
		if (request.getParameter("message") != null && request.getParameter("message").length() > 0)
			mv.addObject("message", request.getParameter("message"));
		System.out.println("***** jno 전달 확인 => " + vo.getJno());
		System.out.println("***** jname 한글 전달 확인 => " + vo.getJname());
		
		
		// 1. 요청분석
		String uri = "/jo/joDetail";
		
		// 2. Service 처리
			vo = service.selectOne(vo);
			
			if (vo != null) { // 해당 자료가 있는 경우
				
				// 2-1) 수정요청인지 확인
				if ("U".equals(request.getParameter("jCode"))) // 찾은 자료에 "U"가 jCode와 같다면
					uri = "/jo/jupdateForm"; // jupdateForm으로 이동
				
				else {
					// 조원 목록 불러오기
					// => 조별로 조회가 가능한 searchList 메서드를 활용함
					//    1 Page만 있으면 되므로 기본값을 지정함.
					//    단, RowsPerPage는 현재 Paging을 하지 않기 때문에 큰 값을 지정.
					cri.setRowsPerPage(30);
					cri.setCurrPage(1);
					cri.setSnoEno();
					cri.setKeyword(Integer.toString(vo.getJno()));
					cri.setSearchType("j");
					mv.addObject("banana", mservice.searchList(cri));
				} 
				
				// 2-2) 결과 전달
				mv.addObject("apple", vo);
				
			} else
				mv.addObject("message", "** 조 번호에 해당하는 자료가 없습니다 **");

			mv.setViewName(uri);
			return mv;
			
	} // jdetail

//-----------------------------------------------------------------------------------------------------------------------

	// ** InsertF : 새 게시글 등록
	@RequestMapping(value = "/jinsertf")
	public ModelAndView jinsertf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

		mv.setViewName("/jo/jinsertForm");

		return mv;

	} // jinsertf
	
//-----------------------------------------------------------------------------------------------------------------------

	// ** Insert
	@RequestMapping(value = "/jinsert", method = RequestMethod.POST)
	// => 매핑네임과 method가 일치하는 요청만 처리함
	public ModelAndView jinsert(HttpServletRequest request, 
								HttpServletResponse response, ModelAndView mv, JoVO vo, RedirectAttributes rttr) {
		// 1. 요청분석
	    // => insert 성공 : jlist (redirect 요청, message 전달)
	    //    insert 실패 : jinsertForm.jsp
		String uri = "redirect:jlist";
		
		// 2. Service 처리
		if (service.insert(vo) > 0) {
			rttr.addFlashAttribute("message", "=> 새로운 그룹 등록 성공!!");
			rttr.addFlashAttribute("mytest", "rttr.addFlashAttribute 메서드 Test");
			
		} else {
			mv.addObject("message", "=> 그룹 등록 실패, 다시 시도 해주세용.");
			uri = "/jo/jinsertForm";
		}
		
		// 3. 결과(View -> Forward) 처리
		mv.setViewName(uri);
		return mv;

	} // jinsert
	
//-----------------------------------------------------------------------------------------------------------------------

	// ** Update : Update 성공 후 jno 꺼내기
	@RequestMapping(value = "/jupdate", method = RequestMethod.POST)
	public ModelAndView jupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, JoVO vo) {

		// 1. 요청분석
		// => Update 성공 : 내 정보 표시 -> joDetail.jsp
		//           실패 : 재수정 요구 -> jupdateForm.jsp
		//String uri = "/jo/joDetail"; // 원래
		// String uri = "redirect:jdetail?jno=" + vo.getJno(); // ver01
		String uri = "redirect:jdetail"; // ver02
		
		// ** Spring의 redirect:
		// => mv.addObject에 보관한 값들을 쿼리스트링의 parameter로 붙여 전달해줌.
		//    그러므로 전달하려는 값들을 mv.addObject로 처리하면 편리.
		//    단, 브라우저의 주소창에 보여지게 됨.
		
		
		/*
		 redirect에서 parameter를 사용하여 전달하면서 RedirectAttributes rttr 사용 시 오류 발생
		 jdetail 메서드의 매개변수에서 vo로 전달된 parameter를 받는 경우에 오류 발생함.
		 vo로 받지 않는 경우에는 쿼리스트링으로 전달하면서 RedirectAttributes rttr 사용 가능.
		 
		 ** RedirectAttributes : Redirect 할 때 parameter를 쉽게 전달할 수 있도록 지원함.
		   => addtribute : URL에 parameter가 붙어 전달되게 된다.
		                   그렇기 때문에 전달된 페이지에서 parameter가 노출됨.
		   => addFlashAttribute : Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸된다.
		                          Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어준다.
		                          (insert 성공 후 redirect:jlist에서 Test)
		*/
		mv.addObject("apple", vo);
		// => Update 성공/실패 모두 출력 시 필요하므로

		// 2. Service 처리
		if(service.update(vo) > 0) {
			//rttr.addFlashAttribute("message", "정보 수정이 성공적으로 완료되었습니다.");
			// => RedirectAttributes rttr 사용 시 오류 발생
			mv.addObject("jno", vo.getJno());
			mv.addObject("jname", vo.getJname());
			mv.addObject("message", "=> 정보 수정이 성공적으로 완료되었습니다. *^^*");

		} else {
			mv.addObject("message", "=> 정보 수정 실패, 다시 수정 부탁드립니다.");
			uri = "/jo/jupdateForm";
		}

		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;

	} // jupdate
	
//-----------------------------------------------------------------------------------------------------------------------
	
	// ** Delete : 게시글 삭제
	@RequestMapping(value = "/jdelete")
	public ModelAndView jdelete(HttpServletRequest request, HttpServletResponse response, 
								ModelAndView mv, JoVO vo, RedirectAttributes rttr) {
		
		// 1. 요청분석
		// => Delete 성공 : redirect:jlist
		//           실패 : message, redirect:bdetail
		String uri = "redirect:jlist";
        	
		// 2. Service 처리
		if (service.delete(vo) > 0) {
			rttr.addFlashAttribute("message", "=> 그룹 삭제 성공!!");

		} else {
			rttr.addFlashAttribute("message", "=> 그룹 삭제 실패, 다시 시도해주세요.");
			uri = "redirect:jdetail?jno=" + vo.getJno();
		} // Service
		
		
		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;

	} // jdelete
	
} // class
