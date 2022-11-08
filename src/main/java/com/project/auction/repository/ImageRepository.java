package com.project.auction.repository;

import com.project.auction.model.AvatarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<AvatarImage, Long> {

}
