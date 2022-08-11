package com.project.auction.service;

import com.project.auction.dao.CategoryDao;
import com.project.auction.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return (List<Category>) categoryDao.findAll();
    }

    @Override
    @Transactional
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    @Transactional
    public void delete(Category category) {
        categoryDao.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(Category category) {
        return categoryDao.findById(category.getId()).orElse(null);
    }
}
