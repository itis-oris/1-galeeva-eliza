package com.workout.repository.dao;

import com.workout.mapper.CategoryRecordMapper;
import com.workout.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao extends CustomAbstractDao<Category> {
    //language=sql
    private static final String SQL_GET_ALL = "SELECT * FROM category";

    public CategoryDao(CategoryRecordMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<Long> getIdByCategoryName(String categoryName) {
        List<Category> categories = getAll();
        for (Category category : categories) {
            if (category.name().equals(categoryName))
                return Optional.of(category.id());
        }
        return Optional.empty();
    }

    public List<Category> getAll() {
        PreparedStatement preparedStatement = getPreparedStatement(SQL_GET_ALL, Optional.empty());
        try {
            ResultSet executedQuery = preparedStatement.executeQuery();
            List<Category> categoryList = new ArrayList<>();
            while (executedQuery.next()) {
                categoryList.add(mapper.mapRow(executedQuery, 0));
            }
            return categoryList;
        } catch (SQLException e) {
            throw new DaoException("couldn't get all elements sorry :(");
        }
    }
}
