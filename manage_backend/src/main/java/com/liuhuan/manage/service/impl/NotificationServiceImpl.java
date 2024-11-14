package com.liuhuan.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.NotificationMapper;
import com.liuhuan.manage.model.dto.notification.NotificationQueryRequest;
import com.liuhuan.manage.model.entity.Notification;
import com.liuhuan.manage.model.vo.NotificationVO;
import com.liuhuan.manage.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 公共通知表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Service
@Slf4j
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {


    /**
     * 校验数据
     *
     * @param notification
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validNotification(Notification notification, boolean add) {
        ThrowUtils.throwIf(notification == null, ErrorCode.PARAMS_ERROR);
        // todo 从对象中取值
        String title = notification.getTitle();
        // 创建数据时，参数不能为空
        if (add) {
            // todo 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(title), ErrorCode.PARAMS_ERROR);
        }
        // 修改数据时，有参数则校验
        // todo 补充校验规则
        if (StringUtils.isNotBlank(title)) {
            ThrowUtils.throwIf(title.length() > 80, ErrorCode.PARAMS_ERROR, "标题过长");
        }
    }

    /**
     * 获取查询条件
     *
     * @param notificationQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Notification> getQueryWrapper(NotificationQueryRequest notificationQueryRequest) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        if (notificationQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    /**
     * 获取公共通知表封装
     *
     * @param notification
     * @param request
     * @return
     */
    @Override
    public NotificationVO getNotificationVO(Notification notification) {
        // 对象转封装类
        NotificationVO notificationVO = NotificationVO.objToVo(notification);
        return notificationVO;
    }

}
