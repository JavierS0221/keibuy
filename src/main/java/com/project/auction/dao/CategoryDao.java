package com.project.auction.dao;

import com.project.auction.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category, Long> {

}
