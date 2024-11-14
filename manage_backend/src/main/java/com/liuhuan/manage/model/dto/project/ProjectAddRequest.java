package com.liuhuan.manage.model.dto.project;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建项目表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class ProjectAddRequest implements Serializable {


    /**
     * 项目名称
     */
    private String name;

    /**
     * 源代码路径
     */
    private String source_code_path;

    /**
     * 项目状态 (0: not_started - 未启动, 1: in_progress - 进行中, 2: completed - 已完成)，默认进行中
     */
    private Integer status;

    /**
     * 项目启动日期
     */
    private Date startDate;

    /**
     * 项目组长
     */
    private String leader;


    /**
     * 项目成员
     */
    private List<String> student;

    /**
     * 项目指导老师
     */
    private List<String> teacher;

    private static final long serialVersionUID = 1L;
}