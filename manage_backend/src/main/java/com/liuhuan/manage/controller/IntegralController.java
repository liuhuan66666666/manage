package com.liuhuan.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuhuan.manage.annotation.AuthCheck;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.constant.UserConstant;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.integral.IntegralEditRequest;
import com.liuhuan.manage.model.dto.integral.IntegralQueryRequest;
import com.liuhuan.manage.model.entity.Integral;
import com.liuhuan.manage.model.vo.IntegralVO;
import com.liuhuan.manage.service.IntegralService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 积分表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@RestController
@RequestMapping("/integral")
@Slf4j
public class IntegralController {

    @Resource
    private IntegralService integralService;

    @Resource
    private UserService userService;


    /**
     * 分页获取积分表列表（仅管理员可用）
     *
     * @param integralQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Integral>> listIntegralByPage(@RequestBody IntegralQueryRequest integralQueryRequest) {
        long current = integralQueryRequest.getCurrentPage();
        long size = integralQueryRequest.getPageSize();
        // 查询数据库
        Page<Integral> integralPage = integralService.page(new Page<>(current, size),
                integralService.getQueryWrapper(integralQueryRequest));
        return ResultUtils.success(integralPage);
    }

    /**
     * 分页获取积分表列表（封装类）
     *
     * @param integralQueryRequest
     * @param request
     * @return
     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<IntegralVO>> listIntegralVOByPage(@RequestBody IntegralQueryRequest integralQueryRequest, HttpServletRequest request) {
//        long current = integralQueryRequest.getCurrentPage();
//        long size = integralQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        // 查询数据库
//        Page<Integral> integralPage = integralService.page(new Page<>(current, size),
//                integralService.getQueryWrapper(integralQueryRequest));
//        // 获取封装类
//        return ResultUtils.success(integralService.getIntegralVOPage(integralPage, request));
//    }



    @PostMapping("/list/page/vo")
    public BaseResponse<Page<IntegralVO>> listIntegralVOByPage(@RequestBody IntegralQueryRequest integralQueryRequest, HttpServletRequest request) {
        Integer current = integralQueryRequest.getCurrentPage();
        Integer size = integralQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        List<IntegralVO> integralVOS = integralService.getlistIntegralVO(integralQueryRequest,current,size);
        Page<IntegralVO> integralVOPage = new Page<>();
        integralVOPage.setRecords(integralVOS);
        integralVOPage.setCurrent(current);
        //手动分页,需要获取总数
        List<Integral> list = integralService.list(null);
        integralVOPage.setTotal(list.size());
        return ResultUtils.success(integralVOPage);
    }



    /**
     * 编辑积分表（给用户使用）
     *
     * @param integralEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editIntegral(@RequestBody IntegralEditRequest integralEditRequest, HttpServletRequest request) {
        if (integralEditRequest == null || integralEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Integral integral = new Integral();
        BeanUtils.copyProperties(integralEditRequest, integral);
        // 数据校验
        integralService.validIntegral(integral, false);
        // 判断是否存在
        long id = integralEditRequest.getId();
        Integral oldIntegral = integralService.getById(id);
        ThrowUtils.throwIf(oldIntegral == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
//        if (!oldIntegral.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
        // 操作数据库
        boolean result = integralService.updateById(integral);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
