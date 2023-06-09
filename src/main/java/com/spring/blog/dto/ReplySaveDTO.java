package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class ReplySaveDTO {
    private long blogId;
    private String replyWriter;
    private String replyContent;

    public ReplySaveDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
