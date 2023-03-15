package org.getyou123.Anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 在字段上使用
@Retention(RetentionPolicy.RUNTIME)// 运行时生效
public @interface DI {
}
