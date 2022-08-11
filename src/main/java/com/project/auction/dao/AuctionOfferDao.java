package com.project.auction.dao;

import com.project.auction.model.AuctionOffer;
import org.springframework.data.repository.CrudRepository;

public interface AuctionOfferDao extends CrudRepository<AuctionOffer, Long> {

}
