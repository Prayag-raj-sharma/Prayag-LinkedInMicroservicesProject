package com.prayagrajsharma.linkedInProject.postsService.repository;

import com.prayagrajsharma.linkedInProject.postsService.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserIdAndPostId(long userId, Long postId);

    void deleteByUserIdAndPostId(long userId, Long postId);
}
