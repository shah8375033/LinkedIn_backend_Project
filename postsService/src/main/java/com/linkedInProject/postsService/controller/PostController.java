package com.linkedInProject.postsService.controller;

import com.linkedInProject.postsService.dto.PostCreateResquestDto;
import com.linkedInProject.postsService.dto.PostDto;

import com.linkedInProject.postsService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {
    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateResquestDto  postCreateResquestDto) {
        PostDto postDto = postService.createPost(postCreateResquestDto,1L);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Long postId) {
        PostDto postDto=postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable("userId") Long userId) {
        List<PostDto> postDto=postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(postDto);
    }

}
