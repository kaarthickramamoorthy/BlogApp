package com.prathickya.blogApp.repository;

import com.prathickya.blogApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//Will be automatically scanned no need to add @Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
