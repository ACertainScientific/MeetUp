package com.acertainscientific.meetup.controller;


import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuildingController {

    @Autowired
    IBuildingService buildingService;

    @PostMapping("/add_building")
    public AjaxResult addBuilding(@RequestBody BuildingAddDto buildingAddDto){
        buildingService.addBuilding(buildingAddDto);
        return AjaxResult.success();
    }

    @DeleteMapping("/add_delete_mapping")
    public AjaxResult deleteBuilding(@RequestParam Integer id){
        if (buildingService.deleteBuilding(id)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
    @PostMapping("/update_building")
    public boolean updateBuilding(@RequestBody BuildingUpdateDto buildingUpdateDto){
        if(buildingService.updateBuilding(buildingUpdateDto)){
            return true;
        }
        return false;
    }
}
