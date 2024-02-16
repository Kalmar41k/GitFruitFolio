package com.service.fruitfolio.sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSortRepository extends JpaRepository<ProductSort, Integer> {

}
