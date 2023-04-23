package com.example.mytech.repository;

import com.example.mytech.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image , String> {

    void findByLink (String link) ;

//    @Query(nativeQuery = true, value = "SELECT link FROM image WHERE  created_by = ?1")
//    Page<String> getListImageOfUser(String userId , Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT link FROM image WHERE  created_by = ?1")
    List<String> getListImageOfUser (String userId);
}
