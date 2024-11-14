package com.liuhuan.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhuan.manage.model.entity.Integral;
import com.liuhuan.manage.model.vo.IntegralVO;

import java.util.List;

/**
* @author 29832
* @description 针对表【integral】的数据库操作Mapper
* @createDate 2024-10-10 10:25:17
* @Entity com.liuhuan.manage.model.entity.Integral
*/
public interface IntegralMapper extends BaseMapper<Integral> {


    List<IntegralVO> getListVO(Integer offset, Integer size);
}




