package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.model.ProductComment;

import java.util.List;

public interface ProductCommentService {

    List<ProductComment> getAllProductComments();
    ProductComment getProductCommentById(int id);
    ProductComment createProductComment(ProductComment productComment);
    void deleteProductCommentById(int id);
}
