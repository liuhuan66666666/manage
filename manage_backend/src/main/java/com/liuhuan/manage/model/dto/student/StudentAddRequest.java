package com.liuhuan.manage.model.dto.student;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建学生表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class StudentAddRequest implements Serializable {


    /**
     * 学生学号
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 二级学院
     */
    private String college;

    /**
     * 专业名称
     */
    private String major;



    private static final long serialVersionUID = 1L;
}