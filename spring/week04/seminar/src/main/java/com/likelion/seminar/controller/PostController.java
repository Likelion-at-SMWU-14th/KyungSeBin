package com.likelion.seminar.controller;

import com.likelion.seminar.dto.PostDTO;
import com.likelion.seminar.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostDTO postDTO) {
        postService.createPost(postDTO);
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping
    public List<PostDTO> getPosts() {
        return postService.getPosts();
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id,
                           @RequestBody PostDTO postDTO) {
        postService.updatePost(id, postDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
