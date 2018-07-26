package com.reikoui.klma.dao;


import com.reikoui.klma.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    @Select("select * from category")
    List<Category> listCategories();
}
