package com.project.auction.service;

import com.project.auction.model.AuctionWished;

import java.util.List;

public interface AuctionWishedService {
    public List<AuctionWished> listAuctionsWished();
    public void save(AuctionWished auctionWished);
    public void delete(AuctionWished auctionWished);
    public AuctionWished getAuctionWished(AuctionWished auctionWished);
}
