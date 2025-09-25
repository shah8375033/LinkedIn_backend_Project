package com.linkedInProject.postsService.service;

import com.linkedInProject.postsService.entity.Post;
import com.linkedInProject.postsService.entity.PostLike;
import com.linkedInProject.postsService.exception.BadRequestException;
import com.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.linkedInProject.postsService.repository.PostLikeRepository;
import com.linkedInProject.postsService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikesService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void likePost(Long postId) {
        Long userId=1L;
        log.info("User with id{} Liking post with id {}",userId, postId);
        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id " + postId + " not found"));

        Boolean hasAlreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLiked) throw new BadRequestException("You cannot like this post");
        PostLike postLike=new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);

        //TODO: send notification to owner of post
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId=1L;
        log.info("User with id{} Unliking post with id {}",userId, postId);
        postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post with id " + postId + " not found"));

        Boolean hasAlreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!hasAlreadyLiked) throw new BadRequestException("You cannot unlike this post");

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);
    }
}
