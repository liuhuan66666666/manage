package com.liuhuan.manage.model.dto.project;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询项目表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectQueryRequest extends PageRequest implements Serializable {


    /**
     * 项目ID
     */
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目状态 (0: not_started - 未启动, 1: in_progress - 进行中, 2: completed - 已完成)，默认进行中
     */
    private Integer status;

    /**
     * 项目组长
     */
    private String leader;


    /**
     * 项目指导老师
     */
    private List<String> teacher;

    private static final long serialVersionUID = 1L;
}