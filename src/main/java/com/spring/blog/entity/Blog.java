package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

// 역직렬화(DB -> 자바객체)가 가능하도록 blog 테이블 구조에 맞춰서 멤버변수 선언
// 대부분 실무에서는 엔터티 클래스에는 Setter를 만들지 않음
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder // 빌더패턴 생성자를 쓸 수 있게 해줌
@Entity
@DynamicInsert  // null인 필드값에 기본값 지정된 요소를 집어넣음
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blogId; // 숫자는 long형을 사용함

    @Column(nullable = false)   // not null
    private String writer;

    @Column(nullable = false)   // not null
    private String blogTitle;

    @Column(nullable = false)   // not null
    private String blogContent;

    @ColumnDefault("TIMESTAMP")
    private LocalDateTime publishedAt;
    @ColumnDefault("TIMESTAMP")
    private LocalDateTime updatedAt;

    @ColumnDefault("0") // 조회수는 기본값을 0으로 설정
    private Long blogCount; // Wrapper Long형을 사용, 기본형 변수는 null을 가질 수 없음
    
//    @PrePersist 어노테이션은 insert, update 되기 전 실행할 로직을 작성
    @PrePersist
    public void setDefaultValue(){
        this.blogCount = this.blogCount == null ? 0 : this.blogCount;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

//    @PreUpdate 어노테이션은 update 되기 전 실행할 로직을 작성
    @PreUpdate
    public void setUpdateValue(){
        this.updatedAt = LocalDateTime.now();
    }
}
