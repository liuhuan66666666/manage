package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.TeacherMapper;
import com.liuhuan.manage.model.dto.teacher.TeacherQueryRequest;
import com.liuhuan.manage.model.entity.Teacher;
import com.liuhuan.manage.model.vo.TeacherVO;
import com.liuhuan.manage.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 教师表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {



    /**
     * 校验数据
     *
     * @param teacher
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validTeacher(Teacher teacher, boolean add) {
        ThrowUtils.throwIf(teacher == null, ErrorCode.PARAMS_ERROR);
    }

    /**
     * 获取查询条件
     *
     * @param teacherQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Teacher> getQueryWrapper(TeacherQueryRequest teacherQueryRequest) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        Integer teacherId = teacherQueryRequest.getTeacherId();
        String name = teacherQueryRequest.getName();
        String college = teacherQueryRequest.getCollege();
        String department = teacherQueryRequest.getDepartment();
        //精确查询
        queryWrapper.eq(StringUtils.isNotBlank(department),"department",department);
        queryWrapper.eq(StringUtils.isNotBlank(college),"college",college);
        //模糊查询
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);
        queryWrapper.like(ObjectUtils.isNotEmpty(teacherId),"teacher_id",teacherId);
        if (teacherQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    /**
     * 获取教师表封装
     *
     * @param teacher
     * @return
     */
    @Override
    public TeacherVO getTeacherVO(Teacher teacher) {
        // 对象转封装类
        TeacherVO teacherVO = TeacherVO.objToVo(teacher);
        return teacherVO;
    }

    /**
     * 分页获取教师表封装
     *
     * @param teacherPage
     * @return
     */
    @Override
    public Page<TeacherVO> getTeacherVOPage(Page<Teacher> teacherPage) {
        List<Teacher> teacherList = teacherPage.getRecords();
        Page<TeacherVO> teacherVOPage = new Page<>(teacherPage.getCurrent(), teacherPage.getSize(), teacherPage.getTotal());
        if (CollUtil.isEmpty(teacherList)) {
            return teacherVOPage;
        }
        // 对象列表 => 封装对象列表
        List<TeacherVO> teacherVOList = teacherList.stream().map(teacher -> {
            return TeacherVO.objToVo(teacher);
        }).collect(Collectors.toList());

        teacherVOPage.setRecords(teacherVOList);
        return teacherVOPage;
    }

    /**
     * 获取老师id
     *
     * @param: name
     * @return
     */
    @Override
    public Long getTeacherId(String name) {
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.eq(Teacher::getName,name);
        Teacher teacher = this.baseMapper.selectOne(teacherLambdaQueryWrapper);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(teacher),ErrorCode.PARAMS_ERROR,"教师不存在");
        return teacher.getId();
    }





}
