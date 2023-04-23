package com.example.mytech.repository;

import com.example.mytech.entity.Category;
import com.example.mytech.model.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String> {

    boolean existsByName (String name ) ;

    Category findByName(String name) ;

    Category getCategoryById (String id);

    @Query(nativeQuery = true ,name = "getListCategoryAndCourseCount")
    List<CategoryDTO> getListCategoryAndCourseCount();

    Page<Category> findByNameContaining (String name, Pageable pageable);
}
