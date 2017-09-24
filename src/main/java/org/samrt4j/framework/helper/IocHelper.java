package org.samrt4j.framework.helper;

import org.samrt4j.framework.annotation.Inject;
import org.samrt4j.framework.util.ArrayUtil;
import org.samrt4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class IocHelper {

    static {
        //获取 BeanMap
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //遍历 BeanMap
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            //从 BeanMap 中获取 Bean 类与 Bean 实例
            Class<?> beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();
            //获取 Bean 类中定义的所有成员变量
            Field[] beanFields = beanClass.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(beanFields)) {
                for (Field beanField : beanFields) {
                    //判断当前 Bean Field 是否有 Inject 注解
                    if (beanField.isAnnotationPresent(Inject.class)) {
                        //从 BeanMap 中获取 Bean Field 对应的实例
                        Class<?> beanFieldClass = beanField.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if (beanFieldInstance != null) {
                            //通过反射初始化 BeanField 的值
                            ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            }
        }
    }


}
