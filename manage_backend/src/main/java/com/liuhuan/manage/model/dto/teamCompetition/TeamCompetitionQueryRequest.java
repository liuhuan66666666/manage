package com.liuhuan.manage.model.dto.teamCompetition;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询团队赛请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeamCompetitionQueryRequest extends PageRequest implements Serializable {
    /**
     * 竞赛ID
     */
    private Long id;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛年份
     */
    private Integer year;

    /**
     * 竞赛类型
     */
    private String type;

    /**
     * 竞赛状态 (0: inactive - 已经结束, 1: ongoing - 进行中)，默认进行中
     */
    private Integer status;

    /**
     * 竞赛项目名称
     */
    private String project;

    /**
     * 竞赛指导老师
     */
    private List<String> teacher;

    private static final long serialVersionUID = 1L;
}