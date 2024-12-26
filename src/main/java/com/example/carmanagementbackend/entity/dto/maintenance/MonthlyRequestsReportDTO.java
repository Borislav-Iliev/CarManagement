package com.example.carmanagementbackend.entity.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyRequestsReportDTO {
    private YearMonth yearMonth;
    private long requests;
}
