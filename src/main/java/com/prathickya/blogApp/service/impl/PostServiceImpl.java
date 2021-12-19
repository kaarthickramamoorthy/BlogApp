package com.prathickya.blogApp.service.impl;

import com.prathickya.blogApp.dto.PostDto;
import com.prathickya.blogApp.entity.Post;
import com.prathickya.blogApp.exception.ResourceNotFoundException;
import com.prathickya.blogApp.repository.PostRepository;
import com.prathickya.blogApp.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getDescription(), postDto.getContent());
        Post savedPost = postRepository.save(post);
        return convertEntityToDto(savedPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> allPostsEntity = postRepository.findAll();
        return allPostsEntity.stream()
                      .map(this::convertEntityToDto)
                      .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id + ""));
        return new PostDto(postEntity.getId(), postEntity.getTitle(), postEntity.getDescription(), postEntity.getContent());
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id + ""));
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());
        final Post savedPost = postRepository.save(postEntity);
        return convertEntityToDto(savedPost);
    }

    public void deletePost(long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id + ""));
        postRepository.delete(postEntity);
    }

    private PostDto convertEntityToDto(Post savedPost) {
        PostDto responseDto = new PostDto();
        responseDto.setId(savedPost.getId());
        responseDto.setTitle(savedPost.getTitle());
        responseDto.setDescription(savedPost.getDescription());
        responseDto.setContent(savedPost.getContent());
        return responseDto;
    }
}
