package com.service.fruitfolio.comment;

import com.service.fruitfolio.sort.SortCommentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public SortCommentsResponse create(@RequestBody CommentRequest commentRequest,
                                       Principal connectedUser) {
        return commentService.create(commentRequest, connectedUser);
    }

    @PostMapping("/myComments")
    public List<MyCommentsResponse> getMyComments(@RequestBody MyCommentsRequest myCommentsRequest,
                                                  Principal connectedUser) {
        return commentService.getMyComments(myCommentsRequest, connectedUser);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteCommentById(@PathVariable("id") Integer id) {

        return commentService.deleteCommentById(id);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleExceptionEmail(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
