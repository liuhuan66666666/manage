package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.IndividualCompetitionMapper;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionAddRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionEditRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionQueryRequest;
import com.liuhuan.manage.model.entity.IndividualCompetition;
import com.liuhuan.manage.model.vo.IndividualCompetitionVO;
import com.liuhuan.manage.service.IndividualCompetitionService;
import com.liuhuan.manage.service.StudentService;
import com.liuhuan.manage.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 个人竞赛表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class IndividualCompetitionServiceImpl extends ServiceImpl<IndividualCompetitionMapper, IndividualCompetition> implements IndividualCompetitionService {


    @Resource
    private IndividualCompetitionMapper individualCompetitionMapper;
    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;



    /**
     * 校验数据
     *
     * @param individualCompetition
     * @param add                   对创建的数据进行校验
     */
    @Override
    public void validIndividualCompetition(IndividualCompetition individualCompetition, boolean add) {
        ThrowUtils.throwIf(individualCompetition == null, ErrorCode.PARAMS_ERROR);
    }

    /**
     * 获取查询条件
     *
     * @param individualCompetitionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<IndividualCompetition> getQueryWrapper(IndividualCompetitionQueryRequest individualCompetitionQueryRequest) {
        QueryWrapper<IndividualCompetition> queryWrapper = new QueryWrapper<>();
        if (individualCompetitionQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    /**
     * 获取个人竞赛表封装
     *
     * @param individualCompetition
     * @return
     */
    @Override
    public IndividualCompetitionVO getIndividualCompetitionVO(IndividualCompetition individualCompetition) {
        // 对象转封装类
        IndividualCompetitionVO individualCompetitionVO = IndividualCompetitionVO.objToVo(individualCompetition);

        return individualCompetitionVO;
    }

    /**
     * 分页获取个人竞赛表封装
     *
     * @param individualCompetitionPage
     * @return
     */
    @Override
    public Page<IndividualCompetitionVO> getIndividualCompetitionVOPage(Page<IndividualCompetition> individualCompetitionPage) {
        List<IndividualCompetition> individualCompetitionList = individualCompetitionPage.getRecords();
        Page<IndividualCompetitionVO> individualCompetitionVOPage = new Page<>(individualCompetitionPage.getCurrent(), individualCompetitionPage.getSize(), individualCompetitionPage.getTotal());
        if (CollUtil.isEmpty(individualCompetitionList)) {
            return individualCompetitionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<IndividualCompetitionVO> individualCompetitionVOList = individualCompetitionList.stream().map(individualCompetition -> {
            return IndividualCompetitionVO.objToVo(individualCompetition);
        }).collect(Collectors.toList());


        individualCompetitionVOPage.setRecords(individualCompetitionVOList);
        return individualCompetitionVOPage;
    }

    @Override
    public Long addIndividualCompetition(IndividualCompetitionAddRequest individualCompetitionAddRequest) {
        //获取学生id
        String studentName = individualCompetitionAddRequest.getStudent();
        Long studentId = studentService.getStudentId(studentName);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(studentId), ErrorCode.PARAMS_ERROR, "该学生不存在");
        //获取教师id
        List<String> teacher = individualCompetitionAddRequest.getTeacher();
        ArrayList<Long> teacherIds = new ArrayList<>();
        for (String s : teacher) {
            Long teacherId = teacherService.getTeacherId(s);
            ThrowUtils.throwIf(ObjectUtils.isEmpty(teacherId), ErrorCode.PARAMS_ERROR, "教师不存在");
            teacherIds.add(teacherId);
        }
        //存入竞赛信息至竞赛表
        IndividualCompetition individualCompetition = new IndividualCompetition();
        BeanUtils.copyProperties(individualCompetitionAddRequest, individualCompetition);
        individualCompetitionMapper.insert(individualCompetition);
        //主键回显
        Long id = individualCompetition.getId();

        //学生竞赛关联表
        individualCompetitionMapper.saveCompetitionStudentRelation(id, studentId);

        //教师竞赛关联表
        for (Long teacherId : teacherIds) {
            individualCompetitionMapper.saveCompetitionTeacherRelation(id, teacherId);
        }

        return id;
    }

    @Override
    public Boolean deleteIndividualCompetition(long id) {
        int i = individualCompetitionMapper.deleteById(id);

        //学生竞赛关联表
        Boolean TeacherBoolean = individualCompetitionMapper.deleteCompetitionTeacherRelation(id);

        //教师竞赛关联表
        Boolean StudentBoolean = individualCompetitionMapper.deleteCompetitionStudentRelation(id);
        if (i > 0 && TeacherBoolean && StudentBoolean) {
            return true;
        }
        return false;
    }

    @Override
    public Page<IndividualCompetitionVO> getListlCompetitionVO(IndividualCompetitionQueryRequest individualCompetitionQueryRequest) {
        int current = individualCompetitionQueryRequest.getCurrentPage();
        int size = individualCompetitionQueryRequest.getPageSize();
        String name = individualCompetitionQueryRequest.getName();
        Integer year = individualCompetitionQueryRequest.getYear();
        Integer status = individualCompetitionQueryRequest.getStatus();
        int offset = (current - 1) * size;
        // 查询列表数据
        List<IndividualCompetitionVO> competitionList = individualCompetitionMapper.getListCompetitionVO(name,year,status,offset,size);
        // 查询总数
        int totalCount = individualCompetitionMapper.countCompetitionVO(name,year,status);
        //构造分页对象
        Page<IndividualCompetitionVO> individualCompetitionPage = new Page<>(current, size);
        individualCompetitionPage.setTotal(totalCount);
        individualCompetitionPage.setRecords(competitionList);

        return individualCompetitionPage;
    }

    @Override
    public Boolean editIndividualCompetition(IndividualCompetitionEditRequest individualCompetitionEditRequest) {
        // todo 在此处将实体类和 DTO 进行转换
        IndividualCompetition individualCompetition = new IndividualCompetition();
        BeanUtils.copyProperties(individualCompetitionEditRequest, individualCompetition);
        // 数据校验
        this.validIndividualCompetition(individualCompetition, false);
        // 判断是否存在
        long id = individualCompetitionEditRequest.getId();
        IndividualCompetition oldIndividualCompetition = this.getById(id);
        ThrowUtils.throwIf(oldIndividualCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作个人竞赛表
        boolean result = this.updateById(individualCompetition);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        //获取学生id
        String studentName = individualCompetitionEditRequest.getStudent();
        Long studentId = studentService.getStudentId(studentName);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(studentId), ErrorCode.PARAMS_ERROR, "该学生不存在");


        //先删除再添加不然有bug
        individualCompetitionMapper.deleteCompetitionTeacherRelation(id);
        //获取教师id
        List<String> teacher = individualCompetitionEditRequest.getTeacher();
        ArrayList<Long> teacherIds = new ArrayList<>();
        for (String s : teacher) {
            Long teacherId = teacherService.getTeacherId(s);
            ThrowUtils.throwIf(ObjectUtils.isEmpty(teacherId), ErrorCode.PARAMS_ERROR, "教师不存在");
            teacherIds.add(teacherId);
        }

        //学生竞赛关联表
        individualCompetitionMapper.updateCompetitionStudentRelation(id,studentId);


        //教师竞赛关联表
        for (Long teacherId : teacherIds) {
           individualCompetitionMapper.saveCompetitionTeacherRelation(id, teacherId);
        }





        return true;

    }


}
