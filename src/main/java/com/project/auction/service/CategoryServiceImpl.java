package com.project.auction.service;

import com.project.auction.repository.CategoryRepository;
import com.project.auction.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(Category category) {
        return categoryRepository.findById(category.getId()).orElse(null);
    }
}
