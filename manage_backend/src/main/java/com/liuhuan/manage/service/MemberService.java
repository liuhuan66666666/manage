package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.member.MemberQueryRequest;
import com.liuhuan.manage.model.entity.Member;
import com.liuhuan.manage.model.vo.MemberVO;

import java.util.List;

/**
 * 成员表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
public interface MemberService extends IService<Member> {

    /**
     * 校验数据
     *
     * @param member
     * @param add 对创建的数据进行校验
     */
    void validMember(Member member, boolean add);

    /**
     * 获取查询条件
     *
     * @param memberQueryRequest
     * @return
     */
    QueryWrapper<Member> getQueryWrapper(MemberQueryRequest memberQueryRequest);
    
    /**
     * 获取成员表封装
     *
     * @param member
     * @return
     */
    MemberVO getMemberVO(Member member);

    /**
     * 分页获取成员表封装
     *
     * @param memberPage
     * @return
     */
    Page<MemberVO> getMemberVOPage(Page<Member> memberPage);

    List<MemberVO> MemberListVo(List<Member> members);
}
