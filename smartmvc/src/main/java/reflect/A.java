package reflect;

public class A {
	public void f1() {
		System.out.println("A的f1方法");
	}
	public String f2() {
		System.out.println("B的f2方法");
		return "I am f2";
	}
	public String hello(String name) {
		System.out.println("A的hello方法");
		return "hello"+name;
	}
}
