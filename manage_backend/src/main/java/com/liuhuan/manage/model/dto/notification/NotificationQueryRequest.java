package com.liuhuan.manage.model.dto.notification;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询公共通知表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationQueryRequest extends PageRequest implements Serializable {

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