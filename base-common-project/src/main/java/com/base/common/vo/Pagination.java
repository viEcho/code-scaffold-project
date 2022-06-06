package com.base.common.vo;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.List;

/**
 * @description: 分页实体
 * @author: echo
 * @date: 2022/5/18
 */
@Data
public class Pagination<T> {

    private int pageNum;
    private int pageSize;
    private List<T> list;
    private int total;

    public void setList(List<?> pageList,Class<T> elementType){
        List<T> list = JSONUtil.toList(JSONUtil.parseArray(pageList), elementType);
        this.list = list;
    }
    public void setList(List<?> pageList){
        this.list = list;
    }
}
