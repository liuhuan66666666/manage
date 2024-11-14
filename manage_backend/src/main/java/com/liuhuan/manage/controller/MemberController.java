package com.liuhuan.manage.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuhuan.manage.annotation.AuthCheck;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.constant.UserConstant;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.member.MemberAddRequest;
import com.liuhuan.manage.model.dto.member.MemberEditRequest;
import com.liuhuan.manage.model.dto.member.MemberQueryRequest;
import com.liuhuan.manage.model.dto.member.MemberUpdateRequest;
import com.liuhuan.manage.model.entity.Member;
import com.liuhuan.manage.model.vo.MemberVO;
import com.liuhuan.manage.service.MemberService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 成员表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Resource
    private MemberService memberService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建成员表
     *
     * @param memberAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addMember(@RequestBody MemberAddRequest memberAddRequest) {
        ThrowUtils.throwIf(memberAddRequest == null, ErrorCode.PARAMS_ERROR);
        //将实体类和 DTO 进行转换
        Member member = new Member();
        BeanUtils.copyProperties(memberAddRequest, member);
        // 数据校验
        memberService.validMember(member, true);
        // 写入数据库
        boolean result = memberService.save(member);
        //主键回显
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newMemberId = member.getId();
        return ResultUtils.success(newMemberId);
    }

    /**
     * 删除成员表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteMember(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Member Member = memberService.getById(id);
        ThrowUtils.throwIf(Member == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除

        // 操作数据库
        boolean result = memberService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新成员表（仅管理员可用）
     *
     * @param memberUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateMember(@RequestBody MemberUpdateRequest memberUpdateRequest) {
        if (memberUpdateRequest == null || memberUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Member member = new Member();
        BeanUtils.copyProperties(memberUpdateRequest, member);
        // 数据校验
        memberService.validMember(member, false);
        // 判断是否存在
        long id = memberUpdateRequest.getId();
        Member oldMember = memberService.getById(id);
        ThrowUtils.throwIf(oldMember == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = memberService.updateById(member);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取成员表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<MemberVO> getMemberVOById(Long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Member member = memberService.getById(id);;
        ThrowUtils.throwIf(member == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(memberService.getMemberVO(member));
    }

    /**
     * 分页获取成员表列表（仅管理员可用）
     *
     * @param memberQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Member>> listMemberByPage(@RequestBody MemberQueryRequest memberQueryRequest) {
        long current = memberQueryRequest.getCurrentPage();
        long size = memberQueryRequest.getPageSize();
        Page<Member> Page = new Page<>(current, size);
        // 查询数据库
        Page<Member> memberPage = memberService.page(new Page<>(current, size),
                memberService.getQueryWrapper(memberQueryRequest));
        return ResultUtils.success(memberPage);
    }

    /**
     * 分页获取成员表列表（封装类）
     *
     * @param memberQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<MemberVO>> listMemberVOByPage(@RequestBody MemberQueryRequest memberQueryRequest,
                                                               HttpServletRequest request) {
        long current = memberQueryRequest.getCurrentPage();
        long size = memberQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Member> memberPage = memberService.page(new Page<>(current, size),
                memberService.getQueryWrapper(memberQueryRequest));
        // 获取封装类
        return ResultUtils.success(memberService.getMemberVOPage(memberPage));
    }

    /**
     * 分页获取当前登录用户创建的成员表列表
     *
     * @param memberQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<MemberVO>> listMyMemberVOByPage(@RequestBody MemberQueryRequest memberQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(memberQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据

        long current = memberQueryRequest.getCurrentPage();
        long size = memberQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Member> memberPage = memberService.page(new Page<>(current, size),
                memberService.getQueryWrapper(memberQueryRequest));
        // 获取封装类
        return ResultUtils.success(memberService.getMemberVOPage(memberPage));
    }



    //各专业人数
    @GetMapping("/getMajorDistribution")
    public BaseResponse<Map<String, Object>> getMajorDistribution() {
        // 获取Member列表
        List<Member> list = memberService.list();

        // 创建一个HashMap用于存储专业及其对应的人数
        Map<String, Integer> majorCountMap = new HashMap<>();

        // 遍历每个Member，统计各个专业的人数
        for (Member member : list) {
            String major = member.getMajor();
            majorCountMap.put(major, majorCountMap.getOrDefault(major, 0) + 1);
        }

        // 将结果转换为前端ECharts所需的格式
        List<String> categories = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : majorCountMap.entrySet()) {
            categories.add(entry.getKey());
            data.add(entry.getValue());
        }

        // 将结果封装为Map返回
        Map<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("data", data);

        return ResultUtils.success(result);
    }


    //各部门比例
    @GetMapping("/getDepartmentDistribution")
    public BaseResponse<Map<String, Object>> getDepartmentDistribution() {
        // 从服务层获取成员列表
        List<Member> list = memberService.list();

        // 统计各部门的人数
        Map<String, Long> departmentCount = list.stream()
                .collect(Collectors.groupingBy(Member::getDepartment, Collectors.counting()));

        // 创建一个 JSON 对象，准备返回
        Map<String, Object> response = new HashMap<>();

        // 将部门计数转换为指定格式的对象数组
        List<Map<String, Object>> departmentData = departmentCount.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> departmentInfo = new HashMap<>();
                    departmentInfo.put("value", entry.getValue()); // 数量
                    departmentInfo.put("name", entry.getKey()); // 部门名称
                    return departmentInfo;
                })
                .collect(Collectors.toList());

        response.put("data", departmentData); // 将数据加入响应对象

        return ResultUtils.success(response); // 返回响应
    }

//    @GetMapping("/test")
//    public Object test(){
//        // 获取Member列表
//        List<Member> list = memberService.list();
//        System.out.println(list);
//        return null;
//    }

    /**
     * 编辑成员表（给用户使用）
     *
     * @param memberEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editMember(@RequestBody MemberEditRequest memberEditRequest, HttpServletRequest request) {
        if (memberEditRequest == null || memberEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将实体类和 DTO 进行转换
        Member member = new Member();
        BeanUtils.copyProperties(memberEditRequest, member);
        // 数据校验
        memberService.validMember(member, false);
        // 判断是否存在
        long id = memberEditRequest.getId();
        Member oldMember = memberService.getById(id);
        ThrowUtils.throwIf(oldMember == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = memberService.updateById(member);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion

    @PostMapping(value="/exportMemberExcel")
    public void export(@RequestBody MemberQueryRequest memberQueryRequest ,HttpServletResponse response) throws IOException {

        long size = memberQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        //查出要转换的数据
        List<Member> memberList = memberService.list(memberService.getQueryWrapper(memberQueryRequest));

        // 进行vo转换
        List<MemberVO> memberVOS = memberService.MemberListVo(memberList);

        // 创建Workbook对象
        ExportParams params = new ExportParams("成员数据表", "", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, MemberVO.class, memberVOS);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("成员汇总表" + ".xlsx", "UTF-8"));
        // 获取响应的输出流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            // 关闭Workbook资源
            if (workbook != null) {
                workbook.close();
            }
        }
    }



}
