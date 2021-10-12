package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.*;
import com.acertainscientific.meetup.mapper.BuildingMapper;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public PageResponseDto listBuilding(Integer page, Integer pageSize, String name){
        PageHelper.startPage(page,pageSize);
        List<BuildingModel> buildingModels = buildingMapper.listBuilding(name);
        PageInfo pageInfo =new PageInfo(buildingModels);
        List<BuildingListDto> buildingListDtos = modelMapper.map(buildingModels,new TypeToken<List<BuildingListDto>>(){}.getType());
        PageResponseDto pageResponseDto= new PageResponseDto();
        pageResponseDto.setList(buildingListDtos);
        pageResponseDto.setTotalCount((int) pageInfo.getTotal());
        pageResponseDto.setPageCount(pageInfo.getPages());
        pageResponseDto.setPerPage(pageInfo.getPageSize());
        pageResponseDto.setCurrentPage(pageInfo.getPageNum());
        return pageResponseDto;

    }

    @Override
    public List<BuildingListDto> listAllBuildings(String name){
        List<BuildingModel> buildingModels = buildingMapper.listBuilding(name);
        List<BuildingListDto> buildingListDtos =  modelMapper.map(buildingModels, new TypeToken<List<BuildingListDto>>(){}.getType());
        return buildingListDtos;
    }

    @Override
    public boolean dbDecision(Integer id){
        BuildingModel buildingModel = this.getById(id);
        return buildingModel != null && buildingModel.getIsDeleted() != 1;
    }

    @Override
    public DetailBuildingDto detailBuilding(Integer id){
        BuildingModel buildingModel = this.getById(id);
        DetailBuildingDto  detailBuildingDto = new DetailBuildingDto();
        detailBuildingDto.setName(buildingModel.getName());
        detailBuildingDto.setFloorStart(buildingModel.getFloorStart());
        detailBuildingDto.setFloorEnd(buildingModel.getFloorEnd());
        return detailBuildingDto;

    }

}
