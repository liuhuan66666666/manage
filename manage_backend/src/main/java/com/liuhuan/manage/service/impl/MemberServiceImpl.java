package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.MemberMapper;
import com.liuhuan.manage.model.dto.member.MemberQueryRequest;
import com.liuhuan.manage.model.entity.Member;
import com.liuhuan.manage.model.vo.MemberVO;
import com.liuhuan.manage.service.MemberService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 成员表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param member
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validMember(Member member, boolean add) {
        ThrowUtils.throwIf(member == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String name = member.getName();
        Integer gender = member.getGender();
        String studentId = member.getStudentId();
        String contact = member.getContact();
        String department = member.getDepartment();
        String major = member.getMajor();
        String grade = member.getGrade();

        // 创建数据时，参数不能为空
        ThrowUtils.throwIf(StringUtils.isBlank(name),ErrorCode.PARAMS_ERROR,"姓名不能为空");
        ThrowUtils.throwIf(ObjectUtils.isEmpty(gender),ErrorCode.PARAMS_ERROR,"性别不能为空");
        ThrowUtils.throwIf(StringUtils.isBlank(studentId),ErrorCode.PARAMS_ERROR,"学号不能为空");
        ThrowUtils.throwIf(StringUtils.isBlank(contact),ErrorCode.PARAMS_ERROR,"联系方式不能为空");
        ThrowUtils.throwIf(StringUtils.isBlank(department),ErrorCode.PARAMS_ERROR,"部门不能为空");
        ThrowUtils.throwIf(StringUtils.isBlank(major),ErrorCode.PARAMS_ERROR,"专业不能为空");
        ThrowUtils.throwIf(StringUtils.isBlank(grade),ErrorCode.PARAMS_ERROR,"年级不能为空");

        // 修改数据时，参数校验
        if (member != null) {
            // 如果姓名不为空，则校验姓名
            if (StringUtils.isNotBlank(name)) {
                ThrowUtils.throwIf(StringUtils.length(name) > 50, ErrorCode.PARAMS_ERROR, "姓名长度不能超过50个字符");
            }
            // 如果性别不为空，则校验性别
            if (ObjectUtils.isNotEmpty(gender)) {
                ThrowUtils.throwIf(!(gender == 1 || gender == 2), ErrorCode.PARAMS_ERROR, "性别输入错误,请检查");
            }
            // 如果学号不为空，则校验学号
            if (StringUtils.isNotBlank(studentId)) {
                ThrowUtils.throwIf(StringUtils.length(studentId) > 20, ErrorCode.PARAMS_ERROR, "学号长度不能超过20个字符");
            }
            // 如果联系方式不为空，则校验联系方式
            if (StringUtils.isNotBlank(contact)) {
                ThrowUtils.throwIf(StringUtils.length(contact) > 50, ErrorCode.PARAMS_ERROR, "联系方式长度不能超过50个字符");
            }
            // 如果部门不为空，则校验部门
            if (StringUtils.isNotBlank(department)) {
                ThrowUtils.throwIf(StringUtils.length(department) > 50, ErrorCode.PARAMS_ERROR, "部门长度不能超过50个字符");
            }
            // 如果专业不为空，则校验专业
            if (StringUtils.isNotBlank(major)) {
                ThrowUtils.throwIf(StringUtils.length(major) > 50, ErrorCode.PARAMS_ERROR, "专业长度不能超过50个字符");
            }
            // 如果年级不为空，则校验年级
            if (StringUtils.isNotBlank(grade)) {
                ThrowUtils.throwIf(StringUtils.length(grade) > 10, ErrorCode.PARAMS_ERROR, "年级长度不能超过10个字符");
            }
        }

    }

    /**
     * 获取查询条件
     *
     * @param memberQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Member> getQueryWrapper(MemberQueryRequest memberQueryRequest) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        if (memberQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = memberQueryRequest.getId();
        String name = memberQueryRequest.getName();
        String studentId = memberQueryRequest.getStudentId();
        String department = memberQueryRequest.getDepartment();
        String major = memberQueryRequest.getMajor();
        String grade = memberQueryRequest.getGrade();

        // 补充需要的查询条件
        // 从多字段中搜索

        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);

        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),"id",id);
        queryWrapper.eq(StringUtils.isNotBlank(studentId),"studentId",studentId);
        queryWrapper.eq(StringUtils.isNotBlank(department),"department",department);
        queryWrapper.eq(StringUtils.isNotBlank(major),"major",major);
        queryWrapper.like(StringUtils.isNotBlank(grade),"grade",grade);

        // 排序规则

        return queryWrapper;
    }

    /**
     * 获取成员表封装
     *
     * @param member
     * @return
     */
    @Override
    public MemberVO getMemberVO(Member member) {
        // 对象转封装类
        MemberVO memberVO = MemberVO.objToVo(member);
        // 根据需要为封装对象补充值，不需要的内容可以删除
        return memberVO;
    }

    /**
     * 分页获取成员表封装
     *
     * @param memberPage
     * @return
     */
    @Override
    public Page<MemberVO> getMemberVOPage(Page<Member> memberPage) {
        List<Member> memberList = memberPage.getRecords();
        Page<MemberVO> memberVOPage = new Page<>(memberPage.getCurrent(), memberPage.getSize(), memberPage.getTotal());
        if (CollUtil.isEmpty(memberList)) {
            return memberVOPage;
        }
        // 对象列表 => 封装对象列表
        List<MemberVO> memberVOList = memberList.stream().map(member -> {
            return MemberVO.objToVo(member);
        }).collect(Collectors.toList());

        //以根据需要为封装对象补充值，不需要的内容可以删除
        memberVOPage.setRecords(memberVOList);
        return memberVOPage;
    }


    /*
     * @desciption: 获取成员列表封装
     * @Param: [memberlist]
     * @Return: java.util.List<com.liuhuan.manage.model.vo.MemberVO>
     * @Author: liuhuan
     */
    @Override
    public List<MemberVO> MemberListVo(List<Member> members) {
        List<MemberVO> memberVOS = members.stream().map((member) -> {
            MemberVO memberVO = new MemberVO();
            BeanUtils.copyProperties(member, memberVO);
            return memberVO;
        }).collect(Collectors.toList());
        return memberVOS;
    }

}
