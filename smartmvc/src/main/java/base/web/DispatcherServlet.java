package base.web;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;
import base.common.ViewResolver;
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HandlerMapping handlerMapping;
	private ViewResolver viewResolver;
	/*
	 * 读取配置文件(smartmvc.xml),将处理器实例化,然后将这些处理器
	 * 交给HandlerMapping(映射处理器)来处理:
	 * 注:HandlerMapping读取处理器中的路径信息,建立请求路径已处理器的对应关系
	 */
	@Override
	public void init() throws ServletException {
		//利用dom4j解析配置文档
		SAXReader sax = new SAXReader();
		InputStream in = getClass().getClassLoader().getResourceAsStream("smartmvc.xml");
		try {
			//解析配置文件
			Document doc = sax.read(in);
			//获取根元素
			Element root = doc.getRootElement();
			//获取根元素下的所有子元素
			List<Element> elements = root.elements();
			List beans = new ArrayList();
			for(Element e:elements) {
				//获取类名
				String className = e.attributeValue("class");
				System.out.println("className:"+className);
				//将处理器实例化
				Object bean = Class.forName(className).newInstance();//!
				//将处理器实例添加到集合里面
				beans.add(bean);
			}
			System.out.println("beans:"+beans);
			//创建映射处理器
			handlerMapping = new HandlerMapping();
			//将处理器实例交给HanderMapping来处理
			handlerMapping.process(beans);
			//创建视图解析器
			viewResolver = new ViewResolver();
			
		} catch (Exception e) {
			e.printStackTrace();
			//异常抛给容器处理
			throw new ServletException(e);
		}
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//获得请求资源路径
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		//获得应用名
		String contextPath = request.getContextPath();
		//截取请求资源路径的一部分
		String path = uri.substring(contextPath.length());
		System.out.println("path:"+path);
		/*调用HandlerMapping的process的方法来对应处理器
		 * 注:处理器封装到Handler对象里面,这样便于利用java反射来调用
		 */
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:"+handler);
		//利用java反射来调用处理器的方法
		Method mh = handler.getMh();
		Object obj = handler.getObj();
		Object returnVal = null;
		try {
			/*
			 * 处理器的方法有可能带有参数,所以在调用之前需要利用java反射
			 * 来获得要调用的方法的参数信息
			 */
			//获得调用的方法中参数的类型,返回值为Class数组
			Class[] types = mh.getParameterTypes();
			System.out.println("types长度:"+types.length);
			if(types.length == 0) {
				//该方法不带参
				returnVal = mh.invoke(obj);
			}else {
				//该方法带参
				//创建用来存放参数值的数组
				Object[] args = new Object[types.length];
				for(int i=0;i<types.length;i++) {
					//看参数类型是什么,然后赋值相应的值
					if(types[i] == HttpServletRequest.class) {
						args[i] = request;
					}
					if(types[i] == HttpServletResponse.class) {
						args[i] = response;
					}
				}
				returnVal = mh.invoke(obj,args);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}	
		System.out.println("returnVal:"+returnVal);
		//获得视图名
		String viewName = returnVal.toString();
		//调用ViewResolver来处理视图名
		viewResolver.processView(viewName, contextPath, request, response);
		
		
		
		
	}

}
