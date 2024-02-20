package com.service.fruitfolio.comment;

import com.service.fruitfolio.sort.ProductSort;
import com.service.fruitfolio.sort.ProductSortRepository;
import com.service.fruitfolio.sort.ProductSortService;
import com.service.fruitfolio.sort.SortCommentsResponse;
import com.service.fruitfolio.user.User;
import com.service.fruitfolio.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductSortRepository productSortRepository;
    private final UserRepository userRepository;

    public Comment create(CommentRequest commentRequest, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Comment comment = new Comment();

        Optional<ProductSort> productSort = productSortRepository.findById(commentRequest.getProductSortId());
        if (productSort.isEmpty()) {
            return null;
        }
        comment.setUser(user);
        comment.setProductSort(productSort.orElse(null));
        comment.setText(commentRequest.getText());
        return commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public List<MyCommentsResponse> getMyComments(MyCommentsRequest myCommentsRequest, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Optional<ProductSort> productSort = productSortRepository.findById(myCommentsRequest.getId());
        if (productSort.isEmpty()) {
            return null;
        }

        List<Comment> comments = getSortComments(productSort.orElse(null));
        List<MyCommentsResponse> myComments = new ArrayList<>();

        for (Comment comment : comments) {
            User user1 = comment.getUser();
            if (user1.getId().equals(user.getId())) {
                MyCommentsResponse myCommentsResponse = new MyCommentsResponse();
                myCommentsResponse.setId(comment.getId());
                myCommentsResponse.setText(comment.getText());
                myCommentsResponse.setCreateDate(comment.getCreateDate());
                myComments.add(myCommentsResponse);
            }
        }
        return myComments;
    }

    public List<Comment> getSortComments(ProductSort productSort) {
        return commentRepository.getByProductSort(productSort);
    }


    public String deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
        return "OK";
    }
}
