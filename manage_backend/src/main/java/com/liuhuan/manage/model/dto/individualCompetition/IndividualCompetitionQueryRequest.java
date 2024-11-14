package com.liuhuan.manage.model.dto.individualCompetition;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询个人竞赛表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IndividualCompetitionQueryRequest extends PageRequest implements Serializable {


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
     * 竞赛状态 (0: inactive - 已经结束, 1: 进行中 )，默认进行中
     */
    private Integer status;

    /**
     * 报名截止日期
     */
    private Date registrationDeadline;

    /**
     * 竞赛结束日期
     */
    private Date endDeadline;


    /**
     * 竞赛学生
     */
    private String student;

    /**
     *指导老师
     */

    private List<String> teacher;


    private static final long serialVersionUID = 1L;
}