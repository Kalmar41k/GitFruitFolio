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
        Optional<ProductSort> productSortOptional = productSortRepository.findById(id);
        if (productSortOptional.isEmpty()) {
            throw new IllegalStateException("Product Sort is not found!");
        }
        ProductSort productSort = productSortOptional.get();

        List<Comment> comments = commentService.getSortComments(productSort);
        List<SortCommentsResponse> sortComments = new ArrayList<>();

        for (Comment comment : comments) {
            User user = comment.getUser();
            Optional<User> userOptional = userRepository.findById(user.getId());
            if (userOptional.isPresent()) {
                user = userOptional.get();
                SortCommentsResponse sortCommentsResponse = new SortCommentsResponse();
                sortCommentsResponse.setId(comment.getId());
                sortCommentsResponse.setFirstname(user.getFirstname());
                sortCommentsResponse.setLastname(user.getLastname());
                sortCommentsResponse.setText(comment.getText());
                sortCommentsResponse.setCreateDate(comment.getCreateDate());
                sortComments.add(sortCommentsResponse);
            }
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

    public ProductSort findByDesctiption(ProductDescriptionRequest descriptionRequest) {
        Optional<ProductSort> productSort = Optional.ofNullable(productSortRepository
                .findByDescription(descriptionRequest.getDescription()));
        if (productSort.isEmpty()) {
            throw new IllegalStateException("Product Sort is not found!");
        }
        return productSort.get();
    }

    public List<ProductSort> findByProductClass(String productClass) {
        return productSortRepository.findByEnumClass(ProductClassEnum.valueOf(productClass));
    }
}
