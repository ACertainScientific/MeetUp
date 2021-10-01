package com.acertainscientific.meetup.mapper;

import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingListDto;
import com.acertainscientific.meetup.model.BuildingModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BuildingMapper extends BaseMapper<BuildingModel> {

//    void addBuilding(BuildingAddDto buildingAddDto);
    List<BuildingModel> listBuilding(@Param("name")String name);
}
