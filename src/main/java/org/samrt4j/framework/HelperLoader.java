package org.samrt4j.framework;

import org.samrt4j.framework.helper.BeanHelper;
import org.samrt4j.framework.helper.ClassHelper;
import org.samrt4j.framework.helper.ControllerHelper;
import org.samrt4j.framework.helper.IocHelper;
import org.samrt4j.framework.util.ClassUtil;

/**
 * 加载响应的 helper 类
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
