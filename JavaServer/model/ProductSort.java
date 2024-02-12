package org.example.serverfruitfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_sorts", schema = "public", catalog = "FruitFolioDB")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSort {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "product_class", nullable = true)
    private ProductClassEnum productClass;

    @Basic
    @Column(name = "product_type_id", nullable = false)
    private int productTypeId;

    @Basic
    @Column(name = "product_sort", nullable = false, length = -1)
    private String productSort;

    @Basic
    @Column(name = "num_of_comments", nullable = false)
    private int numOfComments;

    @Basic
    @Column(name = "num_of_grades", nullable = false)
    private int numOfGrades;

    @Basic
    @Column(name = "mean_grade", nullable = false, precision = 0)
    private double meanGrade;

}

