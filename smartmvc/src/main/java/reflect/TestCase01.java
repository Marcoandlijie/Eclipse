package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
public class TestCase01 {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Scanner scan = new Scanner(System.in);
		String className = scan.nextLine();
		Class classzz = Class.forName(className);	
		Object obj = classzz.newInstance();
		Method[]  methods = classzz.getDeclaredMethods();
		Object returnVal = null;
		for(Method m : methods) {
			String name = m.getName();
			System.out.println("name:"+name);
			if(name.startsWith("test")) {
			returnVal = m.invoke(obj);
			}
			System.out.println("returnVal:"+returnVal);
		}
	}
}
