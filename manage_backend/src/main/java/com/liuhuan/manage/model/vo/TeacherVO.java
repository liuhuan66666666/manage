package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.Teacher;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class TeacherVO implements Serializable {

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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date createTime;

    /**
     * 教师状态 (0: inactive - 离职, 1: active - 在职)，默认在职
     */
    private Integer status;

    /**
     * 教师类型(0:校内指导老师 1:企业指导老师)
     */
    private Integer teacherType;
    /**
     * 封装类转对象
     *
     * @param teacherVO
     * @return
     */
    public static Teacher voToObj(TeacherVO teacherVO) {
        if (teacherVO == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherVO, teacher);
        return teacher;
    }

    /**
     * 对象转封装类
     *
     * @param teacher
     * @return
     */
    public static TeacherVO objToVo(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(teacher, teacherVO);
        return teacherVO;
    }
}
