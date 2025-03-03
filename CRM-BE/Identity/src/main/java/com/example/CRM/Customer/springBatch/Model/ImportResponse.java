package com.example.CRM.Customer.springBatch.Model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ImportResponse {
    private final int totalRecords;
    private final LocalDateTime importedAt;
}
