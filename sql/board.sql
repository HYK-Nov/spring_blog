-- 블로그 테이블 생성 구문
CREATE TABLE blog(
                     blog_id INT AUTO_INCREMENT PRIMARY KEY,
                     writer VARCHAR(16) NOT NULL,
                     blog_title VARCHAR(200) NOT NULL,
                     blog_content VARCHAR(4000) NOT NULL,
                     published_at DATETIME DEFAULT NOW(),
                     updated_at DATETIME DEFAULT NOW(),
                     blog_count INT DEFAULT 0
);
-- 더미 데이터 생성 구문
INSERT INTO blog
VALUES (NULL, '1번 유저', '1번 제목', '1번 본문', now(), now(), DEFAULT),
       (NULL, '2번 유저', '2번 제목', '2번 본문', now(), now(), DEFAULT),
       (NULL, '3번 유저', '3번 제목', '3번 본문', now(), now(), DEFAULT);
-- 테이블 삭제 구문
DROP TABLE blog;