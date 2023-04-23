package com.example.mytech.service;

import com.example.mytech.entity.Schedule;
import com.example.mytech.model.dto.ScheduleDTO;
import com.example.mytech.model.request.ScheduleReq;
import com.example.mytech.model.request.UpdateScheduleReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface ScheduleService {

    public List<Schedule> getListSchedule () ;

    Page<ScheduleDTO> findScheduleByCourseName (Integer page);

    public Schedule createSchedule ( ScheduleReq req) throws ParseException;

    Schedule updateSchedule(String id, UpdateScheduleReq req) throws Exception;

    public void deleteSchedule (Schedule schedule) ;

    public Schedule getScheduleById (String id) ;

    public List<Schedule> getCourseSchedules (String courseId ) ;


}
