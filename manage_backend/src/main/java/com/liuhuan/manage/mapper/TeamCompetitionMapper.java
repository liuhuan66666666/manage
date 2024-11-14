package com.liuhuan.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhuan.manage.model.entity.TeamCompetition;
import com.liuhuan.manage.model.vo.TeamCompetitionVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 29832
* @description 针对表【team_competition(团体赛信息表)】的数据库操作Mapper
* @createDate 2024-11-12 08:51:36
* @Entity com.liuhuan.manage.model.entity.TeamCompetition
*/
public interface TeamCompetitionMapper extends BaseMapper<TeamCompetition> {

    Long getProjectId(@Param("projectName") String projectName);


    int countTeamCompetitions(@Param("name") String name,@Param("year") Integer year,@Param("status") Integer status );

    List<TeamCompetitionVO> listTeamCompetitionVOS(@Param("offset") Integer offset, @Param("size") Integer pageSize , @Param("name") String name, @Param("year") Integer year, @Param("status") Integer status );
}




