package com.project.auction.service;

import com.project.auction.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> listCategories();
    public void save(Category category);
    public void delete(Category category);
    public Category getCategory(Category category);
}
