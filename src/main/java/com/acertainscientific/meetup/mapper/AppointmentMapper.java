package com.acertainscientific.meetup.mapper;

import com.acertainscientific.meetup.model.AppointmentModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Component
public interface AppointmentMapper extends BaseMapper<AppointmentModel> {
    Integer appointmentsGivenTime(@Param("startTime")Integer startTime, @Param("endTime") Integer endTime,
                                                     @Param("roomId") Integer roomId,
                                                     @Param("dateTime") Integer dateTime);
}
