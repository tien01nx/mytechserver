package com.example.mytech.controller.admin;


import com.example.mytech.entity.User;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.ChangeStatusReq;
import com.example.mytech.model.request.UserRep;
import com.example.mytech.service.UserCourseService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCourseService userCourseService;

    @GetMapping("/admin/users")
    public String getListUserPage(Model model,
                                   @RequestParam(defaultValue = "", required = false) String id,
                                   @RequestParam(defaultValue = "", required = false) String nameStudent,
                                   @RequestParam(defaultValue = "1", required = false) Integer page) {
        // get list user
        Page<User> users = userService.findUserByNameOrId(id, nameStudent, page);
        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", users.getPageable().getPageNumber() + 1);
        return "admin/user/list";
    }

    // Go to more courses page
    @GetMapping("/admin/users/create")
    public String getUserCreatePage(Model model, UserRep rep) {
        model.addAttribute("user", rep);
        return "admin/user/create";
    }

    // add user by Server Side
    @PostMapping("/admin/users/create")
    public ModelAndView createUser(ModelMap model, @Valid @ModelAttribute(value = "user") UserRep req, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/user/create");
        }
        User user = userService.createUser(req);
        model.addAttribute("user", user);
        return new ModelAndView("redirect:/admin/users", model);
    }

    // Go to page details
    @GetMapping("/admin/users/{id}")
    public String getDetailUserPage(Model model, @PathVariable("id") String id) {
        try {
            // Get info
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "admin/user/detail";
        } catch (NotFoundException ex) {
            return "admin/error/err";
        }
    }
    //update user thymleaf
    @PostMapping ("/admin/users/update/{id}")
    public ModelAndView updateUser(@Valid @PathVariable String id, @ModelAttribute("user") UserRep req, ModelMap model, BindingResult result) {
        if (result.hasErrors()) {
            req.setId(id);
            return new ModelAndView("redirect:/admin/user/detail", model);
        }
        User user = userService.updateUser(id, req);
        model.addAttribute("user", user);
        return new ModelAndView("redirect:/admin/users", model);
    }

    // delete user by id follow server side
    @GetMapping("/admin/users/delete/{id}")
    public ModelAndView deleteCourse (ModelMap model, @PathVariable("id") String id) {
        Optional<User> rs = Optional.ofNullable(userService.getUserById(id));
        if(rs.isPresent()){
            userService.deleteUser(rs.get());
            model.addAttribute("message", "Xóa thành công");
            return new ModelAndView("redirect:/admin/users", model);
        }
        else {
            model.addAttribute("message", "Xóa thất bại");
            return new ModelAndView("redirect:/admin/users", model);
        }
    }

    // học viên đăng ký khóa học , được admin phê duyệt
    @GetMapping("/admin/user-course/{id}")
    public void updateCourseStatus(@PathVariable String id , ChangeStatusReq req) {
        userCourseService.updateStatus(id , req);
    }
}

