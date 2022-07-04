package com.aim.questionnaire.common.utils;

import java.lang.annotation.*;

/**
 * Excel单元格注解
 *
 */
// 作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）
@Target(ElementType.FIELD)
//作用：表示需要在什么级别保存该注释信息，用于描述注解的生命周期（即：被描述的注解在什么范围内有效）
@Retention(RetentionPolicy.RUNTIME)
// @Documented用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。Documented是一个标记注解，没有成员。
@Documented
public @interface ExcelCell {
    String value() default "";

    String type() default "";
}
