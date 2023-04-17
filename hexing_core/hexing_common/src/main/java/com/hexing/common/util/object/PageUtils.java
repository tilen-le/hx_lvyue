package com.hexing.common.util.object;

import com.hexing.common.pojo.PageParam;

/**
 * {@link PageParam} 工具类
 *
 * @author firerock_tech
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
