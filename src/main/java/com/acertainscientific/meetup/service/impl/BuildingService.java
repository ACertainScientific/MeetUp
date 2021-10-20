package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.*;
import com.acertainscientific.meetup.mapper.BuildingMapper;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import com.acertainscientific.meetup.util.RedisUtil;
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
    private RedisUtil redisUtil;

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
            if (redisUtil.hasKey("Building:" + buildingModel.getId())){
                redisUtil.del("Building:" + buildingModel.getId());
            }
            return true;
        }

        return false;

    }

    @Override
    public boolean updateBuilding(BuildingUpdateDto buildingUpdateDto){
        BuildingModel buildingModel = modelMapper.map(buildingUpdateDto, BuildingModel.class);
        Integer currentId = buildingModel.getId();
        BuildingModel buildingModel1 = this.getById(currentId);
        if(buildingModel1 != null && buildingModel1.getIsDeleted() != 1 && buildingModel.getFloorEnd() >= buildingModel.getFloorStart()){
            buildingModel.setUpdatedAt((int)(System.currentTimeMillis()/1000));
            this.updateById(buildingModel);
            if (redisUtil.hasKey("Building:" + buildingModel.getId())){
                redisUtil.del("Building:" + buildingModel.getId());
            }
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
        if (redisUtil.hasKey("Building:" + id)){
            return true;
        }
        BuildingModel buildingModel = this.getById(id);
        return buildingModel != null && buildingModel.getIsDeleted() != 1;
    }

    @Override
    public DetailBuildingDto detailBuilding(Integer id){
        if (redisUtil.hasKey("Building:" + id)){
            return modelMapper.map(redisUtil.get("Building:"+id), DetailBuildingDto.class);
        }
        BuildingModel buildingModel = this.getById(id);
        DetailBuildingDto  detailBuildingDto = new DetailBuildingDto();
        detailBuildingDto.setName(buildingModel.getName());
        detailBuildingDto.setFloorStart(buildingModel.getFloorStart());
        detailBuildingDto.setFloorEnd(buildingModel.getFloorEnd());
        redisUtil.set("Building:"+buildingModel.getId().toString(), detailBuildingDto);
        return detailBuildingDto;

    }

}
