package com.liuhuan.manage.model.dto.teacher;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询教师表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherQueryRequest extends PageRequest implements Serializable {

    /*
     *
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
     * 二级学院
     */
    private String college;

    /**
     * 所在系
     */
    private String department;

    /**
     * 教师类型(0:校内指导老师 1:企业指导老师)
     */
    private Integer teacherType;

    private static final long serialVersionUID = 1L;
}