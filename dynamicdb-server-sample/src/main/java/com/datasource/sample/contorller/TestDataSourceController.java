package com.datasource.sample.contorller;

import com.base.common.vo.ResponseVO;
import com.datasource.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 数据源测试 接口类
 * @author: echo
 * @date: 2023/3/16
 */
@RestController
@RequestMapping("/dataSource")
@Slf4j
public class TestDataSourceController {

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 查询计数
     *
     * @return {@link ResponseVO}
     */
    @GetMapping("/queryUserCount")
    ResponseVO queryCount() {
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("master", userService.queryMasterUserCount());
        countMap.put("slave", userService.querySlaveUserCount());
        return new ResponseVO(countMap);
    }
}
