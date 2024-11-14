package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.activity.ActivityQueryRequest;
import com.liuhuan.manage.model.entity.Activity;
import com.liuhuan.manage.model.vo.ActivityVO;

/**
 * 活动表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
public interface ActivityService extends IService<Activity> {

    /**
     * 校验数据
     *
     * @param activity
     * @param add 对创建的数据进行校验
     */
    void validActivity(Activity activity, boolean add);

    /**
     * 获取查询条件
     *
     * @param activityQueryRequest
     * @return
     */
    QueryWrapper<Activity> getQueryWrapper(ActivityQueryRequest activityQueryRequest);
    
    /**
     * 获取活动表封装
     *
     * @param activity
     * @return
     */
    ActivityVO getActivityVO(Activity activity);


}
