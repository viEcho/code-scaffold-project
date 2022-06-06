package com.base.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @description: 自定义工具类
 * @author: echo
 * @date: 2021/6/30
 */
public class DefinedUtil {

    /**
     * @description: 按key 去重，key不能为null
     * @author: echo
     * @date: 2021/7/8
     * @param: keyExtractor
     * @return: java.util.function.Predicate<T>
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * @description: 生成uuid 并去除"-"
     * @author: echo
     * @date: 2021/7/8
     * @param:
     * @return: java.lang.String
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * @description: 万分位 分割计数且保留两位小数
     * @author: echo
     * @date: 2021/7/8
     * @param: initBigDecimal
     * @return: java.lang.String
     */
    public static String spillWithTenThousand(BigDecimal initBigDecimal){
        if(null == initBigDecimal) return "0";

        DecimalFormat decimalFormat = new DecimalFormat("#,####.##");
        String format = decimalFormat.format(initBigDecimal);
        return format;
    }

}
