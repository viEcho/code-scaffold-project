package com.base.common.enums;

import lombok.Getter;

/**
 * @description: 响应码枚举
 * @author: echo
 * @date: 2021/5/22
 */
@Getter
public enum ResponseCodeEnum implements StatusCode {

    /**
     * 系统异常及公共普通异常 1000-1199
     */
    SUCCESS(true, 1000, I18nResponseEnum.SUCCESS_I18N),
    VALIDATE_ERROR(false, 1001, I18nResponseEnum.VALIDATE_ERROR_I18N),
    RIGHT_VALIDATE_ERROR(false, 1002,I18nResponseEnum.RIGHT_VALIDATE_ERROR_I18N),
    UNKNOWN_REASON_ERROR(false, 1003, I18nResponseEnum.UNKNOWN_REASON_ERROR_I18N),
    PARAM_ERROR(false, 1004, I18nResponseEnum.PARAM_ERROR_I18N),
    NO_NEED_HANDLE_ERROR(false, 1005, I18nResponseEnum.NO_NEED_HANDLE_ERROR_I18N),

    /**
     * 自定义异常 1200-1500
     */
    BAD_SQL_GRAMMAR(false,1200, I18nResponseEnum.BAD_SQL_GRAMMAR_I18N),
    JSON_PARSE_ERROR(false,1202, I18nResponseEnum.JSON_PARSE_ERROR_I18N),
    FILE_UPLOAD_ERROR(false, 1203, I18nResponseEnum.FILE_UPLOAD_ERROR_I18N),
    EXCEL_DATA_IMPORT_ERROR(false, 1204, I18nResponseEnum.EXCEL_DATA_IMPORT_I18N),
    DATA_ADD_REPEAT_ERROR(false, 1205, I18nResponseEnum.DATA_ADD_REPEAT_I18N),
    IMPORTANT_PARAMS_MISSED(false, 1206, I18nResponseEnum.IMPORTANT_PARAMS_MISSED_I18N),
    REFERENCED_BY_OTHERS_DATA(false, 1207, I18nResponseEnum.REFERENCED_BY_OTHERS_I18N),
    REDIS_KEY_NOT_NULL(false, 1208, I18nResponseEnum.REDIS_KEY_NOT_NULL_I18N),
    REDIS_KEY_NOT_EXIST(false, 1209, I18nResponseEnum.REDIS_KEY_NOT_EXIST_I18N),
    ADD_REDIS_LOCK_FAIL(false, 1210, I18nResponseEnum.ADD_REDIS_LOCK_FAIL_I18N),
    GENERATE_DATA_FAIL(false, 1211, I18nResponseEnum.GENERATE_DATA_FAIL_I18N),
    EXIST_SAME_LEVEL_DATA(false, 1212, I18nResponseEnum.EXIST_SAME_LEVEL_DATA_I18N);

    // 是否响应成功
    private Boolean success;
    // 响应的状态码
    private Integer code;
    // 国际化枚举
    private I18nResponseEnum i18nResponseEnum;

    /**
     * 响应代码枚举
     *
     * @param success          成功
     * @param code             密码
     * @param i18nResponseEnum i18n响应枚举
     */
    ResponseCodeEnum(Boolean success, Integer code, I18nResponseEnum i18nResponseEnum) {
        this.success = success;
        this.code = code;
        this.i18nResponseEnum = i18nResponseEnum;
    }

    /**
     * 获取desc
     *
     * @return {@link String}
     */
    @Override
    public int getCode() {
        // 后面这里做了登录的话，可以根据前台传递的国际化类型动态切换返回
        return this.code;
    }

    /**
     * 获取desc
     *
     * @return {@link String}
     */
    @Override
    public String getDesc() {
        // 后面这里做了登录的话，可以根据前台传递的国际化类型动态切换返回
        return this.i18nResponseEnum.getMsgCn();
    }
}
