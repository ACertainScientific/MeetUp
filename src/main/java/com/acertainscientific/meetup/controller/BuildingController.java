package com.acertainscientific.meetup.controller;


import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.BuildingAddDto;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.dto.UrlDto;
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
    public AjaxResult addBuilding(@RequestBody BuildingAddDto buildingAddDto, HttpServletRequest request){
        buildingService.addBuilding(buildingAddDto);
        return AjaxResult.success().auth(request);
    }

    @DeleteMapping("/building/delete")
    public AjaxResult deleteBuilding(@RequestParam Integer id, HttpServletRequest request){
        if (buildingService.deleteBuilding(id)){
            return AjaxResult.success().auth(request);
        }
        return AjaxResult.error().auth(request);
    }
    @PostMapping("/building/update")
    public AjaxResult updateBuilding(@RequestBody BuildingUpdateDto buildingUpdateDto, HttpServletRequest request){
        if(buildingService.updateBuilding(buildingUpdateDto)){
            return AjaxResult.success().auth(request);
        }
        return AjaxResult.error().auth(request);
    }

    @GetMapping("/building/list")
    public AjaxResult listBuilding(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "page-size") Integer pageSize,
                                   @RequestParam(value = "name",required = false, defaultValue = "") String name, HttpServletRequest request){
        return AjaxResult.success(buildingService.listBuilding(page,pageSize,name)).auth(request);
    }
    @GetMapping("/building/detail")
    public AjaxResult detailBuilding(@RequestParam(value = "id") Integer id, HttpServletRequest request){
        if (buildingService.dbDecision(id)){
            return AjaxResult.success(buildingService.detailBuilding(id)).auth(request);
        }
        return AjaxResult.error().auth(request);
    }


    @RequestMapping("/qrnologo")
    @ApiOperation(value = "qrcode", notes = "Generate qrcode by given website")
    public void qrnologo(HttpServletRequest request, HttpServletResponse response, @RequestBody UrlDto urlDto) {
        String requestUrl = urlDto.getUrl();
        try {
            OutputStream os = response.getOutputStream();
            QrCodeUtil.encode(requestUrl, null, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/building/list/all")
    public AjaxResult listAllBuildings(@RequestParam(value = "name",required = false, defaultValue = "") String name, HttpServletRequest request){
        return AjaxResult.success(buildingService.listAllBuildings(name)).auth(request);
    }
}
