package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Bean 컨테이너에 적재
public class BlogServiceImpl implements BlogService{

    BlogRepository blogRepository;
    ReplyRepository replyRepository;

    @Autowired // 생성자 주입이 속도가 더 빠름
    public BlogServiceImpl(BlogRepository blogRepository, ReplyRepository replyRepository){
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findById(long blogId) {
        return blogRepository.findById(blogId);
    }

    @Transactional // 둘 다 실행 or 둘 다 실행X
    @Override
    public void deleteById(long blogId) {
        replyRepository.deleteAllReplyByBlogId(blogId);
        blogRepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        blogRepository.update(blog);
    }

    @Override
    public void updateBlogCount(Blog blog) {
        blogRepository.updateBlogCount(blog);
    }
}
