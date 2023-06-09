package com.spring.blog.entity;

import lombok.*;

import java.time.LocalDateTime;

// Entity는 불변성을 지키기 위해 Setter를 제공하지 않음
// 한번 생성된 데이터가 변경될 가능성을 없앰
@Getter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class Reply {
    private long replyId;
    private long blogId;
    private String replyWriter;
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
}
