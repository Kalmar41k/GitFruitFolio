package org.example.serverfruitfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_comments", schema = "public", catalog = "FruitFolioDB")
public class ProductComment {

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
    @Column(name = "comment", nullable = false, length = -1)
    private String comment;

    @Basic
    @Column(name = "date_time", nullable = false)
    private OffsetDateTime dateTime;

}
