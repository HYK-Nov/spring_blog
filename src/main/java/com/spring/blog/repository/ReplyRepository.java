package com.spring.blog.repository;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {
    List<ReplyResponseDTO> findAllByBlogId(long blogId);
    ReplyResponseDTO findByReplyId(long replyId);
    void deleteByReplyId(long replyId);
    void save(ReplyCreateDTO replyCreateDTO);
    void update(ReplyUpdateRequestDTO replyUpdateRequestDTO);
    void deleteAllReplyByBlogId(long blogId);
}
