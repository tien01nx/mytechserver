package com.example.mytech.service.impl;

import com.example.mytech.config.Contant;
import com.example.mytech.entity.Category;
import com.example.mytech.exception.BadRequestException;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.dto.CategoryDTO;
import com.example.mytech.model.request.CategoryRep;
import com.example.mytech.repository.CategoryRepository;
import com.example.mytech.repository.CourseRepository;
import com.example.mytech.service.CategoryService;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository ;

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRep req) {
        Category category = new Category();

        category.setName(req.getName());

        // check trùng name
        if (categoryRepository.existsByName(req.getName())) {
            throw new BadRequestException("Tên danh mục đã tồn tại");
        }

        Slugify slg = new Slugify();
        category.setSlug(slg.slugify(req.getName()));

         category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        category.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        try {
            categoryRepository.save(category);
            return category;

        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi thêm category");
        }
    }

    @Override
    public List<CategoryDTO> getListCategoryAndCourseCount() {
        return categoryRepository.getListCategoryAndCourseCount();
    }

    @Override
    public void updateCategory(String id, CategoryRep req) {

        // Check category exist
        Optional<Category> rs = categoryRepository.findById(id);

        // if not found id =>
        if (rs.isEmpty()) {
            throw new NotFoundException("Category không tồn tại");
        }

        Category category = rs.get();

        if (!req.getName().equalsIgnoreCase(rs.get().getName())) {
            if (categoryRepository.existsByName(req.getName())) {
                throw new BadRequestException("Tên danh mục đã tồn tại");
            }
        }
        category.setName(req.getName());

        Slugify slg = new Slugify();
        category.setSlug(slg.slugify(req.getName()));
        category.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        try {
            categoryRepository.save(category);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi chỉnh sửa category");
        }
    }

    @Override
    public void deleteCategory(String id) {
        Optional<Category> opt = categoryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new NotFoundException("Category không tồn tại");
        }
        Category category = opt.get();
        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new InternalServerException("Lỗi khi xóa category");
        }
    }

    @Override
    public Category getCategoryById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new NotFoundException("Không tìm thấy danh mục");
        }
        return category.get();
    }

    @Override
    public Page<Category> adminGetListCategory(String name, int page) {
        page--;
        if (page <= 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, Contant.LIMIT_CATEGORY , Sort.by("createdAt").descending());
        return categoryRepository.findByNameContaining( name,  pageable);
    }
}
