package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionAddRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionEditRequest;
import com.liuhuan.manage.model.dto.individualCompetition.IndividualCompetitionQueryRequest;
import com.liuhuan.manage.model.entity.IndividualCompetition;
import com.liuhuan.manage.model.vo.IndividualCompetitionVO;

/**
 * 个人竞赛表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface IndividualCompetitionService extends IService<IndividualCompetition> {

    /**
     * 校验数据
     *
     * @param individualCompetition
     * @param add 对创建的数据进行校验
     */
    void validIndividualCompetition(IndividualCompetition individualCompetition, boolean add);

    /**
     * 获取查询条件
     *
     * @param individualCompetitionQueryRequest
     * @return
     */
    QueryWrapper<IndividualCompetition> getQueryWrapper(IndividualCompetitionQueryRequest individualCompetitionQueryRequest);
    
    /**
     * 获取个人竞赛表封装
     *
     * @param individualCompetition
     * @return
     */
    IndividualCompetitionVO getIndividualCompetitionVO(IndividualCompetition individualCompetition);

    /**
     * 分页获取个人竞赛表封装
     *
     * @param individualCompetitionPage
     * @return
     */
    Page<IndividualCompetitionVO> getIndividualCompetitionVOPage(Page<IndividualCompetition> individualCompetitionPage);


    /**
     * 添加个人竞赛信息
     *
     */
    Long addIndividualCompetition(IndividualCompetitionAddRequest individualCompetitionAddRequest);

    Boolean deleteIndividualCompetition(long id);


    /*
     *
     * 获取个人竞赛表VO
     */
    Page<IndividualCompetitionVO> getListlCompetitionVO(IndividualCompetitionQueryRequest individualCompetitionQueryRequest);


    Boolean editIndividualCompetition(IndividualCompetitionEditRequest individualCompetitionEditRequest);
}
