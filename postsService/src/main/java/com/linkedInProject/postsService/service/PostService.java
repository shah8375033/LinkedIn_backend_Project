package com.linkedInProject.postsService.service;

import com.linkedInProject.postsService.auth.AuthContextHolder;
import com.linkedInProject.postsService.client.ConnectionServiceClient;
import com.linkedInProject.postsService.dto.PersonDto;
import com.linkedInProject.postsService.dto.PostCreateResquestDto;
import com.linkedInProject.postsService.dto.PostDto;
import com.linkedInProject.postsService.entity.Post;
import com.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.linkedInProject.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final ConnectionServiceClient connectionServiceClient;

    public PostDto createPost(PostCreateResquestDto postCreateResquestDto, Long userId) {
        Post post=modelMapper.map(postCreateResquestDto,Post.class);
        post.setUserId(userId);
        post=postRepository.save(post);
        return modelMapper.map(post,PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        Long userId= AuthContextHolder.getCurrentUserId();
        List<PersonDto> list=connectionServiceClient.getFirstDegreeConnections(userId);
        log.info("Get post by id {}", postId);
        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Resource not found with id " + postId));
        return  modelMapper.map(post,PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Get all posts of user {}", userId);
        List<Post> postList=postRepository.findByUserId(userId);
        return postList.stream()
                .map((element)->modelMapper.map(element,PostDto.class))
                .collect(Collectors.toList());
    }
}
