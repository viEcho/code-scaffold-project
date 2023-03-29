package com.tess4j.sample.controller;

import com.base.common.utils.ExceptionUtil;
import com.base.common.vo.ResponseVO;
import com.tess4j.sample.entity.ImageVO;
import com.tess4j.sample.service.Tess4jService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/29
 */
@RequestMapping("/tess4j")
@RestController
@Slf4j
public class Tess4jController {

    @Autowired
    private Tess4jService tess4jService;

    @PostMapping("/imageToChar")
    ResponseVO turnImageToChar(@RequestBody ImageVO imageVO){
        ResponseVO vo = new ResponseVO();
        try {
            vo.setData(tess4jService.getChar(imageVO.getImagePath()));
        } catch (Exception e) {
            log.error("Tess4jController turnImageToChar occurs error：{}",e);
            ExceptionUtil.checkResponse(e,vo);
        }
        return vo;
    }
}
