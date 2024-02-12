package org.example.serverfruitfolio.Repository;

import org.example.serverfruitfolio.model.ProductSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSortRepository extends JpaRepository<ProductSort, Integer> {
}
