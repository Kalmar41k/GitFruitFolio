package com.service.fruitfolio.grade;

import com.service.fruitfolio.sort.ProductSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    Grade findByUserId(Integer id);

    List<Grade> getByProductSort(ProductSort productSort);
}
