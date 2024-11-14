package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.StudentMapper;
import com.liuhuan.manage.model.dto.student.StudentQueryRequest;
import com.liuhuan.manage.model.entity.Student;
import com.liuhuan.manage.model.vo.StudentVO;
import com.liuhuan.manage.service.StudentService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param student
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validStudent(Student student, boolean add) {
        ThrowUtils.throwIf(student == null, ErrorCode.PARAMS_ERROR);
    }

    /**
     * 获取查询条件
     *
     * @param studentQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Student> getQueryWrapper(StudentQueryRequest studentQueryRequest) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (studentQueryRequest == null) {
            return queryWrapper;
        }
        Long studentId = studentQueryRequest.getStudentId();
        String name = studentQueryRequest.getName();
        String college = studentQueryRequest.getCollege();
        String major = studentQueryRequest.getMajor();

        //模糊查询
        queryWrapper.like(StringUtils.isNotBlank(name),"name",studentQueryRequest.getName());
        //精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(studentId),"student_id",studentQueryRequest.getStudentId());
        queryWrapper.eq(StringUtils.isNotBlank(college),"college",studentQueryRequest.getCollege());
        queryWrapper.eq(StringUtils.isNotBlank(major),"major",studentQueryRequest.getMajor());

        // 排序规则
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
//                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
//                sortField);
        return queryWrapper;
    }

    /**
     * 获取学生表封装
     *
     * @param student
     * @return
     */
    @Override
    public StudentVO getStudentVO(Student student) {
        // 对象转封装类
        StudentVO studentVO = StudentVO.objToVo(student);
        return studentVO;
    }

    /**
     * 分页获取学生表封装
     *
     * @param studentPage
     * @return
     */
    @Override
    public Page<StudentVO> getStudentVOPage(@NotNull Page<Student> studentPage) {
        List<Student> studentList = studentPage.getRecords();
        Page<StudentVO> studentVOPage = new Page<>(studentPage.getCurrent(), studentPage.getSize(), studentPage.getTotal());
        if (CollUtil.isEmpty(studentList)) {
            return studentVOPage;
        }
        // 对象列表 => 封装对象列表
        List<StudentVO> studentVOList = studentList.stream().map(student -> {
            return StudentVO.objToVo(student);
        }).collect(Collectors.toList());

        studentVOPage.setRecords(studentVOList);
        return studentVOPage;
    }


    /**
     * 获取学生id
     *
     * @param: name
     * @return
     */
    @Override
    public Long getStudentId(String name) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getName,name);
        Student student = this.baseMapper.selectOne(studentLambdaQueryWrapper);
        if(student==null){
            ThrowUtils.throwIf(student==null,ErrorCode.PARAMS_ERROR,"没有该学生");
        }
        return student.getId();
    }

}
