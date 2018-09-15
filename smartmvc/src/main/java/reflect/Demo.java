package reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 *@Retention是元注解,即用来解释其他注解的注解
 *注解生存时间有三个:
 *source:注解只存于源码里面
 *class:注解只保留字节码文件里面
 *runtime:直接保留到运行时
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Demo {
	//注解的属性名如果是value,则赋值时可以不写属性名
	public  String value();
	
}
