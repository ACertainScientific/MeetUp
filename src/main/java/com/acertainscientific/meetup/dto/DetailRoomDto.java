package com.acertainscientific.meetup.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailRoomDto {
    private String name;
    private Integer floor;
    private Integer buildingId;
    private String buildingName;
}
