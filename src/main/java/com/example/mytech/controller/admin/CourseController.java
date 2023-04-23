package com.example.mytech.controller.admin;

import com.example.mytech.entity.Course;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.CategoryRep;
import com.example.mytech.model.request.CourseRep;
import com.example.mytech.model.request.ScheduleReq;
import com.example.mytech.model.request.UserRep;
import com.example.mytech.service.CategoryService;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.TeacherService;
import com.example.mytech.websocket.WebSocketHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TeacherService teacherService ;

    @Autowired
    private WebSocketHandler webSocketHandler;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/courses")
    public String getAdminCourse(Model model,
                                   @RequestParam(defaultValue = "", required = false) String id,
                                   @RequestParam(defaultValue = "", required = false) String name,
                                   @RequestParam(defaultValue = "1", required = false) Integer page) {
        // get list course
        Page<Course> courses = courseService.adminGetListCourses(id, name, page);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", courses.getPageable().getPageNumber() + 1);
        return "admin/course/list";
    }

    // get list category
    @ModelAttribute("categories")
    public List<CategoryRep> getCategories() {
        return categoryService.getListCategory().stream()
                .map(item -> {
                    CategoryRep rep = new CategoryRep();
                    modelMapper.map(item, rep);
                    return rep;
                }).collect(Collectors.toList());
    }

    @ModelAttribute("teachers")
    public List<UserRep> getTeachers() {
        return teacherService.getUserWithRoleTeacher().stream()
                .map(item -> {
                    UserRep rep = new UserRep();
                    modelMapper.map(item, rep);
                    return rep;
                }).collect(Collectors.toList());
    }

    // Go to more courses page
    @GetMapping("/courses/create")
    public String getCourseCreatePage(Model model, CourseRep rep ) {
        model.addAttribute("course", rep);
        return "admin/course/create";
    }

    // add course by Server Side
    @PostMapping("/courses/create")
    public ModelAndView createCourse(ModelMap model, @Valid @ModelAttribute(value = "course") CourseRep req, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/course/create");
        }
        Course course = courseService.createCourse(req);
        model.addAttribute("course", course);
        webSocketHandler.notifyCourseChange();
        return new ModelAndView("redirect:/admin/courses", model);
    }

    // Go to page details
    @GetMapping("/courses/{id}")
    public String getDetailProducPage(Model model, @PathVariable("id") String id) {
        try {
            // Get info
            Course course = courseService.getCourseById(id);
            model.addAttribute("course", course);
            return "admin/course/details";
        } catch (NotFoundException ex) {
            return "admin/error/err";
        }
    }

    //update code thymleaf
    @PostMapping("/courses/update/{id}")
    public ModelAndView updateCourse(@Valid @PathVariable String id, @ModelAttribute("course") CourseRep req, ModelMap model, BindingResult result) {
        if (result.hasErrors()) {
            req.setId(id);
            return new ModelAndView("redirect:/admin/course/details", model);
        }
        Course course = courseService.updateCourse(id, req);
        model.addAttribute("course", course);
        webSocketHandler.notifyCourseChange();

        return new ModelAndView("redirect:/admin/courses", model);
    }

    // delete product by id follow server side
    @GetMapping("/courses/delete/{id}")
    public ModelAndView deleteCourse(ModelMap model, @PathVariable("id") String id) {
        Optional<Course> rs = Optional.ofNullable(courseService.getCourseById(id));
        if (rs.isPresent()) {
            courseService.deleteCourse(rs.get());
            model.addAttribute("message", "Xóa thành công");
            webSocketHandler.notifyCourseChange();

            return new ModelAndView("redirect:/admin/courses", model);
        } else {
            model.addAttribute("message", "Xóa thất bại");
            return new ModelAndView("redirect:/admin/courses", model);
        }
    }

}
