package com.liuhuan.manage.model.dto.teamCompetition;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑团队赛请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class TeamCompetitionEditRequest implements Serializable {

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
     * 报名截止日期
     */
    private Date registrationDeadline;

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