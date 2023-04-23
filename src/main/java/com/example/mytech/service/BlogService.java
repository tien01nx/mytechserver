package com.example.mytech.service;

import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.BlogReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BlogService {

    Page<Blog> getAdminBlogPage (String title , Integer page) ;

    public Blog createBlog (User user,BlogReq req) ;
}
