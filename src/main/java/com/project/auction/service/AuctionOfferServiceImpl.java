package com.project.auction.service;

import com.project.auction.dao.AuctionOfferDao;
import com.project.auction.model.AuctionOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuctionOfferServiceImpl implements AuctionOfferService {

    @Autowired
    private AuctionOfferDao auctionOfferDao;

    @Override
    @Transactional(readOnly = true)
    public List<AuctionOffer> listAuctionOffers() {
        return (List<AuctionOffer>) auctionOfferDao.findAll();
    }

    @Override
    @Transactional
    public void save(AuctionOffer auctionOffer) {
        auctionOfferDao.save(auctionOffer);
    }

    @Override
    @Transactional
    public void delete(AuctionOffer auctionOffer) {
        auctionOfferDao.delete(auctionOffer);
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionOffer getAuctionOffer(AuctionOffer auctionOffer) {
        return auctionOfferDao.findById(auctionOffer.getPerson().getId()).orElse(null);
    }
}
