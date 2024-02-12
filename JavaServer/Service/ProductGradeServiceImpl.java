package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.Repository.ProductGradeRepository;
import org.example.serverfruitfolio.model.ProductGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductGradeServiceImpl implements ProductGradeService{

    private final ProductGradeRepository productGradeRepository;

    @Autowired
    public ProductGradeServiceImpl(ProductGradeRepository productGradeRepository) {
        this.productGradeRepository = productGradeRepository;
    }

    @Override
    public List<ProductGrade> getAllProductGrades() {
        return productGradeRepository.findAll();
    }

    @Override
    public ProductGrade getProductGradeById(int id) {
        Optional<ProductGrade> optionalProductGrade = productGradeRepository.findById(id);
        if (optionalProductGrade.isEmpty()) {
            throw new IllegalStateException("Product Grade with id " + id + " does not exist");
        }
        return optionalProductGrade.get();
    }

    @Override
    public ProductGrade createProductGrade(ProductGrade productGrade) {
        return productGradeRepository.save(productGrade);
    }

    @Override
    public void deleteProductGradeById(int id) {
        productGradeRepository.deleteById(id);
    }
}
