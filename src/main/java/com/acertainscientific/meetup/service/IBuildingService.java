package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.*;
import com.acertainscientific.meetup.model.BuildingModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IBuildingService extends IService<BuildingModel> {

    /**
     * add a building to our database
     * @param buildingAddDto the building information
     *
     */
    void addBuilding(BuildingAddDto buildingAddDto);

    boolean deleteBuilding(Integer id);

    boolean updateBuilding(BuildingUpdateDto buildingUpdateDto);

     PageResponseDto listBuilding(Integer page, Integer pageSize, String name);

     boolean dbDecision(Integer id);

     DetailBuildingDto detailBuilding(Integer id);

     List<BuildingListDto> listAllBuildings(String name);

}
