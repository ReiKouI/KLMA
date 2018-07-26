package com.reikoui.klma.dao;

import com.reikoui.klma.domain.Plan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PlanMapper {


    int insert(Plan plan);

    @Select("select * from plan where id = #{id}")
    Plan selectPlanById(@Param("id") int id);

}
