package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.model.BuildingModel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IBuildingService extends IService<BuildingModel> {

    /**
     * add a building to our database
     * @param buildingAddDto the building information
     *
     */
    void addBuilding(BuildingAddDto buildingAddDto);

    boolean deleteBuilding(Integer id);

    boolean updateBuilding(BuildingUpdateDto buildingUpdateDto);

}
