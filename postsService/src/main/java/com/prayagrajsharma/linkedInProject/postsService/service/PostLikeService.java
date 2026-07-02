package com.prayagrajsharma.linkedInProject.postsService.service;

import com.prayagrajsharma.linkedInProject.postsService.entity.PostLike;
import com.prayagrajsharma.linkedInProject.postsService.exception.BadRequestException;
import com.prayagrajsharma.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.prayagrajsharma.linkedInProject.postsService.repository.PostLikeRepository;
import com.prayagrajsharma.linkedInProject.postsService.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        long userId = 1L;
        log.info("liking the post by user: {}on post: {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post is not found {}"+ postId));

        boolean hasLikedAlready = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if(hasLikedAlready) throw new BadRequestException("Cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

        // TODO: send the notification to the user..
    }

    @Transactional
    public void unLikePost(Long postId) {
        long userId = 1L;
        log.info("unliking the post by user: {}on post: {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post is not found {}"+ postId));

        boolean hasLikedAlready = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if(!hasLikedAlready) throw new BadRequestException("Cannot unlike the post again");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        // TODO: send the notification to the user..

    }
}
