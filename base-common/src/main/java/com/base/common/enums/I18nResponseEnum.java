package com.base.common.enums;

import lombok.Getter;

/**
 * i18n响应枚举
 *
 * @author echo
 * @date 2024/01/27
 */
@Getter
public enum I18nResponseEnum {
    SUCCESS_I18N("Success", "成功"),
    VALIDATE_ERROR_I18N("Success", "成功"),
    RIGHT_VALIDATE_ERROR_I18N("Right validate fail","权限校验异常"),
    UNKNOWN_REASON_ERROR_I18N("Unknown reason error", "成功"),
    PARAM_ERROR_I18N("Param error", "参数异常"),
    NO_NEED_HANDLE_ERROR_I18N("No need handle error","无需处理异常"),

    BAD_SQL_GRAMMAR_I18N("Bad sql grammar", "sql语法错误"),
    JSON_PARSE_ERROR_I18N("Json parse error", "json转换异常"),
    FILE_UPLOAD_ERROR_I18N("File upload error", "文件上传异常"),
    EXCEL_DATA_IMPORT_I18N("Excel data import error", "excel数据导入异常"),
    DATA_ADD_REPEAT_I18N("Data add repeat error", "数据重复添加异常"),
    IMPORTANT_PARAMS_MISSED_I18N("Important params missed error", "重要参数缺失异常"),
    REFERENCED_BY_OTHERS_I18N("Referenced by others data error", "被其他数据引用，无法操作异常"),
    REDIS_KEY_NOT_NULL_I18N("Redis key not null", "redis key不能为空"),
    REDIS_KEY_NOT_EXIST_I18N("Redis key not exist", "redis key不存在"),
    ADD_REDIS_LOCK_FAIL_I18N("Add redis lock fail", "redis 加锁失败"),
    GENERATE_DATA_FAIL_I18N("Generate data fail", "生成数据失败"),
    EXIST_SAME_LEVEL_DATA_I18N("Exist same level data", "已存在相同级别数据"),

    ;
    // 英文
    private String msgEn;
    // 中文
    private String msgCn;

    /**
     * i18n响应枚举
     *
     * @param msgEn 消息en
     * @param msgCn 消息cn
     */
    I18nResponseEnum(String msgEn, String msgCn) {
        this.msgEn = msgEn;
        this.msgCn = msgCn;
    }
}
