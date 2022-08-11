package com.project.auction.service;

import com.project.auction.model.AuctionOffer;

import java.util.List;

public interface AuctionOfferService {
    public List<AuctionOffer> listAuctionOffers();
    public void save(AuctionOffer auctionOffer);
    public void delete(AuctionOffer auctionOffer);
    public AuctionOffer getAuctionOffer(AuctionOffer auctionOffer);
}
