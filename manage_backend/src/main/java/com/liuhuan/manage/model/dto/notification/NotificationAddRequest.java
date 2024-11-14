package com.liuhuan.manage.model.dto.notification;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建公共通知表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class NotificationAddRequest implements Serializable {

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
    private Integer status;

    private static final long serialVersionUID = 1L;
}