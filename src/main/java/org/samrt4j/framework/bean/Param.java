package org.samrt4j.framework.bean;

import org.samrt4j.framework.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {

    private Map<String, Object> paramMap;

    /**
     * 获取 long 型参数值
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     * @return
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
