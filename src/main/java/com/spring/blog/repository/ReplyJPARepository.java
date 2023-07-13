package com.spring.blog.repository;

import com.spring.blog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyJPARepository extends JpaRepository<Reply, Long> {
//    blogId를 기준으로 전체 댓글을 가져오는 메서드를 쿼리 메서드 방식으로 생성
    List<Reply> findAllByBlogId(long blogId);
//    blogId를 기준으로 연관된 댓글을 전부 삭제하는 메서드
//    DELETE FROM reply WHERE blog_id = blogId
    void deleteAllByBlogId(long blogId);
}
