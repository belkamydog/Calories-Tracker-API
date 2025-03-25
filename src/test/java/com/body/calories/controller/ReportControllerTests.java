package com.body.calories.controller;

import com.body.calories.exceptions.UserNotFoundException;
import com.body.calories.models.reports.DayCountCaloriesReport;
import com.body.calories.service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportService reportService;

    @Test
    void getDayReport_ShouldReturnReport() throws Exception {
        String testDate = LocalDate.now().toString();
        long userId = 1L;
        DayCountCaloriesReport mockReport = new DayCountCaloriesReport(
                LocalDate.now().toString(), 1000, 999.0, true, null);

        Mockito.when(reportService.getDayReport(testDate, userId))
                .thenReturn(mockReport);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/{date}/{user_id}", testDate, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calories").value(1000));
    }

    @Test
    void getDayReport_ShouldReturnNotFound_WhenInvalidUser() throws Exception {
        String testDate = "2023-01-01";
        long invalidUserId = 999L;
        Mockito.when(reportService.getDayReport(testDate, invalidUserId))
                .thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/{date}/{user_id}", testDate, invalidUserId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDayReport_ShouldReturnBadRequest_WhenInvalidDateFormat() throws Exception {
        String invalidDate = "01-01-2023";
        long userId = 1L;
        Mockito.when(reportService.getDayReport(invalidDate, userId)).thenThrow();
        mockMvc.perform(MockMvcRequestBuilders.get("/report/{date}/{user_id}", invalidDate, userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getHistory_ShouldReturnNotFound_WhenInvalidUser() throws Exception {
        long invalidUserId = 999L;
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(reportService.getHistoryReport(invalidUserId, pageable))
                .thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/report/history/{user_id}", invalidUserId)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getHistory_ShouldReturnBadRequest_WhenInvalidPagination() throws Exception {
        long userId = 1L;
        Mockito.when(reportService.getHistoryReport(userId, PageRequest.of(1, 100000)));
        mockMvc.perform(MockMvcRequestBuilders.get("/report/history/{user_id}", userId)
                        .param("page", "-1")
                        .param("size", "1000")) // Превышение максимального размера страницы
                .andExpect(status().isBadRequest());
    }
}
