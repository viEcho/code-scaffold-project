package com.base.common.global;

import com.base.common.enums.ResponseCodeEnum;
import com.base.common.vo.ResponseVO;
import com.base.common.enums.ResponseCodeEnum;
import com.base.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 全局异常处理
 * @author: echo
 * @date: 2021/5/22
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @description: 处理的所有的Exception
     * @author: echo
     * @date: 2021/5/22
     * @param: e
     * @return: com.vbills.modules.common.ResponseVO
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVO error(Exception e){
        e.printStackTrace();
        return ResponseVO.error();
    }

    /**
     * @description: 处理自己写的统一异常
     * @author: echo
     * @date: 2021/5/22
     * @param: e
     * @return: com.vbills.modules.common.ResponseVO
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseVO error(GlobalException e){
        log.error(ExceptionUtil.getStackMessage(e));//打印异常堆栈信息
        return ResponseVO.error().msg(e.getMessage()).code(e.getCode());
    }

    /**
     * @description: sql异常
     * @author: echo
     * @date: 2021/5/22
     * @param: e
     * @return: com.vbills.modules.common.ResponseVO
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public ResponseVO error(BadSqlGrammarException e){
        e.printStackTrace();
        return ResponseVO.definedResponseVO(ResponseCodeEnum.BAD_SQL_GRAMMAR);
    }
}
