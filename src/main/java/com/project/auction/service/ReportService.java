package com.project.auction.service;

import com.project.auction.model.Report;
import com.project.auction.model.User;

import java.util.List;

public interface ReportService {
    public List<Report> listReports();
    public void save(Report report);
    public void delete(Report report);
    public Report getReport(Report report);
}
