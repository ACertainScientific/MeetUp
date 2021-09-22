package com.acertainscientific.meetup.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingAddDto {


    private String name;
    private int floor_start;
    private int floor_end;
}
