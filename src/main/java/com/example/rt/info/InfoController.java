package com.example.rt.info;

import com.example.rt.info.annual_report.AnnualReport;
import com.example.rt.info.annual_report.AnnualReportService;
import com.example.rt.info.annual_report.requests.PostAnnualReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/info")
public class InfoController {
    private final AnnualReportService annualReportService;

    @GetMapping("/contacts")
    String getContacts() {
        return null;
    }

    @GetMapping("/what-union-provides")
    String getWhatUnionProvides() {
        return null;
    }

    @GetMapping("/history-of-union")
    String getHistoryOfUnion() {
        return null;
    }

    @GetMapping("/annual-reports")
    public ResponseEntity<List<AnnualReport>> getAnnualReport(
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize
    ) {
        return ResponseEntity.ok(annualReportService.getAllAnnualReports(pageNo, pageSize));
    }

    @PostMapping("/annual-reports")
    public ResponseEntity<AnnualReport> postAnnualReport(
            @RequestBody PostAnnualReportRequest annualReport
    ) {
        return ResponseEntity.ok(annualReportService.postReport(annualReport));
    }

    @GetMapping("/union-plans")
    String getUnionPlans() {
        return null;
    }
}
