package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class ReplyFindByIdDTO {
    private long replyId;
    private String replyWriter;
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

//    DTO는 Entity 객체를 이용해서 생성될 수 있어야 하나
//    반대는 성립하지 않는다. (Entity는 DTO의 내부 구조를 알 필요 없음)
    public ReplyFindByIdDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
        this.publishedAt = reply.getPublishedAt();
        this.updatedAt = reply.getUpdatedAt();
    }
}
