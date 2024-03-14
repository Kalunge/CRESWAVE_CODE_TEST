package com.blog_api.blog_api.repository;

import com.blog_api.blog_api.entity.Post;
import com.blog_api.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
