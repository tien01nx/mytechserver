package com.example.mytech.controller.api;

import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.BlogReq;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

@Controller
public class BlogApiController {

    @Autowired
    private BlogService blogService ;

    @PostMapping("/api/admin/blogs")
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogReq req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            Blog blog = blogService.createBlog(user,req);
            return ResponseEntity.ok(blog.getId()) ;
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); // Phản hồi 401 Unauthorized
        }
    }
}
