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
                                                     @Param("dateTime") Integer dateTime,
                                                        @Param("year") Integer year,
                                                @Param("month") Integer month);

    Integer appointmentsWithoutSelfTime(@Param("startTime")Integer startTime, @Param("endTime") Integer endTime,
                                        @Param("roomId") Integer roomId,
                                        @Param("dateTime") Integer dateTime,
                                        @Param("year") Integer year,
                                        @Param("month") Integer month,
                                        @Param("id") Integer id);

    Integer isValidUser (@Param("id") Integer id);

    List<AppointmentModel> listAppointment(@Param("roomId")Integer roomId,
                            @Param("startTime")Integer startTime,
                            @Param("endTime")Integer endTime,
                            @Param("userId")String userId,
                            @Param("month")Integer month,
                            @Param("year")Integer year,
                            @Param("date")Integer date);
}
