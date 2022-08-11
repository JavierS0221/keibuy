package com.project.auction.service;

import com.project.auction.model.Log;

import java.util.List;

public interface LogService {
    public List<Log> listLogs();
    public void save(Log log);
    public void delete(Log log);
    public Log getLog(Log log);
}
