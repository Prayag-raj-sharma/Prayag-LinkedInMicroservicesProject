package com.prayagrajsharma.linkedInProject.postsService.service;

import com.prayagrajsharma.linkedInProject.postsService.auth.AuthContextHolder;
import com.prayagrajsharma.linkedInProject.postsService.entity.Post;
import com.prayagrajsharma.linkedInProject.postsService.entity.PostLike;
import com.prayagrajsharma.linkedInProject.postsService.event.PostLikedEvent;
import com.prayagrajsharma.linkedInProject.postsService.exception.BadRequestException;
import com.prayagrajsharma.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.prayagrajsharma.linkedInProject.postsService.repository.PostLikeRepository;
import com.prayagrajsharma.linkedInProject.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, PostLikedEvent> postLikedKafkaTemplate;

    @Transactional
    public void likePost(Long postId) {
        long userId = AuthContextHolder.getCurrentUserId();
        log.info("liking the post by user: {}on post: {}", userId, postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post is not found {}"+ postId));

        boolean hasLikedAlready = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if(hasLikedAlready) throw new BadRequestException("Cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

        // send the notification to the user
        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                            .postId(postId)
                            .ownerUserId(userId)
                            .likedByUserId(post.getUserId())
                            .build();
        postLikedKafkaTemplate.send("post_liked_topic", postLikedEvent);
    }

    @Transactional
    public void unLikePost(Long postId) {
        long userId = AuthContextHolder.getCurrentUserId();
        log.info("unliking the post by user: {}on post: {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post is not found {}"+ postId));

        boolean hasLikedAlready = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if(!hasLikedAlready) throw new BadRequestException("Cannot unlike the post again");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        // TODO: send the notification to the user..

    }
}
