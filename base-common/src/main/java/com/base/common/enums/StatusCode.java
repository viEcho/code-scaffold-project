package com.base.common.enums;

/**
 * 状态代码
 *
 * @author echo
 * @date 2024/01/27
 */
public interface StatusCode {

    /**
     * 获取代码
     *
     * @return int
     */
    int getCode();

    /**
     * 获取desc
     *
     * @return {@link String}
     */
    String getDesc();
}
