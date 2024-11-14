package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.integral.IntegralQueryRequest;
import com.liuhuan.manage.model.entity.Integral;
import com.liuhuan.manage.model.vo.IntegralVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 积分表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
public interface IntegralService extends IService<Integral> {

    /**
     * 校验数据
     *
     * @param integral
     * @param add 对创建的数据进行校验
     */
    void validIntegral(Integral integral, boolean add);

    /**
     * 获取查询条件
     *
     * @param integralQueryRequest
     * @return
     */
    QueryWrapper<Integral> getQueryWrapper(IntegralQueryRequest integralQueryRequest);
    
    /**
     * 获取积分表封装
     *
     * @param integral
     * @param request
     * @return
     */
    IntegralVO getIntegralVO(Integral integral, HttpServletRequest request);

    /**
     * 分页获取积分表封装
     *
     * @param integralPage
     * @param request
     * @return
     */
    Page<IntegralVO> getIntegralVOPage(Page<Integral> integralPage, HttpServletRequest request);



    List<IntegralVO> getlistIntegralVO(IntegralQueryRequest integralQueryRequest,Integer current,Integer size);
}
