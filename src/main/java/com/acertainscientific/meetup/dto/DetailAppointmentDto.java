package com.acertainscientific.meetup.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DetailAppointmentDto {
    private Integer roomId;

    private Integer startTime;

    private Integer endTime;

    private Integer userId;

    private Integer month;

    private Integer year;

    private Integer date;
}
