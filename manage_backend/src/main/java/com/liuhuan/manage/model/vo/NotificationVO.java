package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.Notification;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共通知表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class NotificationVO implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String content;

    /**
     *
     */

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date date;

    /**
     *
     */
    private Integer status;

    /**
     * 封装类转对象
     *
     * @param notificationVO
     * @return
     */
    public static Notification voToObj(NotificationVO notificationVO) {
        if (notificationVO == null) {
            return null;
        }
        Notification notification = new Notification();
        return notification;
    }

    /**
     * 对象转封装类
     *
     * @param notification
     * @return
     */
    public static NotificationVO objToVo(Notification notification) {
        if (notification == null) {
            return null;
        }
        NotificationVO notificationVO = new NotificationVO();
        BeanUtils.copyProperties(notification, notificationVO);
        return notificationVO;
    }
}
