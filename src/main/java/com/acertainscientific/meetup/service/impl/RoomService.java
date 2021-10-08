package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.*;
import com.acertainscientific.meetup.mapper.RoomMapper;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.service.IRoomService;
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
public class RoomService extends ServiceImpl<RoomMapper, RoomModel> implements IRoomService {
    @Autowired
    RoomMapper roomMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BuildingService buildingService;

    @Override
    public RoomModel searchById(int id){
        return roomMapper.selectById(id);
    }

    @Override
    public boolean addRoom(RoomAddDto roomAddDto){
        RoomModel roomModel = modelMapper.map(roomAddDto, RoomModel.class);
        log.info(String.valueOf(roomAddDto));
        BuildingModel buildingModel = buildingService.getById(roomModel.getBuildingId());
        if (buildingModel == null || buildingModel.getIsDeleted() == 1 || roomModel.getFloor() > buildingModel.getFloorEnd() || roomModel.getFloor() < buildingModel.getFloorStart()){
            return false;
        }
        roomModel.setCreatedAt((int)(System.currentTimeMillis()/1000));
        this.save(roomModel);
        return true;
    }

    @Override
    public boolean deleteRoom(Integer id){
        RoomModel roomModel = this.getById(id);
        if (roomModel != null){
            roomModel.setIsDeleted(1);
            roomModel.setDeletedAt((int) (System.currentTimeMillis() / 1000));
            this.updateById(roomModel);
            return true;
        }
        return false;
    }

    @Override
    public PageResponseDto listRoom(Integer page, Integer pageSize, Integer buildingId, Integer floor, String roomName){
        PageHelper.startPage(page, pageSize);
        List<RoomModel> roomModels = roomMapper.listRoom(buildingId, floor, roomName);
        PageInfo pageInfo = new PageInfo(roomModels);
        List<RoomListDto> roomListDtos = modelMapper.map(roomModels,new TypeToken<List<RoomModel>>(){}.getType());

        PageResponseDto pageResponseDto= new PageResponseDto();
        pageResponseDto.setList(roomListDtos);
        pageResponseDto.setTotalCount((int) pageInfo.getTotal());
        pageResponseDto.setPageCount(pageInfo.getPages());
        pageResponseDto.setPerPage(pageInfo.getPageSize());
        pageResponseDto.setCurrentPage(pageInfo.getPageNum());
        return pageResponseDto;
    }

    @Override
    public boolean roomDecision(Integer id){
        RoomModel roomModel = this.getById(id);
        return roomModel != null && roomModel.getIsDeleted() != 1;
    }

    @Override
    public DetailRoomDto detailRoomDto(Integer id){
        RoomModel roomModel = this.getById(id);
        DetailRoomDto detailRoomDto = new DetailRoomDto();
        detailRoomDto.setName(roomModel.getRoomName());
        detailRoomDto.setBuildingId(roomModel.getBuildingId());
        detailRoomDto.setFloor(roomModel.getFloor());
        return detailRoomDto;
    }

}
