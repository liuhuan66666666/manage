package com.liuhuan.manage.model.dto.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建活动表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class ActivityAddRequest implements Serializable {


    /**
     *
     */
    private String name;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date date;

    /**
     *
     */
    private String location;


    private static final long serialVersionUID = 1L;
}