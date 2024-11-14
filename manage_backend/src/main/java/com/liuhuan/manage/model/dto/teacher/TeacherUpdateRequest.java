package com.liuhuan.manage.model.dto.teacher;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新教师表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class TeacherUpdateRequest implements Serializable {


    /**
     * 主键id
     */
    private Long id;

    /**
     * 教师工号
     */
    private Integer teacherId;

    /**
     * 教师姓名
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
     * 所在系
     */
    private String department;

    /**
     * 教师状态 (0: inactive - 离职, 1: active - 在职)，默认在职
     */
    private Integer status;

    /**
     * 教师类型(0:校内指导老师 1:企业指导老师)
     */
    private Integer teacherType;

    private static final long serialVersionUID = 1L;
}