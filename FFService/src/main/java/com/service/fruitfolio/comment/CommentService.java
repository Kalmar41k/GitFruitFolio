package com.service.fruitfolio.comment;

import com.service.fruitfolio.sort.ProductSort;
import com.service.fruitfolio.sort.ProductSortRepository;
import com.service.fruitfolio.sort.ProductSortService;
import com.service.fruitfolio.user.User;
import com.service.fruitfolio.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductSortRepository productSortRepository;
    private final UserRepository userRepository;

    public Comment create(CommentRequest commentRequest) {
        Comment comment = new Comment();
        Optional<User> user = userRepository.findById(commentRequest.getUserId());
        if (user.isEmpty()) {
            return null;
        }
        Optional<ProductSort> productSort = productSortRepository.findById(commentRequest.getProductSortId());
        if (productSort.isEmpty()) {
            return null;
        }
        comment.setUser(user.orElse(null));
        comment.setProductSort(productSort.orElse(null));
        comment.setText(commentRequest.getText());
        return commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getSortComments(ProductSort productSort) {
        return commentRepository.getByProductSort(productSort);
    }

    public String deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
        return "OK";
    }
}
