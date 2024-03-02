package com.base.common.global;

import com.base.common.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 自定义全局异常
 * @author: echo
 * @date: 2021/5/22
 */

@Data
@ApiModel(value = "全局异常")
public class GlobalException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;

    public GlobalException(){
        super();
    }

    /**
     * @description: 接收自定传递的状态码和异常消息
     * @author: echo
     * @date: 2021/5/22
     * @param: code
     * @param: message
     * @return:
     */
    public GlobalException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    /**
     * @description: 接收自定传递的状态码和异常消息
     * @author: echo
     * @date: 2021/5/25
     * @param: msg
     * @return:
     */
    public GlobalException(String msg){
        super(msg);
        this.code = ResponseCodeEnum.UNKNOWN_REASON_ERROR.getCode();
        this.msg = msg;
    }

    /**
     * @description: 接收枚举类型参数
     * @author: echo
     * @date: 2021/5/22
     * @param: responseCodeEnum
     * @return:
     */
    public GlobalException(ResponseCodeEnum responseCodeEnum){
        super(responseCodeEnum.getDesc());
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getDesc();
    }

    @Override
    public String toString() {
        return "GlobalException{code=" + code +" message=" + this.getMessage() +"}";
    }
}
