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
import com.liuhuan.manage.model.dto.project.ProjectAddRequest;
import com.liuhuan.manage.model.dto.project.ProjectEditRequest;
import com.liuhuan.manage.model.dto.project.ProjectQueryRequest;
import com.liuhuan.manage.model.dto.project.ProjectUpdateRequest;
import com.liuhuan.manage.model.entity.Project;
import com.liuhuan.manage.model.vo.ProjectVO;
import com.liuhuan.manage.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Resource
    private ProjectService projectService;


    // region 增删改查

    /**
     * 创建项目表
     *
     * @param projectAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addProject(@RequestBody ProjectAddRequest projectAddRequest) {
        ThrowUtils.throwIf(projectAddRequest == null, ErrorCode.PARAMS_ERROR);
        Long newProjectId= projectService.addProject(projectAddRequest);
        return ResultUtils.success(newProjectId);

    }

    /**
     * 删除项目表
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProject(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Project oldProject = projectService.getById(id);
        ThrowUtils.throwIf(oldProject == null, ErrorCode.NOT_FOUND_ERROR);
        Boolean result = projectService.deleteProject(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新项目表（仅管理员可用）
     *
     * @param projectUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateProject(@RequestBody ProjectUpdateRequest projectUpdateRequest) {
        if (projectUpdateRequest == null || projectUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Project project = new Project();
        BeanUtils.copyProperties(projectUpdateRequest, project);
        // 数据校验
        projectService.validProject(project, false);
        // 判断是否存在
        long id = projectUpdateRequest.getId();
        Project oldProject = projectService.getById(id);
        ThrowUtils.throwIf(oldProject == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = projectService.updateById(project);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取项目表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<ProjectVO> getProjectVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Project project = projectService.getById(id);
        ThrowUtils.throwIf(project == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(projectService.getProjectVO(project));
    }

    /**
     * 分页获取项目表列表（仅管理员可用）
     *
     * @param projectQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Project>> listProjectByPage(@RequestBody ProjectQueryRequest projectQueryRequest) {
        long current = projectQueryRequest.getCurrentPage();
        long size = projectQueryRequest.getPageSize();
        // 查询数据库
        Page<Project> projectPage = projectService.page(new Page<>(current, size),
                projectService.getQueryWrapper(projectQueryRequest));
        return ResultUtils.success(projectPage);
    }

    /**
     * 分页获取项目表列表（封装类）
     *
     * @param projectQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<ProjectVO>> listProjectVOByPage(@RequestBody ProjectQueryRequest projectQueryRequest) {
        Page<ProjectVO> projectVOPage = projectService.listProjectVOByPage(projectQueryRequest);
        return ResultUtils.success(projectVOPage);
    }



    /**
     * 编辑项目表（给用户使用）
     *
     * @param projectEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editProject(@RequestBody ProjectEditRequest projectEditRequest) {
        if (projectEditRequest == null || projectEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result= projectService.editProject(projectEditRequest);
        return ResultUtils.success(result);
    }

    // endregion


    @GetMapping("/getProjectNames")
    public BaseResponse<List<String>> getProjectNames(){
        ArrayList<String> projectNames = new ArrayList<>();
        for (Project project : projectService.list()) {
            projectNames.add(project.getName());
        }

        return ResultUtils.success(projectNames);

    }
}
