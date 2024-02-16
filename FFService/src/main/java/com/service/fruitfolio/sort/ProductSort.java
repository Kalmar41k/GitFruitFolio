package com.service.fruitfolio.sort;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductSort {

    @Id
    @GeneratedValue
    public Integer id;
    public ProductClassEnum enumClass;
    public String type;
    public String sort;
}
