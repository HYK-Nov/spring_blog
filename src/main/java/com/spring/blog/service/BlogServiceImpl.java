package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service // Bean 컨테이너에 적재
public class BlogServiceImpl implements BlogService {

    BlogRepository blogRepository;
    ReplyRepository replyRepository;
    BlogJPARepository blogJPARepository;
    ReplyJPARepository replyJPARepository;

    @Autowired // 생성자 주입이 속도가 더 빠름
    public BlogServiceImpl(BlogRepository blogRepository, ReplyRepository replyRepository,
                           BlogJPARepository blogJPARepository, ReplyJPARepository replyJPARepository) {
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
        this.blogJPARepository = blogJPARepository;
        this.replyJPARepository = replyJPARepository;
    }

    @Override
    public Page<Blog> findAll(Long pageNum) {
//        return blogRepository.findAll(); // Mybatis를 활용한 전체 글 가져오기
//        return blogJPARepository.findAll(); // JPA를 활용한 전체 글 가져오기

//        페이징 처리에 관련된 정보를 먼저 객체로 생성
        Pageable pageable = PageRequest.of((getCalibratedPageNum(pageNum) - 1), 10, Sort.by("blogId").descending());

//        생성된 페이징 정보를 파라미터로 제공해서 findAll()을 호출
        return blogJPARepository.findAll(pageable);
    }

    public int getCalibratedPageNum(Long pageNum){
        if (pageNum == null || pageNum < 1L){
            pageNum = 1L;
        }else{
            int totalPagesCount = (int) Math.ceil(blogJPARepository.count() / 10.0);
            pageNum = (pageNum > totalPagesCount) ? totalPagesCount : pageNum;
        }
        return pageNum.intValue();
    }

    @Override
    public Blog findById(long blogId) {
//        return blogRepository.findById(blogId);
//        JPA의 findById는 Optional을 리턴하므로, 일반 객체로 만들기 위해 뒤에 .get()을 사용
//        Optional은 참조형 변수에 대해서 null 검사 및 처리를 쉽게 할 수 있도록 제공하는 제네릭
//        JPA는 Optional을 쓰는 것을 권장하기 위해 리턴 자료형으로 강제해둠
        return blogJPARepository.findById(blogId).get();
    }

    @Transactional // 둘 다 실행 or 둘 다 실행X
    @Override
    public void deleteById(long blogId) {
//        replyRepository.deleteAllReplyByBlogId(blogId);
//        blogRepository.deleteById(blogId);
        replyJPARepository.deleteAllByBlogId(blogId);
        blogJPARepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {
//        blogRepository.save(blog);
        blogJPARepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
//        JPA의 수정은, findById()를 이용해 얻어온 엔터티 클래스 객체 내부 내용물을 수정한 다음
//        해당 요소를 save() 해서 이루어짐
        Blog updated = blogJPARepository.findById(blog.getBlogId()).get();  // 준영속 상태
        updated.setBlogTitle(blog.getBlogTitle());  // 커맨드 객체에 들어온 제목으로 수정
        updated.setBlogContent(blog.getBlogContent());  // 커맨드 객체에 들어온 내용으로 수정

        blogJPARepository.save(updated);
//        blogRepository.update(blog);
    }

    @Override
    public void updateBlogCount(Blog blog) {
        blogRepository.updateBlogCount(blog);
    }
}
