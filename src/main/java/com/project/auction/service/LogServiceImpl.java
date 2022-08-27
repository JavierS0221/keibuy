package com.project.auction.service;

import com.project.auction.repository.LogRepository;
import com.project.auction.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Log> listLogs() {
        return (List<Log>) logRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Log log) {
        logRepository.save(log);
    }

    @Override
    @Transactional
    public void delete(Log log) {
        logRepository.delete(log);
    }

    @Override
    @Transactional(readOnly = true)
    public Log getLog(Log log) {
        return logRepository.findById(log.getId()).orElse(null);
    }
}
