package com.example.rt.info.annual_report;

import com.example.rt.info.annual_report.requests.PostAnnualReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnualReportService {
    private final AnnualReportRepository annualReportRepository;

    public List<AnnualReport> getAllAnnualReports(int pageNo, int pageSize) {
        return annualReportRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
    }

    public AnnualReport postReport(PostAnnualReportRequest request) {
        return annualReportRepository.save(AnnualReport.builder()
                .filename(request.filename())
                .name(request.name())
                .build());
    }
}
