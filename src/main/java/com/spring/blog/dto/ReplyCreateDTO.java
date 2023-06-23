package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class ReplyCreateDTO {
    private long blogId;
    private String replyWriter;
    private String replyContent;

    public ReplyCreateDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
