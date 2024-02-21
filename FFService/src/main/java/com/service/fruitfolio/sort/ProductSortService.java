package com.service.fruitfolio.sort;

import com.service.fruitfolio.comment.Comment;
import com.service.fruitfolio.comment.CommentService;
import com.service.fruitfolio.user.User;
import com.service.fruitfolio.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSortService {

    private final ProductSortRepository productSortRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;

    public ProductSort create(ProductSort productSort) {
        return productSortRepository.save(productSort);
    }

    public List<ProductSort> findAll() {
        return productSortRepository.findAll();
    }

    public List<SortCommentsResponse> getSortCommentsById(Integer id) {
        Optional<ProductSort> productSort = productSortRepository.findById(id);
        if (productSort.isEmpty()) {
            throw new IllegalStateException("Product Sort is not found!");
        }
        List<Comment> comments = commentService.getSortComments(productSort.orElse(null));
        List<SortCommentsResponse> sortComments = new ArrayList<>();

        for (Comment comment : comments) {
            Optional<User> user = Optional.ofNullable(comment.getUser());
            user = userRepository.findById(user.get().getId());
            SortCommentsResponse sortCommentsResponse = new SortCommentsResponse();
            sortCommentsResponse.setId((comment.getId()));
            sortCommentsResponse.setFirstname(user.get().getFirstname());
            sortCommentsResponse.setLastname(user.get().getLastname());
            sortCommentsResponse.setText(comment.getText());
            sortCommentsResponse.setCreateDate(comment.getCreateDate());
            sortComments.add(sortCommentsResponse);
        }
        return sortComments;
    }

    public ProductSort findById(Integer id) {
        Optional<ProductSort> optionalProductSort = productSortRepository.findById(id);
        if (optionalProductSort.isEmpty()) {
            throw new IllegalStateException("Product Sort is not found!");
        }
        return optionalProductSort.get();
    }
}
