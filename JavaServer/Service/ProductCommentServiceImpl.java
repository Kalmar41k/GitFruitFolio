package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.Repository.ProductCommentRepository;
import org.example.serverfruitfolio.model.ProductComment;
import org.example.serverfruitfolio.model.ProductGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCommentServiceImpl implements ProductCommentService{

    private final ProductCommentRepository productCommentRepository;

    @Autowired
    public ProductCommentServiceImpl(ProductCommentRepository productCommentRepository) {
        this.productCommentRepository = productCommentRepository;
    }

    @Override
    public List<ProductComment> getAllProductComments() {
        return productCommentRepository.findAll();
    }

    @Override
    public ProductComment getProductCommentById(int id) {
        Optional<ProductComment> optionalProductComment = productCommentRepository.findById(id);
        if (optionalProductComment.isEmpty()) {
            throw new IllegalStateException("Product Comment with id " + id + " does not exist");
        }
        return optionalProductComment.get();
    }

    @Override
    public ProductComment createProductComment(ProductComment productComment) {
        return productCommentRepository.save(productComment);
    }

    @Override
    public void deleteProductCommentById(int id) {
        productCommentRepository.deleteById(id);
    }
}
