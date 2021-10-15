package com.acertainscientific.meetup.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateDto {
    private int id;
    private String roomName;
    private Integer floor;
    private Integer buildingId;
}
