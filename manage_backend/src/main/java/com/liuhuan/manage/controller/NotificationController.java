package com.liuhuan.manage.controller;

import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.notification.NotificationAddRequest;
import com.liuhuan.manage.model.dto.notification.NotificationEditRequest;
import com.liuhuan.manage.model.entity.Notification;
import com.liuhuan.manage.model.vo.NotificationVO;
import com.liuhuan.manage.service.NotificationService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公共通知表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@RestController
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建公共通知表
     *
     * @param notificationAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addNotification(@RequestBody NotificationAddRequest notificationAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(notificationAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 将实体类和 DTO 进行转换
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationAddRequest, notification);
        // 数据校验
        notificationService.validNotification(notification, true);
        // 写入数据库
        boolean result = notificationService.save(notification);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newNotificationId = notification.getId();
        return ResultUtils.success(newNotificationId);
    }

    /**
     * 删除公共通知表
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteNotification(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Notification oldNotification = notificationService.getById(id);
        ThrowUtils.throwIf(oldNotification == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        // 操作数据库
        boolean result = notificationService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取公共通知表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<NotificationVO> getNotificationVOById(Long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Notification notification = notificationService.getById(id);
        ThrowUtils.throwIf(notification == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(notificationService.getNotificationVO(notification));
    }


    @GetMapping("/getList")
    public BaseResponse<List<NotificationVO>> getNotificationListVO(){
        List<Notification> notifications = notificationService.list();
        List<NotificationVO> notificationVOS = notifications.stream().map(notification -> {
            NotificationVO notificationVO = new NotificationVO();
            BeanUtils.copyProperties(notification, notificationVO);
            return notificationVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(notificationVOS);
    }





    /**
     * 编辑公共通知表（给用户使用）
     *
     * @param notificationEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editNotification(@RequestBody NotificationEditRequest notificationEditRequest) {
        if (notificationEditRequest == null || notificationEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationEditRequest, notification);
        // 数据校验
        notificationService.validNotification(notification, false);
        // 判断是否存在
        long id = notificationEditRequest.getId();
        Notification oldNotification = notificationService.getById(id);
        ThrowUtils.throwIf(oldNotification == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑

        // 操作数据库
        boolean result = notificationService.updateById(notification);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
