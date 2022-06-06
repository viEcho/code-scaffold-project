package com.base.common.enums;

import lombok.Getter;

/**
 * @description: 响应码枚举
 * @author: echo
 * @date: 2021/5/22
 */
@Getter
public enum ResponseCodeEnum {

    SUCCESS(true, 1000,"Success"),
    UNKNOWN_REASON(false, 1001, "Unknown error"),
    BAD_SQL_GRAMMAR(false, 1002, "Sql syntax error"),
    JSON_PARSE_ERROR(false, 1003, "Json parsing error"),
    PARAM_ERROR(false, 1004, "Parameter is not correct"),
    FILE_UPLOAD_ERROR(false, 1005, "File upload occurs error"),
    EXCEL_DATA_IMPORT_ERROR(false, 1006, "Excel data import occurs error"),
    DATA_ADD_REPEAT_ERROR(false, 1007, "The data be added duplicated "),
    IMPORTANT_PARAMS_MISSED(false,1008,"Missing important parameters"),
    REFERENCED_BY_OTHERS_DATA(false,1009,"It has been referenced by other data. Operation is not allowed"),
    REDIS_KEY_NOT_NULL(false,1010,"Key in redis cannot be empty"),
    REDIS_KEY_NOT_EXIT(false,1011,"The key does not exist in redis"),
    ADD_REDIS_LOCK_FAIL(false,1012,"Redis locking failed"),
    GENERATE_DATA_FAIL(false,1013,"Generate data to redis failed"),
    EXIST_SAME_LEVEL_DATA(false,1014,"Data of the same level already exists");

    // 是否响应成功
    private Boolean success;
    // 响应的状态码
    private Integer code;
    // 响应的消息
    private String msg;

    ResponseCodeEnum(Boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
}
