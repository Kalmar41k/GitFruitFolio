package com.service.fruitfolio.comment;

import com.service.fruitfolio.sort.ProductSort;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue
    public Integer id;

    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "Comment_Sort_ID")
    private ProductSort productSort;

    public String text;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createDate;


}
