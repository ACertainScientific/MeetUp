package com.acertainscientific.meetup.controller;


import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Building")
public class BuildingController {

    @Autowired
    IBuildingService buildingService;

    @PostMapping("/add_building")
    public AjaxResult addBuilding(@RequestBody BuildingAddDto buildingAddDto){
        buildingService.addBuilding(buildingAddDto);
        return AjaxResult.success();
    }

    @DeleteMapping("/delete_building")
    public AjaxResult deleteBuilding(@RequestParam Integer id){
        if (buildingService.deleteBuilding(id)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
    @PostMapping("/update_building")
    public AjaxResult updateBuilding(@RequestBody BuildingUpdateDto buildingUpdateDto){
        if(buildingService.updateBuilding(buildingUpdateDto)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @GetMapping("/list_building")
    public AjaxResult listBuilding(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "page_size") Integer pageSize,
                                   @RequestParam(value = "name") String name){
        return AjaxResult.success(buildingService.listBuilding(page,pageSize,name));
    }
    @GetMapping("/detail_building")
    public AjaxResult detailBuilding(@RequestParam(value = "id") Integer id){
        if (buildingService.dbDecision(id)){
            return AjaxResult.success(buildingService.detailBuilding(id));
        }
        return AjaxResult.error();
    }

}
