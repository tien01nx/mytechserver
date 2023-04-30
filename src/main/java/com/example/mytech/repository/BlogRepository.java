package com.example.mytech.repository;

import com.example.mytech.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

    Page<Blog> findByTitleContaining (String title , Pageable pageable) ;

    List<Blog> findByCreatedBy_Id(String userId);

}