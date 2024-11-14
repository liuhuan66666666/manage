package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.TeamCompetition;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 团队赛视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class TeamCompetitionVO implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date registrationDeadline;

    /**
     * 竞赛项目名称
     */
    private String project;

    /**
     * 竞赛指导老师
     */
    private List<String> teacher;

    /**
     * 封装类转对象
     *
     * @param teamCompetitionVO
     * @return
     */
    public static TeamCompetition voToObj(TeamCompetitionVO teamCompetitionVO) {
        if (teamCompetitionVO == null) {
            return null;
        }
        TeamCompetition teamCompetition = new TeamCompetition();
        BeanUtils.copyProperties(teamCompetitionVO, teamCompetition);
        return teamCompetition;
    }

    /**
     * 对象转封装类
     *
     * @param teamCompetition
     * @return
     */
    public static TeamCompetitionVO objToVo(TeamCompetition teamCompetition) {
        if (teamCompetition == null) {
            return null;
        }
        TeamCompetitionVO teamCompetitionVO = new TeamCompetitionVO();
        BeanUtils.copyProperties(teamCompetition, teamCompetitionVO);
        return teamCompetitionVO;
    }
}
