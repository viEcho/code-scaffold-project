package com.base.common.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.base.common.vo.BaseQuery;
import com.base.common.vo.Pagination;

import java.util.List;
import java.util.function.Supplier;

/**
 * @description: 自定义封装数据库操作
 * @author: echo
 * @date: 2022/5/19
 */
public class DBUtils {

    /**
     * @description: 自定义分页封装pageHelper
     * 此方法针对DO 转DTO的场景
     * @author: echo
     * @date: 2022/5/21
     * @param: supplier
     * @param: baseQuery
     * @param: elementType
     * @return: com.vbills.modules.entity.common.Pagination<T>
     */
    public static <T,Q extends BaseQuery> Pagination<T> getPagination(Supplier<List<T>> supplier, Q query,Class<T> elementType){
        PageHelper.startPage(query.getCurrentPage(),query.getPageSize());
        List<T> pageList =  supplier.get();
        PageInfo<T> pageInfo = new PageInfo<>(pageList);

        Pagination<T> pagination = new Pagination<>();
        pagination.setList(pageList,elementType);
        pagination.setPageNum(query.getCurrentPage());
        pagination.setPageSize(query.getPageSize());
        pagination.setTotal(Integer.valueOf(String.valueOf(pageInfo.getTotal())));
        return pagination;
    }
    /**
     * @description:
     * @author: echo
     * @date: 2022/5/21
     * @param: supplier
     * @param: baseQuery
     * @return: com.vbills.modules.entity.common.Pagination<T>
     */
    public static <T,Q extends BaseQuery> Pagination<T> getPagination(Supplier<List<T>> supplier, Q query){
        PageHelper.startPage(query.getCurrentPage(),query.getPageSize());
        List<T> pageList = supplier.get();
        PageInfo<T> pageInfo = new PageInfo<>(pageList);

        Pagination<T> pagination = new Pagination<>();
        pagination.setList(pageList);
        pagination.setPageNum(query.getCurrentPage());
        pagination.setPageSize(query.getPageSize());
        pagination.setTotal(Integer.valueOf(String.valueOf(pageInfo.getTotal())));
        return pagination;
    }
}
