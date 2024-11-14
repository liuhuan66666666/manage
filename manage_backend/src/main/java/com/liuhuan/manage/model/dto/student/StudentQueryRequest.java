package com.liuhuan.manage.model.dto.student;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询学生表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 学生学号
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 二级学院
     */
    private String college;

    /**
     * 专业名称
     */
    private String major;

    /**
     * 学生状态 (0: inactive - 毕业或离校, 1: active - 在校)，默认在校
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}