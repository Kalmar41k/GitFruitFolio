package org.example.serverfruitfolio.Repository;

import org.example.serverfruitfolio.model.ProductGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGradeRepository extends JpaRepository<ProductGrade, Integer> {
}
