package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.mapper.BuildingMapper;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BuildingService extends ServiceImpl<BuildingMapper, BuildingModel> implements IBuildingService {

    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BuildingModel addBuilding(BuildingAddDto buildingAddDto){
        return buildingMapper.addBuilding(buildingAddDto);
    }
}
