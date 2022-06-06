package com.base.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 基础查询条件
 * @author: echo
 * @date: 2021/5/31
 */
@Data
public class BaseQuery implements Serializable {

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private int currentPage = 1;

    @TableField(exist = false)
    private int pageSize = 10;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;
}
