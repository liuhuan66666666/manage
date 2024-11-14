package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.IndividualCompetition;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 个人竞赛表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class IndividualCompetitionVO implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date registrationDeadline;

    /**
     * 竞赛结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date endDeadline;


    /**
     * 竞赛学生
     */
     private String student;

    /**
     *指导老师
     */

     private List<String> teacher;

    /**
     * 封装类转对象
     *
     * @param individualCompetitionVO
     * @return
     */
    public static IndividualCompetition voToObj(IndividualCompetitionVO individualCompetitionVO) {
        if (individualCompetitionVO == null) {
            return null;
        }
        IndividualCompetition individualCompetition = new IndividualCompetition();
        BeanUtils.copyProperties(individualCompetitionVO, individualCompetition);
        return individualCompetition;
    }

    /**
     * 对象转封装类
     *
     * @param individualCompetition
     * @return
     */
    public static IndividualCompetitionVO objToVo(IndividualCompetition individualCompetition) {
        if (individualCompetition == null) {
            return null;
        }
        IndividualCompetitionVO individualCompetitionVO = new IndividualCompetitionVO();
        BeanUtils.copyProperties(individualCompetition, individualCompetitionVO);
        return individualCompetitionVO;
    }
}
