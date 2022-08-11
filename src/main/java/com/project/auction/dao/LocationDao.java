package com.project.auction.dao;

import com.project.auction.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationDao extends CrudRepository<Location, Long> {

}
