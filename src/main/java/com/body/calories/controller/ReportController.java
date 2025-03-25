package com.body.calories.controller;


import com.body.calories.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{date}/{user_id}")
    @Operation (summary = "Get day report for user to date")
    public ResponseEntity<?> getDayReport(@PathVariable String date, @PathVariable long user_id) {
        return  ResponseEntity.status(HttpStatus.OK).body(reportService.getDayReport(date, user_id));
    }


    @GetMapping("/history/{user_id}")
    @Operation (summary = "Get meal history for user with pagination")
    public ResponseEntity<?> getHistory(@PathVariable long user_id, Pageable pageable) {
        return  ResponseEntity.status(HttpStatus.OK).body(reportService.getHistoryReport(user_id, pageable));
    }
}
