package com.base.common.constant;

/**
 * @description: 公共常量类
 * @author: echo
 * @date: 2022/4/7
 */
public class CommonConstant {

    /**
     * 首页缓存数据key
     */
    public final static String R_KEY_HOME_PAGE_DATA = "homePageData:";

    /**
     * redis key默认过期时间
     * 30分钟
     */
    public final static long R_KEY_EXPIRE_TIME_DEFAULT = 30*60L;

    /**
     * redis key 24小时过期时间
     */
    public final static long R_KEY_EXPIRE_TIME_ONE_DAY = 24*60*60L;

    /**
     * 定时任务
     */
    public final static String DEFINED_TASK_USER = "SchedulerTask";
}
