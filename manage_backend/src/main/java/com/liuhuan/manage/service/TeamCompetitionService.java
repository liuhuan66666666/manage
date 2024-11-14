package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionAddRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionEditRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionQueryRequest;
import com.liuhuan.manage.model.entity.TeamCompetition;
import com.liuhuan.manage.model.vo.TeamCompetitionVO;

/**
 * 团队赛服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface TeamCompetitionService extends IService<TeamCompetition> {

    /**
     * 校验数据
     *
     * @param teamCompetition
     * @param add 对创建的数据进行校验
     */
    void validTeamCompetition(TeamCompetition teamCompetition, boolean add);

    /**
     * 获取查询条件
     *
     * @param teamCompetitionQueryRequest
     * @return
     */
    QueryWrapper<TeamCompetition> getQueryWrapper(TeamCompetitionQueryRequest teamCompetitionQueryRequest);
    
    /**
     * 获取团队赛封装
     *
     * @param teamCompetition
     * @return
     */
    TeamCompetitionVO getTeamCompetitionVO(TeamCompetition teamCompetition);

    /**
     * 分页获取团队赛封装
     *
     * @param teamCompetitionPage
     * @return
     */
    Page<TeamCompetitionVO> getTeamCompetitionVOPage(Page<TeamCompetition> teamCompetitionPage);

    long addTeamCompetition(TeamCompetitionAddRequest teamCompetitionAddRequest);

    void deleteTeamCompetition(Long id);

    void editTeamCompetition(TeamCompetitionEditRequest teamCompetitionEditRequest);

    Page<TeamCompetitionVO> listTeamCompetitionVOS(TeamCompetitionQueryRequest teamCompetitionQueryRequest);
}
