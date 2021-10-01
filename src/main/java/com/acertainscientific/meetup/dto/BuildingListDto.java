package com.acertainscientific.meetup.dto;

import lombok.Data;

@Data
public class BuildingListDto {
    private Integer id;
    private String name;
    private Integer floorStart;
    private Integer floorEnd;
}
