package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: sky-take-out
 * @package: com.sky.annaotation
 * @className: AutoFill
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/16 22:05
 * @version: 1.0
 */
//自定义注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OperationType value();
}
