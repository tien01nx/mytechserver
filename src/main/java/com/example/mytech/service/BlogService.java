package com.example.mytech.service;

import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import com.example.mytech.model.dto.BlogDTO;
import com.example.mytech.model.request.BlogReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {

    public List<Blog> getListBlog () ;

    public List<BlogDTO> getBlogByUserId (String userId) ;

    Page<Blog> getAdminBlogPage (String title , Integer page) ;

    public Blog createBlog (User user,BlogReq req) ;
    public Blog getBlogById (String id) ;
    public void updateBlog(BlogReq req, User user, String id);
    public void deletePost(String id);

    public List<BlogDTO> getAllListBlog () ;
}