package com.project.wekiosk.category.Controller;

import com.project.wekiosk.category.dto.CategoryDTO;
import com.project.wekiosk.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/register")
    public ResponseEntity<Void> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.registerCategory(categoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list/{sno}")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@PathVariable("sno") Long sno) {
        List<CategoryDTO> categories = categoryService.getAllCategories(sno);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


//    @GetMapping("/{sno}/store")
//    public List<CategoryDTO> getListBySno(@PathVariable("sno") Long sno) {
//
//        return categoryService.getListBySno(sno);
//    }

    @GetMapping("/{cateno}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("cateno") Long cateno) {
        CategoryDTO category = categoryService.getCategoryById(cateno);
        return new ResponseEntity<>(category, HttpStatus.OK);

    }

    @PutMapping("/{cateno}/modify")
    public ResponseEntity<String> updateCategory(@PathVariable Long cateno, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(cateno, categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{cateno}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable Long cateno) {
        boolean hasProducts = categoryService.hasProductsInCategory(cateno);

        if (hasProducts) {
            return ResponseEntity.badRequest().body("해당 카테고리에 연결된 상품이 있어 삭제할 수 없습니다.");
        }

        categoryService.deleteCategory(cateno);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
