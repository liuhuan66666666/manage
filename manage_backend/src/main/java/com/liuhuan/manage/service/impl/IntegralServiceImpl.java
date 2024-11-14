package com.liuhuan.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.constant.CommonConstant;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.IntegralMapper;
import com.liuhuan.manage.model.dto.integral.IntegralQueryRequest;
import com.liuhuan.manage.model.entity.Integral;
import com.liuhuan.manage.model.vo.IntegralVO;
import com.liuhuan.manage.service.IntegralService;
import com.liuhuan.manage.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 积分表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Service
@Slf4j
public class IntegralServiceImpl extends ServiceImpl<IntegralMapper, Integral> implements IntegralService {

    @Resource
    private IntegralMapper integralMapper;

    /**
     * 校验数据
     *
     * @param integral
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validIntegral(Integral integral, boolean add) {
        ThrowUtils.throwIf(integral == null, ErrorCode.PARAMS_ERROR);

    }

    /**
     * 获取查询条件
     *
     * @param integralQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Integral> getQueryWrapper(IntegralQueryRequest integralQueryRequest) {
        QueryWrapper<Integral> queryWrapper = new QueryWrapper<>();
        String sortField = integralQueryRequest.getSortField();
        String sortOrder = integralQueryRequest.getSortOrder();
        if (integralQueryRequest == null) {
            return queryWrapper;
        }
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取积分表封装
     *
     * @param integral
     * @param request
     * @return
     */
    @Override
    public IntegralVO getIntegralVO(Integral integral, HttpServletRequest request) {
        // 对象转封装类
        IntegralVO integralVO = IntegralVO.objToVo(integral);
        return integralVO;
    }

    /**
     * 分页获取积分表封装
     *
     * @param integralPage
     * @param request
     * @return
     */
    @Override
    public Page<IntegralVO> getIntegralVOPage(Page<Integral> integralPage, HttpServletRequest request) {
        List<Integral> integralList = integralPage.getRecords();
        Page<IntegralVO> integralVOPage = new Page<>(integralPage.getCurrent(), integralPage.getSize(), integralPage.getTotal());
        if (CollUtil.isEmpty(integralList)) {
            return integralVOPage;
        }
        // 对象列表 => 封装对象列表
        List<IntegralVO> integralVOList = integralList.stream().map(integral -> {
            return IntegralVO.objToVo(integral);
        }).collect(Collectors.toList());


        integralVOPage.setRecords(integralVOList);
        return integralVOPage;
    }

    @Override
    public List<IntegralVO> getlistIntegralVO(IntegralQueryRequest integralQueryRequest, Integer current,Integer size) {
        int offset=(current-1)*size;
        return  integralMapper.getListVO(offset,size);
    }

}
