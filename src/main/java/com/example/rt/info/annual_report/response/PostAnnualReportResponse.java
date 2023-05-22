package com.example.rt.info.annual_report.response;

import com.example.rt.data.ResponseBase;
import com.example.rt.data.Status;
import com.example.rt.info.annual_report.AnnualReport;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PostAnnualReportResponse extends ResponseBase{
    AnnualReport report;
}
