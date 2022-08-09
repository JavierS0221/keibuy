package com.project.auction.service;

import com.project.auction.dao.ReportDao;
import com.project.auction.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    @Transactional(readOnly = true)
    public List<Report> listReports() {
        return (List<Report>) reportDao.findAll();
    }

    @Override
    @Transactional
    public void save(Report report) {
        reportDao.save(report);
    }

    @Override
    @Transactional
    public void delete(Report report) {
        reportDao.delete(report);
    }

    @Override
    @Transactional(readOnly = true)
    public Report getReport(Report report) {
        return reportDao.findById(report.getId()).orElse(null);
    }
}
