package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글 개수가 3개인지 확인")
    public void findAllByBlogIdTest(){
//        GIVEN
        long blogId = 2;
//        WHEN
        List<ReplyFindByIdDTO> list = replyRepository.findAllByBlogId(blogId);
//        THEN
        assertEquals(3, list.size());
    }

    @Test
    @Transactional
    @DisplayName("5번 댓글 확인")
    public void findByReplyIdTest(){
//        GIVEN
        long replyId = 5;
//        WHEN
        ReplyFindByIdDTO reply = replyRepository.findByReplyId(replyId);
//        THEN
        assertEquals(replyId, reply.getReplyId());
        assertEquals("엄준식", reply.getReplyWriter());
    }
}
