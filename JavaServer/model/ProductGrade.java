package org.example.serverfruitfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_grades", schema = "public", catalog = "FruitFolioDB")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductGrade {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic
    @Column(name = "product_sort_id", nullable = false)
    private int productSortId;

    @Basic
    @Column(name = "grade", nullable = false)
    private int grade;

}

