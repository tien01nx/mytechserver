package com.example.mytech.controller.admin;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Schedule;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.dto.ScheduleDTO;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.repository.ScheduleRepository;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CourseService courseService ;

    @Autowired
    private ScheduleRepository scheduleRepository ;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("admin/schedules")
    public String getAdminSchedule(Model model,
                                   @RequestParam(defaultValue = "1", required = false) Integer page) {

        Page<ScheduleDTO> schedules = scheduleService.findScheduleByCourseName(page);
        String prevCourseName = null;
        model.addAttribute("currentCourseName", prevCourseName);

        model.addAttribute("schedules", schedules.getContent());
        model.addAttribute("totalPages", schedules.getTotalPages());
        model.addAttribute("currentPage", schedules.getPageable().getPageNumber() + 1);
        return "admin/schedule/list";
    }

    @ModelAttribute("courses")
    public List<CourseRep> getCourses() {
        return courseService.getListCourse().stream()
                .map(item -> {
                    CourseRep rep = new CourseRep();
                    modelMapper.map(item, rep);
                    return rep;
                }).collect(Collectors.toList());
    }

    @GetMapping("/courses/{id}/schedules")
    public ModelAndView getListScheduleByCourses(@PathVariable("id") String courseId, ModelMap model) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            throw new NotFoundException("Khóa học không tồn tại");
        }
        List<Schedule> schedules =  scheduleRepository.findByCourse(course);
        model.addAttribute("schedules", schedules);
        return new ModelAndView("admin/schedule/detail",  model);
    }
}
