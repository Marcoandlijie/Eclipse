package demo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.annotation.RequestMapping;
/**
 * 处理器:用来处理业务逻辑
 * @author Administrator
 */
public class HelloController {
	@RequestMapping("/hello.do")
	public String hello() {
		System.out.println("HelloController的hello方法");
		/*
		 * 返回视图名"hello"
		 * ViewResolver会将视图名映射成对应的jsp
		 */
		return "hello";
	}
	@RequestMapping("/toLogin.do")
	public String toLogin() {
		System.out.println("HelloController的toLogin方法");
		return "login";
	}
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("HelloController的login方法");
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		System.out.println("uname:"+uname+"  "+"pwd:"+pwd);
		if("Sally".equals(uname) && "test".equals(pwd)) {//登陆成功
			//如果视图名是以redirect开头表示重定向
			return "redirect:toGreeting.do";
		}else {//登陆失败
			request.setAttribute("login_failed","用户名或密码错误");
			return "login";
		}

	}
	@RequestMapping("/toGreeting.do")
	public String toGreeting() {
		System.out.println("HelloController的toGreeting方法");
		return "greeting";
	}
}
