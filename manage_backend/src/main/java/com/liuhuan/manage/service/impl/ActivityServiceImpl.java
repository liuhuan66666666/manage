package com.liuhuan.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.ActivityMapper;
import com.liuhuan.manage.model.dto.activity.ActivityQueryRequest;
import com.liuhuan.manage.model.entity.Activity;
import com.liuhuan.manage.model.vo.ActivityVO;
import com.liuhuan.manage.service.ActivityService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 活动表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param activity
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validActivity(Activity activity, boolean add) {
        ThrowUtils.throwIf(activity == null, ErrorCode.PARAMS_ERROR);
    }

    /**
     * 获取查询条件
     *
     * @param activityQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Activity> getQueryWrapper(ActivityQueryRequest activityQueryRequest) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        if (activityQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    /**
     * 获取活动表封装
     *
     * @param activity
     * @return
     */
    @Override
    public ActivityVO getActivityVO(Activity activity) {
        // 对象转封装类
        ActivityVO activityVO = ActivityVO.objToVo(activity);
        return activityVO;
    }




}
