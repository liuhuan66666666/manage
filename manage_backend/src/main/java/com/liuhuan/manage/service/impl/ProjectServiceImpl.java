package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.ProjectMapper;
import com.liuhuan.manage.model.dto.project.ProjectAddRequest;
import com.liuhuan.manage.model.dto.project.ProjectEditRequest;
import com.liuhuan.manage.model.dto.project.ProjectQueryRequest;
import com.liuhuan.manage.model.entity.Project;
import com.liuhuan.manage.model.vo.ProjectVO;
import com.liuhuan.manage.service.ProjectService;
import com.liuhuan.manage.service.StudentService;
import com.liuhuan.manage.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ProjectMapper projectMapper;

    /**
     * 校验数据
     *
     * @param project
     * @param add     对创建的数据进行校验
     */
    @Override
    public void validProject(Project project, boolean add) {
        ThrowUtils.throwIf(project == null, ErrorCode.PARAMS_ERROR);
    }

    /**
     * 获取查询条件
     *
     * @param projectQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Project> getQueryWrapper(ProjectQueryRequest projectQueryRequest) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        if (projectQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    /**
     * 获取项目表封装
     *
     * @param project
     * @return
     */
    @Override
    public ProjectVO getProjectVO(Project project) {
        // 对象转封装类
        ProjectVO projectVO = ProjectVO.objToVo(project);
        return projectVO;
    }

    /**
     * 分页获取项目表封装
     *
     * @param projectPage
     * @return
     */
    @Override
    public Page<ProjectVO> getProjectVOPage(Page<Project> projectPage) {
        List<Project> projectList = projectPage.getRecords();
        Page<ProjectVO> projectVOPage = new Page<>(projectPage.getCurrent(), projectPage.getSize(), projectPage.getTotal());
        if (CollUtil.isEmpty(projectList)) {
            return projectVOPage;
        }
        // 对象列表 => 封装对象列表
        List<ProjectVO> projectVOList = projectList.stream().map(project -> {
            return ProjectVO.objToVo(project);
        }).collect(Collectors.toList());

        projectVOPage.setRecords(projectVOList);
        return projectVOPage;
    }

    @Override
    public Long addProject(ProjectAddRequest projectAddRequest) {
        Project project = new Project();
        BeanUtils.copyProperties(projectAddRequest, project);
        //填充组长id
        project.setLeader_id(studentService.getStudentId(projectAddRequest.getLeader()));
        // 数据校验
        this.validProject(project, true);
        // 写入项目表
        boolean result = this.save(project);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newProjectId = project.getId();

        //获取学生列表id
        List<String> students = projectAddRequest.getStudent();
        for (String student : students) {
            Long studentId = studentService.getStudentId(student);
            //项目学生关联表
            projectMapper.saveProjectStudentRelation(newProjectId, studentId);
        }


        //获取教师列表id
        List<String> teachers = projectAddRequest.getTeacher();
        ArrayList<Long> teacherIds = new ArrayList<>();
        for (String teacher : teachers) {
            Long teacherId = teacherService.getTeacherId(teacher);
            //项目教师关联表
            projectMapper.saveProjectTeacherRelation(newProjectId, teacherId);
        }


        return newProjectId;
    }

    @Override
    public Boolean deleteProject(long id) {
        //项目表
        int i = projectMapper.deleteById(id);

        //学生项目关联表
        projectMapper.deleteProjectStudentRelation(id);

        //教师项目关联表
        projectMapper.deleteProjectTeacherRelation(id);

        return true;
    }

    @Override
    public Boolean editProject(ProjectEditRequest projectEditRequest) {
        ThrowUtils.throwIf(projectEditRequest == null, ErrorCode.PARAMS_ERROR);
        //主键id
        Long id = projectEditRequest.getId();
        //判断存不存在
        ThrowUtils.throwIf(projectMapper.selectById(id) == null, ErrorCode.PARAMS_ERROR);
        //将dto转换为数据库实体对象
        Project project = new Project();
        BeanUtils.copyProperties(projectEditRequest, project);
        //设置组长id
        project.setLeader_id(studentService.getStudentId(projectEditRequest.getLeader()));
        //项目表
        projectMapper.updateById(project);
        //学生项目表
        //先删除再添加不然有bug
        projectMapper.deleteProjectStudentRelation(id);
        List<String> students = projectEditRequest.getStudent();
        for (String student : students) {
            //得到学生id
            Long studentId = studentService.getStudentId(student);
            projectMapper.saveProjectStudentRelation(id, studentId);
        }
        //教师项目表
        //因为是多个教师指导必须先删除再添加,不能直接编辑(第一次选一个指导老师,编辑时选两个会出现这种情况) 不然有bug
        projectMapper.deleteProjectTeacherRelation(id);
        List<String> teachers = projectEditRequest.getTeacher();
        for (String teacher : teachers) {
            //得到教师id
            Long teacherId = teacherService.getTeacherId(teacher);
            projectMapper.saveProjectTeacherRelation(id, teacherId);
        }
        return true;
    }

    @Override
    public Page<ProjectVO> listProjectVOByPage(ProjectQueryRequest projectQueryRequest) {
        // 校验请求参数
        ThrowUtils.throwIf(projectQueryRequest == null, ErrorCode.PARAMS_ERROR);

        String name = projectQueryRequest.getName();
        Long leader = null;

        // 如果队长ID存在，获取队长ID
        if (projectQueryRequest.getLeader() != null && projectQueryRequest.getLeader().length() > 0) {
            leader = studentService.getStudentId(projectQueryRequest.getLeader());
        }

        int currentPage = projectQueryRequest.getCurrentPage();
        int pageSize = projectQueryRequest.getPageSize();

        // 计算分页的起始位置
        int offset = (currentPage - 1) * pageSize;

        // 执行分页查询
        List<ProjectVO> projectVOList = projectMapper.getListProjectVO(offset, pageSize, name, leader);

        // 执行数量查询
        int count = projectMapper.getProjectVOCount(offset, pageSize, name, leader);

        Page<ProjectVO> projectVOPage = new Page<>(offset,pageSize);
        projectVOPage.setRecords(projectVOList);
        projectVOPage.setTotal(count);
        return projectVOPage;
    }


}
