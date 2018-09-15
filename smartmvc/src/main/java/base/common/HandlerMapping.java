package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import base.annotation.RequestMapping;
/**
 * 映射处理器:负责提供请求路径与处理器的对应关系
 * 
 */
public class HandlerMapping {
	private Map<String,Handler> handlerMap = new HashMap<String,Handler>();
	/*
	 * 依据请求路径,返回对应的Handler对象
	 */
	public Handler getHandler(String path) {
		return handlerMap.get(path);
	}
	/*
	 * 负责提供请求路径与处理器的对应关系:
	 * 利用java反射获得处理器上的@RequestMapping注解,然后
	 * 读取该注解的属性值获得路径信息,接下来,已该路径信息作为
	 * key,已处理器实例及方法对象(Method对象)的封装(Handler)作为value,
	 * 将请求路径与处理器的对应关系存放到Map里面
	 */
	public void process(List beans) {
		for(Object bean:beans) {
			//获得class对象
			Class clazz = bean.getClass();
			//获得该对象的所有方法
			Method[] methods = clazz.getDeclaredMethods();
			for(Method mh:methods) {
				//获得加在方法前的注解(@RequestMapping)
				RequestMapping rm = mh.getDeclaredAnnotation(RequestMapping.class);
				//获得注解上的路径信息
				String path = rm.value();
				System.out.println("path:"+path);
				/*
				 * 以请求路径作为key,以Handler对象作为value
				 * 将请求路径与处理器的对应关系放到Map对象里面
				 */
				handlerMap.put(path,new Handler(mh,bean));
				
			}
		}
		System.out.println("handlerMap:"+handlerMap);
	
		
		
	}

}
