package com.example.audibook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDTO {

    private Long id;

    private String eventName;

    private String eventDate;

    private String startTime;

    private String endTime;

    private String purpose;
}

