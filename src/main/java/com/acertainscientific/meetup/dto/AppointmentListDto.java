package com.acertainscientific.meetup.dto;


import lombok.Data;

@Data
public class AppointmentListDto {
    private Integer roomId;
    private Integer startTime;
    private Integer endTime;
    private Integer month;
    private Integer year;
    private Integer date;
}
