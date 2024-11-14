package com.liuhuan.manage.model.dto.activity;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询活动表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityQueryRequest extends PageRequest implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;


    private static final long serialVersionUID = 1L;
}