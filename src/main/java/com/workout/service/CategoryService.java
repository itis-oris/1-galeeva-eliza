package com.workout.service;

import com.workout.mapper.CategoryRecordMapper;
import com.workout.model.Category;
import com.workout.repository.dao.CategoryDao;

import java.util.List;
import java.util.Optional;

public class CategoryService {

    private final CategoryDao categoryDao = new CategoryDao(new CategoryRecordMapper());

    public Optional<Long> getIdByCategoryName(String categoryName) {
        return categoryDao.getIdByCategoryName(categoryName);
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
