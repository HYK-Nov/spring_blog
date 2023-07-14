package com.spring.blog.repository;

import com.spring.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    계정명으로 전체 정보를 얻어오는 쿼리 메서드를 작성
    User findByLoginId(String loginId);
}
