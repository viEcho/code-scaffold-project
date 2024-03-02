package com.base.common.vo;

import com.base.common.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @description: 统一返回封装
 * @author: echo
 * @date: 2021/5/22
 */
@Data
@ApiModel(value = "全局的统一返回结果")
@AllArgsConstructor
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
        this.msg= ResponseCodeEnum.SUCCESS.getDesc();
    }

    public ResponseVO(Object data){
        this.success = ResponseCodeEnum.SUCCESS.getSuccess();
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.msg = ResponseCodeEnum.SUCCESS.getDesc();
        this.data = data;
    }

    public ResponseVO(int code,String msg,Object data){
        this.success = ResponseCodeEnum.SUCCESS.getSuccess();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public  ResponseVO (ResponseCodeEnum responseCodeEnum){
        this.success = responseCodeEnum.getSuccess();
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getDesc();
    }

    public  ResponseVO (ResponseCodeEnum responseCodeEnum,Object data){
        this.success = responseCodeEnum.getSuccess();
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getDesc();
        this.data = data;
    }

    /**
     * @description: ok返回
     * @author: echo
     * @date: 2021/5/22
     * @param:
     * @return: com.vbills.modules.common.ResponseVO
     */
    public static ResponseVO success(){
        return new ResponseVO()
        .success(ResponseCodeEnum.SUCCESS.getSuccess())
        .code(ResponseCodeEnum.SUCCESS.getCode())
        .msg(ResponseCodeEnum.SUCCESS.getDesc());
    }



    /**
     * @description: error返回
     * @author: echo
     * @date: 2021/5/22
     * @param:
     * @return: com.vbills.modules.common.ResponseVO
     */
    public static ResponseVO fail(){
        return new ResponseVO()
        .success(ResponseCodeEnum.UNKNOWN_REASON_ERROR.getSuccess())
        .code(ResponseCodeEnum.UNKNOWN_REASON_ERROR.getCode())
        .msg(ResponseCodeEnum.UNKNOWN_REASON_ERROR.getDesc());
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
