package com.acertainscientific.meetup.controller;


import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.service.IBuildingService;
import com.acertainscientific.meetup.util.QrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@Api(tags = "Building")
public class BuildingController {

    @Autowired
    IBuildingService buildingService;

    @PostMapping("/building/add")
    public AjaxResult addBuilding(@RequestBody BuildingAddDto buildingAddDto){
        buildingService.addBuilding(buildingAddDto);
        return AjaxResult.success();
    }

    @DeleteMapping("/building/delete")
    public AjaxResult deleteBuilding(@RequestParam Integer id){
        if (buildingService.deleteBuilding(id)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
    @PostMapping("/building/update")
    public AjaxResult updateBuilding(@RequestBody BuildingUpdateDto buildingUpdateDto){
        if(buildingService.updateBuilding(buildingUpdateDto)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @GetMapping("/building/list")
    public AjaxResult listBuilding(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "page-size") Integer pageSize,
                                   @RequestParam(value = "name",required = false, defaultValue = "") String name){
        return AjaxResult.success(buildingService.listBuilding(page,pageSize,name));
    }
    @GetMapping("/building/detail")
    public AjaxResult detailBuilding(@RequestParam(value = "id") Integer id){
        if (buildingService.dbDecision(id)){
            return AjaxResult.success(buildingService.detailBuilding(id));
        }
        return AjaxResult.error();
    }

//    //生成不带logo的二维码到response, 测试，还有点问题
//    @RequestMapping("/qrnologo")
//    public void qrnologo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") Integer id) {
//        String requestUrl = "localhost:8089/building/detail/" + id;
//        try {
//            OutputStream os = response.getOutputStream();
//            QrCodeUtil.encode(requestUrl, null, os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @GetMapping("/building/list/all")
    public AjaxResult listAllBuildings(@RequestParam(value = "name",required = false, defaultValue = "") String name){
        return AjaxResult.success(buildingService.listAllBuildings(name));
    }
}
