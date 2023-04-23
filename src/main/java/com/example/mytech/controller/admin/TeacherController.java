package com.example.mytech.controller.admin;

import com.example.mytech.entity.User;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.UserRep;
import com.example.mytech.service.TeacherService;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TeacherController {


    @Autowired
    private TeacherService teacherService ;

    @GetMapping("/admin/teachers")
    public String getListTeacherPage(Model model,
                                   @RequestParam(defaultValue = "", required = false) String id,
                                   @RequestParam(defaultValue = "", required = false) String nameTeacher,
                                   @RequestParam(defaultValue = "1", required = false) Integer page) {
        // get list teacher
        Page<User> teachers = teacherService.findTeacherByNameOrId(id, nameTeacher, page);
        model.addAttribute("teachers", teachers.getContent());
        model.addAttribute("totalPages", teachers.getTotalPages());
        model.addAttribute("currentPage", teachers.getPageable().getPageNumber() + 1);
        return "admin/teacher/list";
    }

    // Go to more courses page
    @GetMapping("/admin/teachers/create")
    public String getTeacherCreatePage(Model model, UserRep rep) {
        model.addAttribute("teacher", rep);
        return "admin/teacher/create";
    }

    // add user by Server Side
    @PostMapping("/admin/teachers/create")
    public ModelAndView createTeacher(ModelMap model, @Valid @ModelAttribute(value = "teacher") UserRep req, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/teacher/create");
        }
        User teacher = teacherService.createTeacher(req);
        model.addAttribute("teacher", teacher);
        return new ModelAndView("redirect:/admin/teachers", model);
    }

    // Go to page details
    @GetMapping("/admin/teachers/{id}")
    public String getDetailTeacherPage(Model model, @PathVariable("id") String id) {
        try {
            // Get info
            User teacher = teacherService.getTeacherById(id);
            model.addAttribute("teacher", teacher);
            return "admin/teacher/detail";
        } catch (NotFoundException ex) {
            return "admin/error/err";
        }
    }
    //update code thymleaf
    @PostMapping ("/admin/teachers/update/{id}")
    public ModelAndView updateUser(@Valid @PathVariable String id, @ModelAttribute("teacher") UserRep req, ModelMap model, BindingResult result) {
        if (result.hasErrors()) {
            req.setId(id);
            return new ModelAndView("redirect:/admin/teacher/detail", model);
        }
        User teacher = teacherService.updateTeacher(id, req);
        model.addAttribute("teacher", teacher);
        return new ModelAndView("redirect:/admin/teachers", model);
    }

    // delete user by id follow server side
    @GetMapping("/admin/teachers/delete/{id}")
    public ModelAndView deleteCourse (ModelMap model, @PathVariable("id") String id) {
        Optional<User> rs = Optional.ofNullable(teacherService.getTeacherById(id));
        if(rs.isPresent()){
            teacherService.deleteTeacher(rs.get());
            model.addAttribute("message", "Xóa thành công");
            return new ModelAndView("redirect:/admin/teachers", model);
        }
        else {
            model.addAttribute("message", "Xóa thất bại");
            return new ModelAndView("redirect:/admin/teachers", model);
        }
    }
}
