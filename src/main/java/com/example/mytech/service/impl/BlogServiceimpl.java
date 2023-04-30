package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Blog;
import com.example.mytech.entity.User;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.dto.BlogDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BlogServiceimpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository ;


    @Override
    public List<Blog> getListBlog() {
        return blogRepository.findAll();
    }

    @Override
    public List<BlogDTO> getBlogByUserId(String userId) {
        List<Blog> blogs = blogRepository.findByCreatedBy_Id(userId);
        List<BlogDTO> blogDTOs = new ArrayList<>();

        for (Blog blog : blogs) {
            BlogDTO blogDTO = new BlogDTO(blog);
            blogDTOs.add(blogDTO);
        }
        return blogDTOs;
    }

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
            if(req.getImage().isEmpty()) {
                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết");
            }
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

    @Override
    public Blog getBlogById(String id) {
        Optional<Blog> rs = blogRepository.findById(id) ;
        if(!rs.isPresent()) {
            throw new NotFoundException("Không tìm thấy bài viết");
        }
        return rs.get();
    }

    @Override
    public void updateBlog(BlogReq req, User user, String id) {
        Blog blog;
        Optional<Blog> rs = blogRepository.findById(id);
        blog = rs.get();
        if (!rs.isPresent()) {
            throw new NotFoundException("Blog do not exits");
        }
        blog.setTitle(req.getTitle());
        blog.setContent(req.getContent());
        Slugify slg = new Slugify();
        blog.setSlug(slg.slugify(req.getTitle()));
        blog.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        blog.setModifiedBy(user);
        if (req.getStatus() == Contant.PUBLIC_BLOG) {
            // Public post
            if (req.getDescription().isEmpty()) {
                throw new BadRequestException("Để công khai bài viết vui lòng nhập mô tả");
            }
            if (req.getImage().isEmpty()) {
                throw new BadRequestException("Vui lòng chọn ảnh cho bài viết trước khi công khai");
            }
            if (blog.getPublishedAt() == null) {
                blog.setPublishedAt(new Timestamp(System.currentTimeMillis()));
            }
        } else {
            if (req.getStatus() != Contant.DRAFT_BLOG) {
                throw new BadRequestException("Trạng thái bài viết không hợp lệ");
            }
        }
        blog.setDescription(req.getDescription());
        blog.setImage(req.getImage());
        blog.setStatus(req.getStatus());
        try {
            blogRepository.save(blog);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật bài viết");
        }
    }

    @Override
    public void deletePost(String id) {
        Optional<Blog> rs = blogRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }
        try {
            blogRepository.delete(rs.get());
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa bài viết");
        }
    }

    @Override
    public List<BlogDTO> getAllListBlog() {
        List<Blog> blogs = blogRepository.findAll();
        List<BlogDTO> blogDTOS = new ArrayList<>() ;

        for (Blog blog : blogs) {
            BlogDTO blogDTO = new BlogDTO(blog);
            blogDTOS.add(blogDTO);
        }
        return blogDTOS;
    }
}
