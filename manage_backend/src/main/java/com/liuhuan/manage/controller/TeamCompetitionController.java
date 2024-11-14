package com.liuhuan.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuhuan.manage.annotation.AuthCheck;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.constant.UserConstant;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionAddRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionEditRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionQueryRequest;
import com.liuhuan.manage.model.dto.teamCompetition.TeamCompetitionUpdateRequest;
import com.liuhuan.manage.model.entity.TeamCompetition;
import com.liuhuan.manage.model.vo.TeamCompetitionVO;
import com.liuhuan.manage.service.TeamCompetitionService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 团队赛接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/teamCompetition")
@Slf4j
public class TeamCompetitionController {

    @Resource
    private TeamCompetitionService teamCompetitionService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建团队赛
     *
     * @param teamCompetitionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addTeamCompetition(@RequestBody TeamCompetitionAddRequest teamCompetitionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(teamCompetitionAddRequest == null, ErrorCode.PARAMS_ERROR);
        long newTeamCompetitionId=teamCompetitionService.addTeamCompetition(teamCompetitionAddRequest);
        return ResultUtils.success(newTeamCompetitionId);
    }

    /**
     * 删除团队赛
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeamCompetition(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        teamCompetitionService.deleteTeamCompetition(deleteRequest.getId());
        return ResultUtils.success(true);
    }

    /**
     * 更新团队赛（仅管理员可用）
     *
     * @param teamCompetitionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateTeamCompetition(@RequestBody TeamCompetitionUpdateRequest teamCompetitionUpdateRequest) {
        if (teamCompetitionUpdateRequest == null || teamCompetitionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        TeamCompetition teamCompetition = new TeamCompetition();
        BeanUtils.copyProperties(teamCompetitionUpdateRequest, teamCompetition);
        // 数据校验
        teamCompetitionService.validTeamCompetition(teamCompetition, false);
        // 判断是否存在
        long id = teamCompetitionUpdateRequest.getId();
        TeamCompetition oldTeamCompetition = teamCompetitionService.getById(id);
        ThrowUtils.throwIf(oldTeamCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = teamCompetitionService.updateById(teamCompetition);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取团队赛（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<TeamCompetitionVO> getTeamCompetitionVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        TeamCompetition teamCompetition = teamCompetitionService.getById(id);
        ThrowUtils.throwIf(teamCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(teamCompetitionService.getTeamCompetitionVO(teamCompetition));
    }

    /**
     * 分页获取团队赛列表（仅管理员可用）
     *
     * @param teamCompetitionQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<TeamCompetition>> listTeamCompetitionByPage(@RequestBody TeamCompetitionQueryRequest teamCompetitionQueryRequest) {
        long current = teamCompetitionQueryRequest.getCurrentPage();
        long size = teamCompetitionQueryRequest.getPageSize();
        // 查询数据库

        Page<TeamCompetition> teamCompetitionPage = teamCompetitionService.page(new Page<>(current, size),
                teamCompetitionService.getQueryWrapper(teamCompetitionQueryRequest));
        return ResultUtils.success(teamCompetitionPage);
    }

    /**
     * 分页获取团队赛列表（封装类）
     *
     * @param teamCompetitionQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<TeamCompetitionVO>> listTeamCompetitionVOByPage(@RequestBody TeamCompetitionQueryRequest teamCompetitionQueryRequest) {
         Page<TeamCompetitionVO> teamCompetitionPage=teamCompetitionService.listTeamCompetitionVOS(teamCompetitionQueryRequest);
         // 获取封装类
         return ResultUtils.success(teamCompetitionPage);
    }


    /**
     * 编辑团队赛（给用户使用）
     *
     * @param teamCompetitionEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editTeamCompetition(@RequestBody TeamCompetitionEditRequest teamCompetitionEditRequest) {
        if (teamCompetitionEditRequest == null || teamCompetitionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        teamCompetitionService.editTeamCompetition(teamCompetitionEditRequest);
        return ResultUtils.success(true);
    }

    // endregion
}
