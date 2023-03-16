package com.datasource.sample.annotation.aspect;

import com.datasource.sample.annotation.DataSource;
import com.datasource.sample.utils.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/16
 */
@Aspect
@Order(1)
@Component
public class DBAspect {
    @Pointcut("@annotation(com.datasource.sample.annotation.DataSource)"
            + "|| @within(com.datasource.sample.annotation.DataSource)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource dataSource = getDBSource(point);

        if (Objects.nonNull(dataSource)) {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.dataSourceName());
        }

        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDBSource(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}
