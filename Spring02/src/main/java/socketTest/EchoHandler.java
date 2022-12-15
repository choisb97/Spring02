package socketTest;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
  *** 스프링 웹 소켓 
  JAVA의 웹소켓 표준인 JSR-356에 맞춰 웹소켓 서버 기능을 구현하면
  스프링의 DispatcherServlet과의 연동이나 스프링 빈 객체를 사용하기가 매우 번거롭다.
  스프링에서는 이것을 간편하게 구현할 수 있도록 기반 클래스(WebSocketHandler)를 제공해준다.     	
  이를 통해 컨트롤러를 구현하는 것과 비슷한 방식으로 서버를 구현할 수 있다.   

  *** 계층도 
  WebSocketHandler (Interface) 
	-> AbstractWebSocketHandler (A) -> TextWebSocketHandler (C) : 텍스트 데이터를 주고 받을때 사용 
                                    -> BinaryWebSocketHandler (C) 
*/

public class EchoHandler extends TextWebSocketHandler {
	
	@Override
	// afterConnectionEstablished() : 웹소켓 클라이언트와 연결되면 호출
	// 매개변수 WebSocketSession : 클라이언트와의 세션을 관리하는 객체로서 클라이언트의 정보를 제공
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//super.afterConnectionEstablished(session);
		System.out.println("\n** 연결성공 session ID => " + session.getId());
	}
	
	@Override
	// handleTextMessage() : 웹소켓 클라이언트가 텍스트 메세지를 전송하면 호출
	// 매개변수 TextMessage : 클라이언트가 전송한 텍스트 데이터
	//						  getPayload() 메서드로 읽어옴
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//super.handleTextMessage(session, message);
		// ** 받은 메시지 처리
		System.out.printf("** %s로부터 [%s] 메시지를 받음 \n", session.getId(), message.getPayload());
		
		// ** 응답 보내기
		session.sendMessage(new TextMessage("** echo : " + message.getPayload()));
	}
	
	@Override
	// afterConnectionClosed() : 웹소켓 클라이언트와 연결 종료될 때 호출 
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
	}

} //class
