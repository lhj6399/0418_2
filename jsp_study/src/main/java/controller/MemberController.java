package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import service.MemberService;
import service.MemberServiceImpl;


@WebServlet("/memb/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//로그객체
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    //rdp 객체 requestDispatcher : 요청결과에 대한 응답 데이터를 jsp로 전송하는 역할
	private RequestDispatcher rdp;
	//destpage : 목적지 주소
	private String destPage;
	//isOk
	private int isOk;
	
	//service
	private MemberService msv;  //service -> interface
    
    public MemberController() {
        msv = new MemberServiceImpl(); //service -> class implement MemberService
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 통합처리
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI();
		log.info(uri);
		
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(path);
		
		switch (path) {
		case "join":
			//index의 join버튼 클릭시 
			destPage = "/member/join.jsp";
			break;
		case "register":
			try {
				//jsp에서 보낸 파라미터 값 받기
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				String phone = request.getParameter("phone");
				
				MemberVO mvo = new MemberVO(id, pwd, email, age, phone);
				log.info(" join mvo >>>> {} ", mvo);
				isOk = msv.register(mvo);
				log.info("join >> {} ", (isOk > 0)?"OK":"Fail");
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "login":
			try {
				//jsp에서 보내온 id, pwd 받기
				//id,pwd를 객체로 만들어 db로 전송 (같은 id/pwd 를 갖는 객체가 있는지 확인)
				//같은 값을 갖는 객체가 있다면 로그인 (세션객체에 값을 저장)
				//session : 모든 jsp에서 공유되는 객체 / 브라우저가 종료되면 삭제
				
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				MemberVO mvo = new MemberVO(id, pwd);
				
				log.info("login >>>> {} ", mvo);
				//id와 pwd가 일치하는 객체 전부를 리턴
				MemberVO loginMvo = msv.login(mvo);
				log.info("return loginMvo >>>> {} ", loginMvo);
				
				//loginMvo 객체가 없다면... loginMvo = null;
				if(loginMvo != null) {
					//session 에 저장
					//세션 객체 가져오기
					//연결된 세션 객체가 있다면 기존 객체를 가져오고, 없으면 생성
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					ses.setMaxInactiveInterval(10*60);  //로그인 유지시간 초단위로 설정
					
				}else {
					//로그인 객체가 없다면...
					//index.jsp로 메시지 전송 
					request.setAttribute("msg_login", -1);
				}
				destPage="/index.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "logout":
			try {
				//session에 값이 있다면 해당 세션 연결 끊기
				HttpSession ses = request.getSession();
				
				MemberVO mvo = (MemberVO)ses.getAttribute("ses");
				log.info("ses에서 추출한 mvo >> {} ", mvo);
				
				//lastLogin update
				isOk = msv.lastLogin(mvo.getId());
				log.info("lastLogin >> {} ", (isOk > 0)?"OK":"Fail");
				ses.invalidate(); //세션 무효화 (세션 끊기)
				destPage="/index.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list":
			try {
				List<MemberVO> list = msv.getList();
				request.setAttribute("list", list);
				destPage="/member/list.jsp";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "modify":
			try {
				//페이지 이동만.
				destPage="/member/modify.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		case "update":
			try {
				//실제 데이터 수정
				String id = request.getParameter("id");  //where
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				String phone = request.getParameter("phone");
				
				MemberVO mvo = new MemberVO(id, pwd, email, age, phone);
				
				int isOk = msv.update(mvo);
				log.info("lastLogin >> {} ", (isOk > 0)?"OK":"Fail");
				if(isOk>0) {
					request.setAttribute("msg_update", "ok");
					
					//업데이트 후 세션 끊기. 
					HttpSession ses = request.getSession(); //로그인한 세션 가져와라.
					ses.invalidate();  // 세션 끊기
					destPage="/index.jsp";
				}else {
					request.setAttribute("msg_update", "fail");
					destPage="/member/modify.jsp";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "delete":
			try {
				HttpSession ses = request.getSession(); //로그인 된 세션 호출
				String id =  ((MemberVO)ses.getAttribute("ses")).getId();
				
				int isOk = msv.delete(id);
				log.info("delete >> {} ", (isOk > 0)?"OK":"Fail");
				// 삭제는 DB에서 삭제 / 세션은 별도로 삭제(끊어야 함)
				if(isOk>0) {
					request.setAttribute("msg_delete", "ok");
					ses.invalidate();					
				}
				destPage="/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		// 목적이 이동 설정
		rdp=request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// method get으로 오는 처리
		service(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// method post로 오는 처리
		service(request, response);
	}

}
