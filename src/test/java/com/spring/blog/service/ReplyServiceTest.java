package com.spring.blog.service;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    @Transactional
    public void findAllByBlogIdTest(){
        long blogId = 2;

        List<ReplyFindByIdDTO> list = replyService.findAllByBlogId(blogId);

        assertEquals(list.size(), 3);
    }

    @Test
    @Transactional
    public void findByReplyIdTest(){
        long replyId = 5;
        String replyWriter = "엄준식";

        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);

        assertEquals(result.getReplyId(), replyId);
        assertEquals(result.getReplyWriter(), replyWriter);
    }

    @Test
    @Transactional
    public void deleteByReplyIdTest(){
        long replyId = 3;

        replyService.deleteByReplyId(replyId);
        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);

        assertNull(result);
    }

    @Test
    @Transactional
    public void saveTest(){
        long blogId = 3;
        String replyWriter = "토토비";
        String replyContent = "토비보자";
        ReplyInsertDTO reply = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.save(reply);
        List<ReplyFindByIdDTO> list = replyService.findAllByBlogId(blogId);
        ReplyFindByIdDTO result = list.get(list.size()-1);

        assertEquals(list.size(), 2);
        assertEquals(result.getReplyWriter(), replyWriter);
        assertEquals(result.getReplyContent(), replyContent);
    }

    @Test
    @Transactional
    public void updateTest(){
        long replyId = 2;
        String replyWriter = "수정된 작성자";
        String replyContent = "수정된 내용";
        ReplyUpdateDTO reply = ReplyUpdateDTO.builder()
                .replyId(2)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.update(reply);
        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);

        assertEquals(result.getReplyId(), replyId);
        assertEquals(result.getReplyWriter(), replyWriter);
        assertEquals(result.getReplyContent(), replyContent);
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }

    @Test
    @Transactional
    public void deleteAllReplyByBlogIdTest(){
        long blogId = 1;

        List<ReplyFindByIdDTO> list = replyService.findAllByBlogId(blogId);

        assertEquals(list.size(), 0);
    }
}
