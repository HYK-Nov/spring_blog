package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // bean container에서 관리하도록 해야 사용할 수 있음
public interface BlogJPARepository extends JpaRepository<Blog, Long> {  // 기본형 자료 사용 불가
}
