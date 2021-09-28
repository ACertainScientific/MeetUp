package com.acertainscientific.meetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingUpdateDto {
    private int id;
    private String name;
    private int floor_start;
    private int floor_end;
}
