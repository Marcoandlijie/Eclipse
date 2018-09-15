package base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/*
 * 自定义注解:用于处理请求路径与处理器的对应关系
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	public String value();
}
