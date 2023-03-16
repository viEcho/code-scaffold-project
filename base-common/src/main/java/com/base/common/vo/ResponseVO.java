package com.base.common.vo;

import com.base.common.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 统一返回封装
 * @author: echo
 * @date: 2021/5/22
 */
@Data
@ApiModel(value = "全局的统一返回结果")
public class ResponseVO {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回的数据！")
    private Object data;

    public ResponseVO() {
        this.success = ResponseCodeEnum.SUCCESS.getSuccess();
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.msg= ResponseCodeEnum.SUCCESS.getMsg();
    }

    /**
     * @description: ok返回
     * @author: echo
     * @date: 2021/5/22
     * @param:
     * @return: com.vbills.modules.common.ResponseVO
     */
    public static ResponseVO ok(){
        return new ResponseVO()
        .success(ResponseCodeEnum.SUCCESS.getSuccess())
        .code(ResponseCodeEnum.SUCCESS.getCode())
        .msg(ResponseCodeEnum.SUCCESS.getMsg());
    }

    /**
     * @description: error返回
     * @author: echo
     * @date: 2021/5/22
     * @param:
     * @return: com.vbills.modules.common.ResponseVO
     */
    public static ResponseVO error(){
        return new ResponseVO()
        .success(ResponseCodeEnum.UNKNOWN_REASON.getSuccess())
        .code(ResponseCodeEnum.UNKNOWN_REASON.getCode())
        .msg(ResponseCodeEnum.UNKNOWN_REASON.getMsg());
    }

    /**
     * @description: definedResponseVO 自定义返回
     * @author: echo
     * @date: 2021/5/22
     * @param: ResponseVOCodeEnum
     * @return: com.vbills.modules.common.ResponseVO
     */
    public static ResponseVO definedResponseVO(ResponseCodeEnum responseCodeEnum){
        ResponseVO r = new ResponseVO();
        r.setSuccess(responseCodeEnum.getSuccess());
        r.setCode(responseCodeEnum.getCode());
        r.setMsg(responseCodeEnum.getMsg());
        return r;
    }
    public ResponseVO success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public ResponseVO msg(String msg){
        this.setMsg(msg);
        return this;
    }
    public ResponseVO code(Integer code){
        this.setCode(code);
        return this;
    }
    public ResponseVO data(Object obj){
        this.setData(obj);
        return this;
    }

}
