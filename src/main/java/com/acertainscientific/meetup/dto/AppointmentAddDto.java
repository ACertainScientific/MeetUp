package com.acertainscientific.meetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentAddDto {
    private Integer room_id;
    private Integer start_time;
    private Integer end_time;
    private String user_id;
    private Integer month;
    private Integer year;
    private Integer date;
}
