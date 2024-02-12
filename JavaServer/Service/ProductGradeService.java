package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.model.ProductGrade;

import java.util.List;

public interface ProductGradeService {

    List<ProductGrade> getAllProductGrades();
    ProductGrade getProductGradeById(int id);
    ProductGrade createProductGrade(ProductGrade productGrade);
    void deleteProductGradeById(int id);
}
