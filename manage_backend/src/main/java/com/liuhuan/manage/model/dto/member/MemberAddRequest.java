package com.liuhuan.manage.model.dto.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建成员表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class MemberAddRequest implements Serializable {


    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer gender;

    /**
     *
     */
    private String studentId;

    /**
     *
     */
    private String contact;

    /**
     *
     */
    private Date joinDate;

    /**
     *
     */
    private String department;

    /**
     *
     */
    private String position;

    /**
     *
     */
    private String major;

    /**
     *
     */
    private String grade;


    private static final long serialVersionUID = 1L;
}