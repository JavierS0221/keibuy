package com.project.auction.service;

import com.project.auction.repository.ReportRepository;
import com.project.auction.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Report> listReports() {
        return (List<Report>) reportRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Report report) {
        reportRepository.save(report);
    }

    @Override
    @Transactional
    public void delete(Report report) {
        reportRepository.delete(report);
    }

    @Override
    @Transactional(readOnly = true)
    public Report getReport(Report report) {
        return reportRepository.findById(report.getId()).orElse(null);
    }
}
