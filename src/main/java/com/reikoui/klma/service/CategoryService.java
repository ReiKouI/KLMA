package com.reikoui.klma.service;

import com.reikoui.klma.dao.CategoryMapper;
import com.reikoui.klma.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> listCategories(
    ) {
        return categoryMapper.listCategories();
    }



}
