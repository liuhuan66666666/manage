package com.liuhuan.manage.model.dto.notification;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 编辑公共通知表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class NotificationEditRequest implements Serializable {

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
    private Date date;

    /**
     *
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}