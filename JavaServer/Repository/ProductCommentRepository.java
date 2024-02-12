package org.example.serverfruitfolio.Repository;

import org.example.serverfruitfolio.model.ProductComment;
import org.example.serverfruitfolio.model.ProductGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {
}
