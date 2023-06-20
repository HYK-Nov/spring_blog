package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import com.spring.blog.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @Transactional
    @DisplayName("댓글번호 2번 삭제")
    public void deleteByReplyIdTest(){
        long replyId = 2;
        long blogId = 2;

        replyRepository.deleteByReplyId(replyId);

        assertNull(replyRepository.findByReplyId(replyId));
        assertEquals(replyRepository.findAllByBlogId(blogId).size(), 2);
    }

    @Test
    @Transactional
    public void saveTest(){
        long blogId = 1;
        String replyWriter = "ㅇㅇ";
        String replyContent = "test";
        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyRepository.save(replyInsertDTO);
        List<ReplyFindByIdDTO> list = replyRepository.findAllByBlogId(blogId);
        ReplyFindByIdDTO result = list.get(list.size()-1);

        assertEquals(result.getReplyWriter(), replyWriter);
        assertEquals(result.getReplyContent(), replyContent);
    }

    @Test
    @Transactional
    public void updateTest(){
        long replyId = 3;
        String replyWriter = "333번 작성자";
        String replyContent = "333번 내용";
        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
                .replyId(replyId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyRepository.update(replyUpdateDTO);
        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);

        assertEquals(result.getReplyWriter(), replyWriter);
        assertEquals(result.getReplyContent(), replyContent);
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }
}
