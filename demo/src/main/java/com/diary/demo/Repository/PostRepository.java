package com.diary.demo.Repository;

import com.diary.demo.Models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {



}
