-- 댓글 테이블 설정
CREATE TABLE reply(
                      reply_id INT AUTO_INCREMENT PRIMARY KEY,
                      blog_id INT NOT NULL,
                      reply_writer VARCHAR(40) NOT NULL,
                      reply_content VARCHAR(200) NOT NULL,
                      published_at DATETIME DEFAULT NOW(),
                      updated_at DATETIME DEFAULT NOW()
);
-- 외래키 설정
-- blog_id에는 기존에 존재하는 글의 blog_id만 들어가야 함
ALTER TABLE reply ADD CONSTRAINT fk_reply FOREIGN KEY (blog_id) REFERENCES blog(blog_id);
-- 더미 데이터 입력(테스트 DB에서만 사용)
INSERT INTO reply
VALUES
    (NULL, 2, "만두콘", "ㄵ", DEFAULT, DEFAULT),
    (NULL, 2, "ㅇㅇ", "냐아아도키도키시테타", DEFAULT, DEFAULT),
    (NULL, 2, "ㅇㅇ", "ㄵ", DEFAULT, DEFAULT),
    (NULL, 1, "ㅂㅈㄷ", "나는 경기도 안양의 이준영이다", DEFAULT, DEFAULT),
    (NULL, 3, "엄준식", "엄", DEFAULT, DEFAULT);