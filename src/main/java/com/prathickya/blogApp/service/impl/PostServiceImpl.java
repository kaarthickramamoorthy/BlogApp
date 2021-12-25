package com.prathickya.blogApp.service.impl;

import com.prathickya.blogApp.dto.PostDto;
import com.prathickya.blogApp.entity.Post;
import com.prathickya.blogApp.exception.ResourceNotFoundException;
import com.prathickya.blogApp.payload.PostResponse;
import com.prathickya.blogApp.repository.PostRepository;
import com.prathickya.blogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getDescription(), postDto.getContent());
        Post savedPost = postRepository.save(post);
        return convertEntityToDto(savedPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder) {
        final Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                                Sort.by(sortBy).ascending() :
                                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        final Page<Post> pagedAllPosts = postRepository.findAll(pageable);
        List<Post> allPostsEntity = pagedAllPosts.getContent();
        final List<PostDto> postDtoList = allPostsEntity.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(pagedAllPosts.getNumber());
        postResponse.setPageSize(pagedAllPosts.getSize());
        postResponse.setTotalElements(pagedAllPosts.getTotalElements());
        postResponse.setTotalPages(pagedAllPosts.getTotalPages());
        postResponse.setLast(pagedAllPosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "id", id + ""));
        return convertEntityToDto(postEntity);
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
        /*
            Below code has been replaced by model mapper
            PostDto responseDto = new PostDto();
            responseDto.setId(savedPost.getId());
            responseDto.setTitle(savedPost.getTitle());
            responseDto.setDescription(savedPost.getDescription());
            responseDto.setContent(savedPost.getContent());
         */
        return modelMapper.map(savedPost, PostDto.class);
    }
}
