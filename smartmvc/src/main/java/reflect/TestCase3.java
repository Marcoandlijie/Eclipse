package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
public class TestCase3 {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//控制台读取类名
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:"+className);
		//完成类的加载:注  JVM委托类加载器(ClassLoader)查找类的字节码问价,然后文件的内容读取到
		//方法区,并转换成一个对象,该对象就是Class对象,Class.forName方法的返回值就会死Class对象
		Class classzz = Class.forName(className);
		System.out.println("Classzz:"+classzz);
		//利用Java反射将该类实例化
		Object obj = classzz.newInstance();
		System.out.println("obj:"+obj);
		//利用反射调用该对象的方法
		/*
		 * Method对象表示一个方法,可以通过该对象获取方法名,参数类型,返回类型,调用该方法等
		 */
		Method[] methods = classzz.getDeclaredMethods();
		for(Method mh : methods) {
			//获得加在该方法前的注解(比如@Demo)
			Demo demo  = mh.getDeclaredAnnotation(Demo.class);
			System.out.println("@Demo:"+demo);
			if(demo != null) {
				//读取注解的属性值
				String value = demo.value();
				System.out.println("value:"+value);
				//该方法前有@Demo注解,则执行
				mh.invoke(obj);
			}
			
			}
		}
		
	}

