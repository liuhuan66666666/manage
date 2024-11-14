package com.liuhuan.manage.model.dto.activity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 编辑活动表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class ActivityEditRequest implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Date date;

    /**
     *
     */
    private String location;


    private static final long serialVersionUID = 1L;
}