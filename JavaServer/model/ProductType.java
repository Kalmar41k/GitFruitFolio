package org.example.serverfruitfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_types", schema = "public", catalog = "FruitFolioDB")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "product_type", nullable = false, length = -1)
    private String productType;

}
