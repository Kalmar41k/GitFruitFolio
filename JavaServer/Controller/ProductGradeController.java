package org.example.serverfruitfolio.Controller;

import org.example.serverfruitfolio.Service.ProductGradeService;
import org.example.serverfruitfolio.model.ProductGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productGrade")
public class ProductGradeController {

    private final ProductGradeService productGradeService;

    @Autowired
    public ProductGradeController(ProductGradeService productGradeService) {
        this.productGradeService = productGradeService;
    }

    @GetMapping("/all")
    public List<ProductGrade> getAllProductGrades() {
        return productGradeService.getAllProductGrades();
    }

    @GetMapping("/byId/{id}")
    public ProductGrade getProductGradeById(@PathVariable("id") Integer id) {
        return productGradeService.getProductGradeById(id);
    }

    @PostMapping("/create")
    public ProductGrade createProductGrade(@RequestBody ProductGrade productGrade) {
        return productGradeService.createProductGrade(productGrade);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductGradeById(@PathVariable("id") Integer id) {
        productGradeService.deleteProductGradeById(id);
    }

}
