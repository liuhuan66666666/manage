package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.project.ProjectAddRequest;
import com.liuhuan.manage.model.dto.project.ProjectEditRequest;
import com.liuhuan.manage.model.dto.project.ProjectQueryRequest;
import com.liuhuan.manage.model.entity.Project;
import com.liuhuan.manage.model.vo.ProjectVO;

/**
 * 项目表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface ProjectService extends IService<Project> {

    /**
     * 校验数据
     *
     * @param project
     * @param add 对创建的数据进行校验
     */
    void validProject(Project project, boolean add);

    /**
     * 获取查询条件
     *
     * @param projectQueryRequest
     * @return
     */
    QueryWrapper<Project> getQueryWrapper(ProjectQueryRequest projectQueryRequest);
    
    /**
     * 获取项目表封装
     *
     * @param project
     * @return
     */
    ProjectVO getProjectVO(Project project);

    /**
     * 分页获取项目表封装
     *
     * @param projectPage
     * @return
     */
    Page<ProjectVO> getProjectVOPage(Page<Project> projectPage);

    Long addProject(ProjectAddRequest projectAddRequest);

    Boolean deleteProject(long id);

    Boolean editProject(ProjectEditRequest projectEditRequest);

    Page<ProjectVO> listProjectVOByPage(ProjectQueryRequest projectQueryRequest);
}
