package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.mapper.BuildingMapper;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BuildingService extends ServiceImpl<BuildingMapper, BuildingModel> implements IBuildingService {

    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void addBuilding(BuildingAddDto buildingAddDto){
        BuildingModel buildingModel = modelMapper.map(buildingAddDto, BuildingModel.class);
        buildingModel.setCreatedAt((int)(System.currentTimeMillis()/1000));
        this.save(buildingModel);
    }

    @Override
    public boolean deleteBuilding(Integer id){
        BuildingModel buildingModel = this.getById(id);
        if (buildingModel != null){
            buildingModel.setIsDeleted(1);
            buildingModel.setDeletedAt((int)(System.currentTimeMillis()/1000));
            this.updateById(buildingModel);
            return true;
        }

        return false;

    }

    @Override
    public boolean updateBuilding(BuildingUpdateDto buildingUpdateDto){
        BuildingModel buildingModel = modelMapper.map(buildingUpdateDto, BuildingModel.class);
        if(buildingModel != null){
            buildingModel.setUpdatedAt((int)(System.currentTimeMillis()/1000));
            this.updateById(buildingModel);
            return true;
        }
        return false;
    }
}
