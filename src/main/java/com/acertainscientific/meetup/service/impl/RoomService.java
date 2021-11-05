package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.*;
import com.acertainscientific.meetup.mapper.RoomMapper;
import com.acertainscientific.meetup.model.BuildingModel;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.model.UserModel;
import com.acertainscientific.meetup.service.IRoomService;
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
public class RoomService extends ServiceImpl<RoomMapper, RoomModel> implements IRoomService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BuildingService buildingService;

    @Override
    public RoomModel searchById(int id) {
        return roomMapper.selectById(id);
    }

    @Override
    public boolean addRoom(RoomAddDto roomAddDto) {
        RoomModel roomModel = modelMapper.map(roomAddDto, RoomModel.class);
        log.info(String.valueOf(roomAddDto));
        BuildingModel buildingModel = buildingService.getById(roomModel.getBuildingId());
        if (buildingModel == null || buildingModel.getIsDeleted() == 1 || roomModel.getFloor() > buildingModel.getFloorEnd() || roomModel.getFloor() < buildingModel.getFloorStart()) {
            return false;
        }
        roomModel.setCreatedAt((int) (System.currentTimeMillis() / 1000));
        this.save(roomModel);
        return true;
    }

    @Override
    public boolean deleteRoom(Integer id) {
        RoomModel roomModel = this.getById(id);
        if (roomModel != null) {
            roomModel.setIsDeleted(1);
            roomModel.setDeletedAt((int) (System.currentTimeMillis() / 1000));
            if (redisUtil.hasKey("Room:" + roomModel.getId())){
                redisUtil.del("Room:" + roomModel.getId());
            }
            this.updateById(roomModel);
            return true;
        }
        return false;
    }

    @Override
    public PageResponseDto listRoom(Integer page, Integer pageSize, Integer buildingId, Integer floor, String roomName) {
        PageHelper.startPage(page, pageSize);
        List<RoomModel> roomModels = roomMapper.listRoom(buildingId, floor, roomName);
        PageInfo pageInfo = new PageInfo(roomModels);
        List<RoomListDto> roomListDtos = modelMapper.map(roomModels, new TypeToken<List<RoomModel>>() {
        }.getType());

        PageResponseDto pageResponseDto = new PageResponseDto();
        pageResponseDto.setList(roomListDtos);
        pageResponseDto.setTotalCount((int) pageInfo.getTotal());
        pageResponseDto.setPageCount(pageInfo.getPages());
        pageResponseDto.setPerPage(pageInfo.getPageSize());
        pageResponseDto.setCurrentPage(pageInfo.getPageNum());
        return pageResponseDto;
    }

    @Override
    public boolean roomDecision(Integer id) {
        RoomModel roomModel = this.getById(id);
        return roomModel != null && roomModel.getIsDeleted() != 1;
    }


    @Override
    public DetailRoomDto detailRoomDto(Integer id) {
        if (redisUtil.hasKey("Room:" + id)){
            return modelMapper.map(redisUtil.get("Room:" + id), DetailRoomDto.class);
        }
        RoomModel roomModel = this.getById(id);
        DetailRoomDto detailRoomDto = new DetailRoomDto();
        detailRoomDto.setName(roomModel.getRoomName());
        detailRoomDto.setBuildingId(roomModel.getBuildingId());
        detailRoomDto.setFloor(roomModel.getFloor());
        detailRoomDto.setBuildingName(buildingService.getById(roomModel.getBuildingId()).getName());
        redisUtil.set("Room:"+roomModel.getId().toString(), detailRoomDto);
        return detailRoomDto;
    }

    @Override
    public boolean roomUpdate(RoomUpdateDto roomUpdateDto) {
        RoomModel roomModel = modelMapper.map(roomUpdateDto, RoomModel.class);
        Integer currentId = roomModel.getId();
        RoomModel roomModel1 = this.getById(currentId);
        if (roomModel1 != null && roomModel1.getIsDeleted() != 1) {
            roomModel.setUpdatedAt((int) (System.currentTimeMillis() / 1000));
            this.updateById(roomModel);

            if (redisUtil.hasKey("Room:" + roomModel.getId())){
                redisUtil.del("Room:" + roomModel.getId());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIn(Integer id){
        RoomModel roomModel = this.getById(id);
        if(roomModel!= null && roomModel.getIsDeleted()==0){
            roomModel.setStatus(1);
            if (redisUtil.hasKey("Room:" + roomModel.getId())){
                redisUtil.del("Room:" + roomModel.getId());
            }
            this.updateById(roomModel);
        }
        return true;
    }

    @Override
    public boolean clean(Integer id){
        RoomModel roomModel = this.getById(id);
        if(roomModel!= null && roomModel.getIsDeleted()==0){
            roomModel.setStatus(0);
            if (redisUtil.hasKey("Room:" + roomModel.getId())){
                redisUtil.del("Room:" + roomModel.getId());
            }
            this.updateById(roomModel);
        }
        return true;
    }

}
