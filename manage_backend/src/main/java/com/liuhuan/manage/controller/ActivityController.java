package com.liuhuan.manage.controller;

import com.liuhuan.manage.annotation.AuthCheck;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.constant.UserConstant;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.activity.ActivityAddRequest;
import com.liuhuan.manage.model.dto.activity.ActivityEditRequest;
import com.liuhuan.manage.model.dto.activity.ActivityUpdateRequest;
import com.liuhuan.manage.model.entity.Activity;
import com.liuhuan.manage.model.vo.ActivityVO;
import com.liuhuan.manage.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Resource
    private ActivityService activityService;

    // region 增删改查

    /**
     * 创建活动表
     *
     * @param activityAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addActivity(@RequestBody ActivityAddRequest activityAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(activityAddRequest == null, ErrorCode.PARAMS_ERROR);
        //将实体类和 DTO 进行转换
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityAddRequest, activity);
        // 数据校验
        activityService.validActivity(activity, true);
        // 写入数据库
        boolean result = activityService.save(activity);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newActivityId = activity.getId();
        return ResultUtils.success(newActivityId);
    }

    /**
     * 删除活动表
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteActivity(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Activity oldActivity = activityService.getById(id);
        ThrowUtils.throwIf(oldActivity == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        // 操作数据库
        boolean result = activityService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新活动表（仅管理员可用）
     *
     * @param activityUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateActivity(@RequestBody ActivityUpdateRequest activityUpdateRequest) {
        if (activityUpdateRequest == null || activityUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityUpdateRequest, activity);
        // 数据校验
        activityService.validActivity(activity, false);
        // 判断是否存在
        long id = activityUpdateRequest.getId();
        Activity oldActivity = activityService.getById(id);
        ThrowUtils.throwIf(oldActivity == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = activityService.updateById(activity);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取活动表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<ActivityVO> getActivityVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Activity activity = activityService.getById(id);
        ThrowUtils.throwIf(activity == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(activityService.getActivityVO(activity));
    }

    @GetMapping("/list")
    public BaseResponse<List<ActivityVO>> getActivityListVO(){
        List<Activity> activities = activityService.list();
        List<ActivityVO> activityVOS = activities.stream().map(activity -> {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(activity, activityVO);
            return activityVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(activityVOS);
    }



    /**
     * 编辑活动表（给用户使用）
     *
     * @param activityEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editActivity(@RequestBody ActivityEditRequest activityEditRequest) {
        if (activityEditRequest == null || activityEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityEditRequest, activity);
        // 数据校验
        activityService.validActivity(activity, false);
        // 判断是否存在
        long id = activityEditRequest.getId();
        Activity oldActivity = activityService.getById(id);
        ThrowUtils.throwIf(oldActivity == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        // 操作数据库
        boolean result = activityService.updateById(activity);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
