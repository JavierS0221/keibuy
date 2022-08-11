package com.project.auction.dao;

import com.project.auction.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageDao extends CrudRepository<Image, String> {

}
