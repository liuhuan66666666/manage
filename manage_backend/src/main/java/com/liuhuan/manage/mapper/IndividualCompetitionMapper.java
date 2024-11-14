package com.liuhuan.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuhuan.manage.model.entity.IndividualCompetition;
import com.liuhuan.manage.model.vo.IndividualCompetitionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 29832
* @description 针对表【individual_competition(个人赛信息表)】的数据库操作Mapper
* @createDate 2024-11-09 19:58:16
* @Entity com.liuhuan.manage.model.entity.IndividualCompetition
*/
public interface IndividualCompetitionMapper extends BaseMapper<IndividualCompetition> {

    void saveCompetitionStudentRelation(@Param("id") Long competitionId, @Param("studentId") Long studentId);

    void saveCompetitionTeacherRelation(@Param("id") Long id, @Param("teacherId") Long teacherId);

    Boolean deleteCompetitionTeacherRelation(@Param("id")  long id);

    Boolean deleteCompetitionStudentRelation( @Param("id") long id);


    //总数查询
    Integer countCompetitionVO(@Param("name") String name, @Param("year") Integer year, @Param("status") Integer status);

    //分页查询
    List<IndividualCompetitionVO> getListCompetitionVO(@Param("name") String name,@Param("year") Integer year,@Param("status") Integer status,@Param("offset") Integer offset,@Param("size") Integer size);

    void updateCompetitionStudentRelation(@Param("id") long id,  @Param("studentId") Long studentId);

    void updateCompetitionTeacherRelation(@Param("id") long id, @Param("teacherId") Long teacherId);
}




