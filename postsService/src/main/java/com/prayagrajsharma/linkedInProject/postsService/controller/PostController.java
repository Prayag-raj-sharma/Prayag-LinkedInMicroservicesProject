package com.prayagrajsharma.linkedInProject.postsService.controller;

import com.prayagrajsharma.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.prayagrajsharma.linkedInProject.postsService.dto.PostDto;
import com.prayagrajsharma.linkedInProject.postsService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    private ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        PostDto postDto = postService.createPost(postCreateRequestDto, 1L);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);

    }

    @GetMapping("/users/{userId}/allPosts")
    private ResponseEntity<List<PostDto>> getAllPostOfUser(@PathVariable Long userId) {
        List<PostDto> posts = postService.getAllPostOfUser(userId);
        return ResponseEntity.ok(posts);
    }
}
