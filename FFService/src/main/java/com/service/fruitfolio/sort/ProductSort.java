package com.service.fruitfolio.sort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.fruitfolio.comment.Comment;
import com.service.fruitfolio.grade.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductSort {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private ProductClassEnum enumClass;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String sort;

    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "productSort")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "productSort")
    private List<Grade> grades;

    @Column(nullable = false)
    private Double meanGrade;

}
