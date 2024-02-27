package com.service.fruitfolio.sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSortRepository extends JpaRepository<ProductSort, Integer> {

    ProductSort findByDescription(String desctiption);

    List<ProductSort> findByEnumClass(ProductClassEnum enumClass);

}
