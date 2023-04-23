package com.example.mytech.controller.admin;

import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.BlogReq;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.service.BlogService;
import com.example.mytech.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService ;

    @Autowired
    private ImageService imageService ;

    @GetMapping("/blogs")
    public String getAdminBlog(Model model,
                               @RequestParam(defaultValue = "", required = false) String title,
                               @RequestParam(defaultValue = "1", required = false) int page) {
        // get list blogs
        Page<Blog> blogs = blogService.getAdminBlogPage(title, page);
        model.addAttribute("blogs", blogs.getContent());
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("currentPage", blogs.getPageable().getPageNumber() + 1);
        return "admin/blog/list";
    }

    @GetMapping("/blogs/create")
    public String getPostCreatePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            List<String> images = imageService.getListImageOfUser(user.getId());
            model.addAttribute("images", images);
        }
        return "admin/blog/create";
    }



}
