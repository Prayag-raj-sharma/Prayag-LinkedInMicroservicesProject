package com.prayagrajsharma.linkedInProject.postsService.service;

import com.prayagrajsharma.linkedInProject.postsService.auth.AuthContextHolder;
import com.prayagrajsharma.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.prayagrajsharma.linkedInProject.postsService.dto.PostDto;
import com.prayagrajsharma.linkedInProject.postsService.entity.Post;
import com.prayagrajsharma.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.prayagrajsharma.linkedInProject.postsService.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Creating post for user with userId: {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.info("Getting post for postId: {}", postId);
        Long userId = AuthContextHolder.getCurrentUserId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found for postId: "+ postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostOfUser(Long userId) {
        log.info("Getting all posts for userId: {}", userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts
                .stream()
                .map(post->modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
