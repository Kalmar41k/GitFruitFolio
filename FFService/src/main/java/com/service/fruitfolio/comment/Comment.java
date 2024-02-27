package com.service.fruitfolio.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.fruitfolio.sort.ProductSort;
import com.service.fruitfolio.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Comment_Sort_ID")
    private ProductSort productSort;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_user_id")
    private User user;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

}
