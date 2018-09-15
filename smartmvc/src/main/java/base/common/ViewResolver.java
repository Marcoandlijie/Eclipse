package base.common;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 视图解析器:负责处理视图名
 */
public class ViewResolver {
	public void processView(String viewName,String contextPath,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		/*处理视图名,生成jsp的地址forwardPath
		 * 分两种情况:
		 * 情形一:(默认转发)
		 * 转发地址 = "/WEB-INF/"+viewName+".jsp";
		 * 情形二:(视图名以redirect:开头,则重定向)
		 * 重定向地址 = 应用名 +"/"+视图名(去掉"redirect:")	
		 */
		if(viewName.startsWith("redirect:")) {
			//情形二:(视图名以redirect:开头,则重定向)
			String redirectPath = contextPath+"/"+viewName.substring("redirect:".length());
			response.sendRedirect(redirectPath);
		}else {
			//情形一:(默认转发)
			String forwardPath = "/WEB-INF/"+viewName+".jsp";
			request.getRequestDispatcher(forwardPath).forward(request, response);
		}
		
	}

}
