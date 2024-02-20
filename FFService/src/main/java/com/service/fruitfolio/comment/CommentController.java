package com.service.fruitfolio.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public Comment create(@RequestBody CommentRequest commentRequest, Principal connectedUser) {
        return commentService.create(commentRequest, connectedUser);
    }

    @GetMapping("/all")
    public List<Comment> findAll() {
        return commentService.findAll();
    }

    @PostMapping("/myComments")
    public List<MyCommentsResponse> getMyComments(@RequestBody MyCommentsRequest myCommentsRequest, Principal connectedUser) {
        return commentService.getMyComments(myCommentsRequest, connectedUser);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteCommentById(@PathVariable("id") Integer id) {
        return commentService.deleteCommentById(id);
    }
}
