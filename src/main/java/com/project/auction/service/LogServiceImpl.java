package com.project.auction.service;

import com.project.auction.dao.LogDao;
import com.project.auction.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @Transactional(readOnly = true)
    public List<Log> listLogs() {
        return (List<Log>) logDao.findAll();
    }

    @Override
    @Transactional
    public void save(Log log) {
        logDao.save(log);
    }

    @Override
    @Transactional
    public void delete(Log log) {
        logDao.delete(log);
    }

    @Override
    @Transactional(readOnly = true)
    public Log getLog(Log log) {
        return logDao.findById(log.getId()).orElse(null);
    }
}
