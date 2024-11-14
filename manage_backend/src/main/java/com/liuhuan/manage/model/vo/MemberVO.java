package com.liuhuan.manage.model.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.Member;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 成员表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class MemberVO implements Serializable {

    /**
     *
     */
    private Long id;


    /**
     *
     */
    @Excel(name="姓名")
    private String name;

    /**
     *
     */
    @Excel(name = "性别", replace = { "男_1", "女_2" })
    private Integer gender;

    /**
     *
     */
    @Excel(name = "学号")
    private String studentId;

    /**
     *
     */
    @Excel(name="联系方式")
    private String contact;

    /**
     *
     */
    @Excel(name = "加入时间", format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date joinDate;

    /**
     *
     */
    @Excel(name = "所属部门")
    private String department;

    /**
     *
     */
    @Excel(name = "专业")
    private String major;

    /**
     *
     */
    @Excel(name = "年级")
    private String grade;

    /**
     * 封装类转对象
     *
     * @param memberVO
     * @return
     */
    public static Member voToObj(MemberVO memberVO) {
        if (memberVO == null) {
            return null;
        }
        Member member = new Member();
        BeanUtils.copyProperties(memberVO, member);
        return member;
    }

    /**
     * 对象转封装类
     *
     * @param member
     * @return
     */
    public static MemberVO objToVo(Member member) {
        if (member == null) {
            return null;
        }
        MemberVO memberVO = new MemberVO();
        BeanUtils.copyProperties(member, memberVO);
        return memberVO;
    }
}
