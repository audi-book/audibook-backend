package com.example.audibook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String eventName;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String status;
    private int userId;
    private String userName;
    private String userEmail;
}
