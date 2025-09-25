package com.linkedInProject.postsService.controller;

import com.linkedInProject.postsService.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable("postId") Long postId) {
        postLikesService.likePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikepost(@PathVariable("postId") Long postId) {
        postLikesService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }
}
