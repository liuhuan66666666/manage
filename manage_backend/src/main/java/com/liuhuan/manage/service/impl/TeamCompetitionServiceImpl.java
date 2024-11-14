package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.IndividualCompetitionMapper;
import com.liuhuan.manage.mapper.TeacherMapper;
import com.liuhuan.manage.mapper.TeamCompetitionMapper;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionAddRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionEditRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionQueryRequest;
import com.liuhuan.manage.model.entity.TeamCompetition;
import com.liuhuan.manage.model.vo.IndividualCompetitionVO;
import com.liuhuan.manage.model.vo.TeamCompetitionVO;
import com.liuhuan.manage.service.TeacherService;
import com.liuhuan.manage.service.TeamCompetitionService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 团队赛服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class TeamCompetitionServiceImpl extends ServiceImpl<TeamCompetitionMapper, TeamCompetition> implements TeamCompetitionService {

    @Resource
    private TeamCompetitionMapper teamCompetitionMapper;

    @Resource
    private TeacherService teacherService;


    @Resource
    private IndividualCompetitionMapper individualCompetitionMapper;



    /**
     * 校验数据
     *
     * @param teamCompetition
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validTeamCompetition(TeamCompetition teamCompetition, boolean add) {
        ThrowUtils.throwIf(teamCompetition == null, ErrorCode.PARAMS_ERROR);
    }

    /**
     * 获取查询条件
     *
     * @param teamCompetitionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<TeamCompetition> getQueryWrapper(TeamCompetitionQueryRequest teamCompetitionQueryRequest) {
        QueryWrapper<TeamCompetition> queryWrapper = new QueryWrapper<>();
        if (teamCompetitionQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    /**
     * 获取团队赛封装
     *
     * @param teamCompetition
     * @return
     */
    @Override
    public TeamCompetitionVO getTeamCompetitionVO(TeamCompetition teamCompetition) {
        // 对象转封装类
        TeamCompetitionVO teamCompetitionVO = TeamCompetitionVO.objToVo(teamCompetition);

        return teamCompetitionVO;
    }

    /**
     * 分页获取团队赛封装
     *
     * @param teamCompetitionPage
     * @return
     */
    @Override
    public Page<TeamCompetitionVO> getTeamCompetitionVOPage(Page<TeamCompetition> teamCompetitionPage) {
        List<TeamCompetition> teamCompetitionList = teamCompetitionPage.getRecords();
        Page<TeamCompetitionVO> teamCompetitionVOPage = new Page<>(teamCompetitionPage.getCurrent(), teamCompetitionPage.getSize(), teamCompetitionPage.getTotal());
        if (CollUtil.isEmpty(teamCompetitionList)) {
            return teamCompetitionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<TeamCompetitionVO> teamCompetitionVOList = teamCompetitionList.stream().map(teamCompetition -> {
            return TeamCompetitionVO.objToVo(teamCompetition);
        }).collect(Collectors.toList());


        teamCompetitionVOPage.setRecords(teamCompetitionVOList);
        return teamCompetitionVOPage;
    }

    @Override
    public long addTeamCompetition(TeamCompetitionAddRequest teamCompetitionAddRequest) {
        ThrowUtils.throwIf(teamCompetitionAddRequest == null, ErrorCode.PARAMS_ERROR);
        //将实体类与dto进行转换
        TeamCompetition teamCompetition = new TeamCompetition();
        BeanUtils.copyProperties(teamCompetitionAddRequest, teamCompetition);
        //数据校验
        this.validTeamCompetition(teamCompetition, true);
        //获取项目id
        String projectName = teamCompetitionAddRequest.getProject();
        Long projectId=teamCompetitionMapper.getProjectId(projectName);
        ThrowUtils.throwIf(projectId == null, ErrorCode.PARAMS_ERROR,"没有该项目");

        //填充项目id
        teamCompetition.setProject_id(projectId);
        teamCompetitionMapper.insert(teamCompetition);
        //id回显
        Long id = teamCompetition.getId();
        //教师竞赛关联表
        for (String teacher : teamCompetitionAddRequest.getTeacher()) {
            Long teacherId = teacherService.getTeacherId(teacher);
            individualCompetitionMapper.saveCompetitionTeacherRelation(id,teacherId);
        }
        return id;
    }

    @Override
    public void deleteTeamCompetition(Long id) {
        // 判断是否存在
        TeamCompetition oldTeamCompetition = teamCompetitionMapper.selectById(id);
        ThrowUtils.throwIf(oldTeamCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        // 团体赛表
        int i =teamCompetitionMapper.deleteById(id);
        //竞赛关联表
        Boolean result = individualCompetitionMapper.deleteCompetitionTeacherRelation(id);
        ThrowUtils.throwIf(!result&&i>0, ErrorCode.OPERATION_ERROR);
    }

    @Override
    public void editTeamCompetition(TeamCompetitionEditRequest teamCompetitionEditRequest) {
        Long id = teamCompetitionEditRequest.getId();
        //判断是否存在
        ThrowUtils.throwIf( teamCompetitionMapper.selectById(id)== null, ErrorCode.PARAMS_ERROR);
        TeamCompetition teamCompetition = new TeamCompetition();
        BeanUtils.copyProperties(teamCompetitionEditRequest, teamCompetition);
        //填充项目id
        String projectName = teamCompetitionEditRequest.getProject();
        Long projectId = teamCompetitionMapper.getProjectId(projectName);
        //团体赛表
        teamCompetition.setProject_id(projectId);
        teamCompetitionMapper.updateById(teamCompetition);
        //教师比赛关联表
        individualCompetitionMapper.deleteCompetitionTeacherRelation(id);
        for (String teacherName : teamCompetitionEditRequest.getTeacher()) {
            Long teacherId = teacherService.getTeacherId(teacherName);
            individualCompetitionMapper.saveCompetitionTeacherRelation(id,teacherId);
        }
    }

    @Override
    public Page<TeamCompetitionVO> listTeamCompetitionVOS(TeamCompetitionQueryRequest teamCompetitionQueryRequest) {

        int current = teamCompetitionQueryRequest.getCurrentPage();
        int  size = teamCompetitionQueryRequest.getPageSize();
        int offset = (current - 1) * size;
        String name = teamCompetitionQueryRequest.getName();//竞赛名称
        Integer year = teamCompetitionQueryRequest.getYear();//竞赛年份
        Integer status = teamCompetitionQueryRequest.getStatus();//竞赛状态


        List<TeamCompetitionVO> teamCompetitionVOS=teamCompetitionMapper.listTeamCompetitionVOS(offset,size,name,year,status);
        int count= teamCompetitionMapper.countTeamCompetitions(name,year,status);
        //构造分页对象
        Page<TeamCompetitionVO> teamCompetitionVOPage = new Page<>(offset, size);
        teamCompetitionVOPage.setTotal(count);
        teamCompetitionVOPage.setRecords(teamCompetitionVOS);
        return teamCompetitionVOPage;

    }

}
