package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.Project;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 项目表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class ProjectVO implements Serializable {

    /**
     * 项目ID
     */
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 源代码路径
     */
    private String source_code_path;

    /**
     * 项目状态 (0: not_started - 未启动, 1: in_progress - 进行中, 2: completed - 已完成)，默认进行中
     */
    private Integer status;

    /**
     * 项目启动日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date startDate;

    /**
     * 项目组长
     */
    private String leader;


    /**
     * 项目成员
     */
    private List<String> student;

    /**
     * 项目指导老师
     */
    private List<String> teacher;




    /**
     * 封装类转对象
     *
     * @param projectVO
     * @return
     */
    public static Project voToObj(ProjectVO projectVO) {
        if (projectVO == null) {
            return null;
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectVO, project);
        return project;
    }

    /**
     * 对象转封装类
     *
     * @param project
     * @return
     */
    public static ProjectVO objToVo(Project project) {
        if (project == null) {
            return null;
        }
        ProjectVO projectVO = new ProjectVO();
        BeanUtils.copyProperties(project, projectVO);
        return projectVO;
    }
}
