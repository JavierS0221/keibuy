package com.project.auction.service;

import com.project.auction.dao.AuctionWishedDao;
import com.project.auction.model.AuctionWished;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuctionWishedServiceImpl implements AuctionWishedService {

    @Autowired
    private AuctionWishedDao auctionWishedDao;

    @Override
    @Transactional(readOnly = true)
    public List<AuctionWished> listAuctionsWished() {
        return (List<AuctionWished>) auctionWishedDao.findAll();
    }

    @Override
    @Transactional
    public void save(AuctionWished auctionWished) {
        auctionWishedDao.save(auctionWished);
    }

    @Override
    @Transactional
    public void delete(AuctionWished auctionWished) {
        auctionWishedDao.delete(auctionWished);
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionWished getAuctionWished(AuctionWished auctionWished) {
        return auctionWishedDao.findById(auctionWished.getPerson().getId()).orElse(null);
    }
}
