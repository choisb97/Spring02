package socketTest;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
  *** 계층도 
  WebSocketHandler (Interface) 
    	-> AbstractWebSocketHandler (A) -> TextWebSocketHandler (C) : 텍스트 데이터를 주고 받을 때 사용 
*/

public class ChatHandler extends TextWebSocketHandler {
	
	// ** user들이 접속할 때마다 이들을 보관하기 위한 Collection 객체가 필요 함
	private Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
	/*
	   ** ConcurrentHashMap
	   => interface 계층도 : Map (I) ->  ConcurrentMap<K,V> (I) ->  ConcurrentHashMap<K, V> 
	   => class 계층도 : Object -> AbstractMap<K, V> -> ConcurrentHashMap<K, V>
	   => ConcurrentHashMap 클래스의 header
	      public class ConcurrentHashMap<K, V> extends AbstractMap<K, V>
					implements ConcurrentMap<K, V>, Serializable { .... }
	   => interface ConcurrentMap의 header 
	 	  interface ConcurrentMap<K, V> extends Map { .... }
	
	   => HashMap을 thread-safe 하도록 만든 클래스
	   => 차이점 : key, value에 null 허용 안 됨, putIfAbsent라는 메소드 추가됨
	
	   ** WebSocketSession : 클라이언트와의 세션을 관리하는 객체
	   => 주요메서드
	 		getId()
			getUri() : 엔드포인트 경로 리턴
			getLocalAddress() : 로컬 서버 주소 리턴
			getRemoteAddress() : 클라이언트 주소 리턴
			boolean isOpen() : 소켓 오픈 확인
			sendMessage()
	        void close() throws IOException : 소켓 종료
	*/
	
	@Override
	// afterConnectionEstablished()
	// => 웹소켓 클라이언트와 연결되면 호출되어, 
	//    클라이언트의 WebSocketSession을 클라이언트 목록(users)에 저장   
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log(session.getId() + " 연결됨");
		users.put(session.getId(), session);
	}
	
	@Override
	// handleTextMessage()
	// => 웹소켓 클라이언트가 텍스트 메세지를 전송하면 호출되어
	//    클라이언트가 전송한 메세지를 클라이언트 목록(users)에 보관된 모든 user들
	//    즉, 전체 WebSocketSession에 다시 전달  
	// => 클라이언트측에서는 메시지를 수신하면 채팅영역에 보여주도록 구현하여 화면에 보여주게 됨  	
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log(session.getId() + "로부터 메시지 수신 : " + message.getPayload());
		for (WebSocketSession s : users.values()) {
			s.sendMessage(message);
			log(s.getId() + "에게 메시지 발송 : " + message.getPayload());
		} //for
	} 

	// afterConnectionClosed()
	// => 웹소켓 클라이언트와 연결 종료되면 (클라이언트가 "나가기" 클릭 -> disconnect) 호출되어, 
	//    종료 클라이언트의 WebSocketSession을 클라이언트 목록 (users)에서 제거  
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log(session.getId() + " 연결 종료됨");
		users.remove(session.getId());
	}
	
	// 클라이언트와의 연결에 문제가 발생하면 호출
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log(session.getId() + "** Exception 발생 : " + exception.toString());
	}
	
	// Console Message 출력 위해 ...
	private void log(String logMsg) {
		System.out.println(new Date() + " : " + logMsg);
	}

} //class
