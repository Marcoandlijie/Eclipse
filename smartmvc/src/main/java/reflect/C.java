package reflect;

public class C {
	@Demo(value="隔壁小红")
	public void f1() {
		System.out.println("C的f1方法");
	}
	public String f2() {
		System.out.println("C的f2方法");
		return "I am f2";
	}
	@Demo(value="小蓝")
	public void bala() {
		System.out.println("C的bala方法");
	}


}
