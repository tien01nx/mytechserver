package com.example.mytech.controller.admin;

import com.example.mytech.entity.Category;
import com.example.mytech.model.dto.CategoryDTO;
import com.example.mytech.model.request.CategoryRep;
import com.example.mytech.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Hiện thị danh mục tìm kiếm theo tên và phân trang
    @GetMapping("/admin/categories")
    public String homePage(Model model,
                           @RequestParam(defaultValue = "", required = false) String name,
                           @RequestParam(defaultValue = "1", required = false) Integer page) {
        Page<Category> category = categoryService.adminGetListCategory(name, page);
        List<Category> categories = category.getContent();
        model.addAttribute("categories", categories);
        model.addAttribute("totalPages", category.getTotalPages());
        model.addAttribute("currentPage", category.getPageable().getPageNumber() + 1);

        return "admin/category/list";
    }

    @GetMapping("/api/admin/categories")
    public ResponseEntity<Object> adminGetListCategories(@RequestParam(defaultValue = "", required = false) String name,
                                                         @RequestParam(defaultValue = "0", required = false) Integer page) {
        Page<Category> categories = categoryService.adminGetListCategory(name, page);
        return ResponseEntity.ok(categories);
    }

//    @GetMapping("/admin/categories")
//    public String getCategoryManagePage(Model model) {
//        List<CategoryDTO> categories = categoryService.getListCategoryAndCourseCount();
//        model.addAttribute("categories", categories);
//        return "admin/category/list";
//    }

}
