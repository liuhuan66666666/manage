package com.liuhuan.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhuan.manage.model.entity.Project;
import com.liuhuan.manage.model.vo.ProjectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 29832
* @description 针对表【project(项目管理表)】的数据库操作Mapper
* @createDate 2024-11-11 13:53:59
* @Entity com.liuhuan.manage.model.entity.Project
*/
public interface ProjectMapper extends BaseMapper<Project> {

    void saveProjectStudentRelation(@Param("ProjectId") long newProjectId, @Param("studentId") Long studentId);

    void saveProjectTeacherRelation(@Param("ProjectId") long newProjectId, @Param("teacherId") Long teacherId);

    void deleteProjectStudentRelation(@Param("project_id") long id);

    void deleteProjectTeacherRelation(@Param("project_id") long id);

    void editProjectStudentRelation(@Param("project_id") Long id, @Param("studentId") Long studentId);

    void editProjectTeacherRelation(@Param("project_id") Long id, @Param("teacherId")  Long teacherId);

    List<ProjectVO> getListProjectVO(@Param("offset") int offset, @Param("size") int size ,@Param("name") String name , @Param("leader") Long leader);

    int getProjectVOCount(@Param("offset") int offset, @Param("size") int size ,@Param("name") String name , @Param("leader") Long leader);
}




