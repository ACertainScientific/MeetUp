package com.acertainscientific.meetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentAddDto {
    private Integer roomId;
    private Integer startTime;
    private Integer endTime;
    private String userId;
    private Integer month;
    private Integer year;
    private Integer date;
}
