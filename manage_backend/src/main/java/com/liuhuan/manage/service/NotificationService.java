package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.notification.NotificationQueryRequest;
import com.liuhuan.manage.model.entity.Notification;
import com.liuhuan.manage.model.vo.NotificationVO;

/**
 * 公共通知表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
public interface NotificationService extends IService<Notification> {

    /**
     * 校验数据
     *
     * @param notification
     * @param add 对创建的数据进行校验
     */
    void validNotification(Notification notification, boolean add);

    /**
     * 获取查询条件
     *
     * @param notificationQueryRequest
     * @return
     */
    QueryWrapper<Notification> getQueryWrapper(NotificationQueryRequest notificationQueryRequest);

    /**
     * 获取公共通知表封装
     *
     * @param notification
     * @return
     */
    NotificationVO getNotificationVO(Notification notification);

}
