package com.spring.blog.service;

import com.spring.blog.dto.BlogUpdateDTO;
import com.spring.blog.entity.Blog;

import java.util.List;

public interface BlogService {

    //    비즈니스 로직을 담당할 메서드를 "정의"만 하면 됨

    //    전체 블로그 포스팅을 조회하는 메서드
    List<Blog> findAll();

    //    단일 포스팅 조회
    Blog findById(long blogId);

    //    단일 포스팅 삭제
    void deleteById(long blogId);

    //    단일 포스팅 게시
    void save(Blog blog);

    void update(Blog blog);

    void updateBlogCount(Blog blog);
}
