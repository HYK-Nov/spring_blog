package com.spring.blog.service;


import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> findAllByBlogId(long blogId);
    Reply findByReplyId(long replyId);
    void deleteByReplyId(long replyId);
    void save(Reply replyCreateDTO);
    void update(Reply replyUpdateRequestDTO);
}
