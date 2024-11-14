package com.liuhuan.manage.model.dto.member;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询成员表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MemberQueryRequest extends PageRequest implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String name;


    /**
     *
     */
    private String studentId;


    /**
     *
     */
    private String department;

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