package com.example.mytech.controller.api;

import com.example.mytech.entity.Category;
import com.example.mytech.model.request.CategoryRep;
import com.example.mytech.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService ;

    @GetMapping("/admin/category/list")
    public ResponseEntity<Object> getListCategory () {
        List<Category> categories = categoryService.getListCategory();
        return ResponseEntity.ok(categories);
    }

//    @GetMapping("/admin/categories")
//    public String getCategoryManagePage(Model model) {
//        List<CategoryDTO> categories = categoryService.getListCategoryAndCourseCount();
//        model.addAttribute("categories", categories);
//        return "admin/category/list";
//    }

    // create category
    @PostMapping("/admin/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRep req) {
        Category category = categoryService.createCategory(req);
        return ResponseEntity.ok(category);
    }

    // sửa danh mục
    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryRep req, @PathVariable String id) {
        categoryService.updateCategory(id, req);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    // xóa danh mục
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Xóa thành công");
    }
}
