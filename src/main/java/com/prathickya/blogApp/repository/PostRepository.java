package com.prathickya.blogApp.repository;

import com.prathickya.blogApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//Will be automatically scanned no need to add @Repository
//Spring Uses SimplaJPARepository implementation.
//@Repository, @Transactional was already used by SimplaJPARepository, so we no need to use it

public interface PostRepository extends JpaRepository<Post, Long> {
}
