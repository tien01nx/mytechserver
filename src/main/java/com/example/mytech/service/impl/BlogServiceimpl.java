package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.model.request.BlogReq;
import com.example.mytech.repository.BlogRepository;
import com.example.mytech.security.CustomUserDetails;
import com.example.mytech.service.BlogService;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class BlogServiceimpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository ;

    @Override
    public Page<Blog> getAdminBlogPage(String title, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_POST, Sort.by("createdAt").descending());
        return blogRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public Blog createBlog(User user, BlogReq req) {

        Blog blog = new Blog() ;

        blog.setTitle(req.getTitle());
        blog.setContent(req.getContent());
        Slugify slg = new Slugify() ;
        blog.setSlug(slg.slugify(req.getTitle()));
        blog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        blog.setCreatedBy(user);

        if(req.getStatus() == Contant.PUBLIC_BLOG) {
            if (req.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai bài viết vui lòng nhập mô tả") ;
            }
//            if(req.getImage().isEmpty()) {
//                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết");
//            }
            blog.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        else {
            if (req.getStatus() != Contant.DRAFT_BLOG) {
                throw new BadRequestException("Trang thái bài viết không hợp lệ") ;
            }
        }
        blog.setDescription(req.getDescription());
        blog.setImage(req.getImage());
        blog.setStatus(req.getStatus());

        blogRepository.save(blog) ;
        return blog;
    }
}
