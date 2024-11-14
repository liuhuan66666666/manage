package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.Student;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class StudentVO implements Serializable {

    /*
     *主键id
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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date createTime;

    /**
     * 学生状态 (0: inactive - 毕业或离校, 1: active - 在校)，默认在校
     */
    private Integer status;


    /**
     * 封装类转对象
     *
     * @param studentVO
     * @return
     */
    public static Student voToObj(StudentVO studentVO) {
        if (studentVO == null) {
            return null;
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentVO, student);;
        return student;
    }

    /**
     * 对象转封装类
     *
     * @param student
     * @return
     */
    public static StudentVO objToVo(Student student) {
        if (student == null) {
            return null;
        }
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(student, studentVO);
        return studentVO;
    }
}
