package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BlogServiceTest {

    @Autowired
    BlogService blogService;

    @Test
    @Transactional
    public void findAllTest() {
//        GIVEN
//        WHEN
        List<Blog> blogList = blogService.findAll();
//        THEN
        assertEquals(3, blogList.size());
//        assertThat(blogList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void findByIdTest() {
//        GIVEN
        long blogId = 2;
//        WHEN
        Blog blog = blogService.findById(blogId);
//        THEN
        assertEquals(blogId, blog.getBlogId());
    }

    @Test
    @Transactional
//    @Commit // 트랜잭션 적용된 테스트의 결과를 커밋해서 DB에 반영하도록 만들어줌
    public void deleteByIdTest() {
//        GIVEN
        long blogId = 2;
//        WHEN
        blogService.deleteById(blogId);
//        THEN
        assertNull(blogService.findById(blogId));
        assertEquals(2, blogService.findAll().size());
    }

    @Test
    @Transactional
    public void saveTest() {
//        GIVEN
        String writer = "추가 유저";
        String title = "추가 제목";
        String content = "추가 내용";
        int lastIndex = 3;
        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(title)
                .blogContent(content)
                .build();
//        WHEN
        blogService.save(blog);
        List<Blog> blogList = blogService.findAll();
//        THEN
        assertEquals(4, blogList.size());
        assertEquals(writer, blogList.get(lastIndex).getWriter());
        assertEquals(title, blogList.get(lastIndex).getBlogTitle());
        assertEquals(content, blogList.get(lastIndex).getBlogContent());
    }

    @Test
    @Transactional
    public void updateTest() {
//        GIVEN
        long blogId = 2;
        String blogTitle = "수정된 제목";
        String blogContent = "수정된 내용";
        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
//        WHEN
        blogService.update(blog);
//        THEN
        assertEquals(blogTitle, blogService.findById(blogId).getBlogTitle());
        assertEquals(blogContent, blogService.findById(blogId).getBlogContent());
    }

//    blog와 함께 reply가 삭제되는 케이스는 따로 다시 테스트 코드를 하나 더 작성해주는게 좋음
}
