package com.acertainscientific.meetup.controller;


import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BuildingController {

    @Autowired
    IBuildingService buildingService;

    @PostMapping("/add_building")
    public BuildingModel addBuilding(@RequestBody BuildingAddDto buildingAddDto){
        return buildingService.addBuilding(buildingAddDto);
    }
}
