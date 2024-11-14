package com.liuhuan.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuhuan.manage.annotation.AuthCheck;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.constant.UserConstant;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionAddRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionEditRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionQueryRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionUpdateRequest;
import com.liuhuan.manage.model.entity.IndividualCompetition;
import com.liuhuan.manage.model.vo.IndividualCompetitionVO;
import com.liuhuan.manage.service.IndividualCompetitionService;
import com.liuhuan.manage.service.StudentService;
import com.liuhuan.manage.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 个人竞赛表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/individualCompetition")
@Slf4j
public class IndividualCompetitionController {

    @Resource
    private IndividualCompetitionService individualCompetitionService;


    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentService studentService;

    // region 增删改查

    /**
     * 创建个人竞赛表
     *
     * @param individualCompetitionAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addIndividualCompetition(@RequestBody IndividualCompetitionAddRequest individualCompetitionAddRequest) {
        ThrowUtils.throwIf(individualCompetitionAddRequest == null, ErrorCode.PARAMS_ERROR);
        Long  newIndividualCompetitionId = individualCompetitionService.addIndividualCompetition(individualCompetitionAddRequest);
        return ResultUtils.success(newIndividualCompetitionId);
    }

    /**
     * 删除个人竞赛表
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteIndividualCompetition(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = deleteRequest.getId();
        // 判断是否存在
        IndividualCompetition oldIndividualCompetition = individualCompetitionService.getById(id);
        ThrowUtils.throwIf(oldIndividualCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        Boolean result = individualCompetitionService.deleteIndividualCompetition(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新个人竞赛表（仅管理员可用）
     *
     * @param individualCompetitionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateIndividualCompetition(@RequestBody IndividualCompetitionUpdateRequest individualCompetitionUpdateRequest) {
        if (individualCompetitionUpdateRequest == null || individualCompetitionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        IndividualCompetition individualCompetition = new IndividualCompetition();
        BeanUtils.copyProperties(individualCompetitionUpdateRequest, individualCompetition);
        // 数据校验
        individualCompetitionService.validIndividualCompetition(individualCompetition, false);
        // 判断是否存在
        long id = individualCompetitionUpdateRequest.getId();
        IndividualCompetition oldIndividualCompetition = individualCompetitionService.getById(id);
        ThrowUtils.throwIf(oldIndividualCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = individualCompetitionService.updateById(individualCompetition);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取个人竞赛表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<IndividualCompetitionVO> getIndividualCompetitionVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        IndividualCompetition individualCompetition = individualCompetitionService.getById(id);
        ThrowUtils.throwIf(individualCompetition == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(individualCompetitionService.getIndividualCompetitionVO(individualCompetition));
    }

    /**
     * 分页获取个人竞赛表列表（仅管理员可用）
     *
     * @param individualCompetitionQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<IndividualCompetition>> listIndividualCompetitionByPage(@RequestBody IndividualCompetitionQueryRequest individualCompetitionQueryRequest) {
        long current = individualCompetitionQueryRequest.getCurrentPage();
        long size = individualCompetitionQueryRequest.getPageSize();
        // 查询数据库
        Page<IndividualCompetition> individualCompetitionPage = individualCompetitionService.page(new Page<>(current, size),
                individualCompetitionService.getQueryWrapper(individualCompetitionQueryRequest));
        return ResultUtils.success(individualCompetitionPage);
    }

    /**
     * 分页获取个人竞赛表列表（封装类）
     *
     * @param individualCompetitionQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<IndividualCompetitionVO>> listIndividualCompetitionVOByPage(@RequestBody IndividualCompetitionQueryRequest individualCompetitionQueryRequest) {
        Page<IndividualCompetitionVO> listCompetitionVO = individualCompetitionService.getListlCompetitionVO(individualCompetitionQueryRequest);

        return ResultUtils.success(listCompetitionVO);
    }



    /**
     * 编辑个人竞赛表（给用户使用）
     *
     * @param individualCompetitionEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editIndividualCompetition(@RequestBody IndividualCompetitionEditRequest individualCompetitionEditRequest) {
        if (individualCompetitionEditRequest == null || individualCompetitionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result=individualCompetitionService.editIndividualCompetition(individualCompetitionEditRequest);

        return ResultUtils.success(result);



    }

    // endregion
}
