package com.acertainscientific.meetup.controller;


import com.acertainscientific.meetup.dto.BuildingAddDto;
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
    public boolean addBuilding(@RequestBody BuildingAddDto buildingAddDto){
        buildingService.addBuilding(buildingAddDto);
        return true;
    }

    @DeleteMapping("/add_delete_mapping")
    public boolean deleteBuilding(@RequestParam Integer id){
        if (buildingService.deleteBuilding(id)){
            return true;
        }
        return false;
    }
}
