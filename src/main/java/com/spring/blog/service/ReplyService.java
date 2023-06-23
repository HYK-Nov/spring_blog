package com.spring.blog.service;


import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;

import java.util.List;

public interface ReplyService {
    List<ReplyResponseDTO> findAllByBlogId(long blogId);
    ReplyResponseDTO findByReplyId(long replyId);
    void deleteByReplyId(long replyId);
    void save(ReplyCreateDTO replyCreateDTO);
    void update(ReplyUpdateRequestDTO replyUpdateRequestDTO);
}
