package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // DROP TABLE시 필요한 어노테이션
public class BlogRepositoryTest {
    @Autowired
    BlogRepository blogRepository;

    @BeforeEach // 각 테스트 전에 공통적으로 실행할 코드를 저장해두는 곳
    public void setBlogTable() {
        blogRepository.createBlogTable(); // blog 테이블 생성
        blogRepository.insertTestData(); // 생성된 blog 테이블에 더미데이터 3개 입력
    }

    @Test
    @DisplayName("전체 행을 얻어오고, 그 중 자바 1번 인덱스 행만 추출해 번호 확인")
    public void findAllTest() {
//        GIVEN // (사람 기준)2번 요소 조회를 위한 fixture 선언
        int blogId = 1;
//        WHEN // DB에 있는 모든 데이터를 자바 엔티티로 역직렬화
        List<Blog> blogList = blogRepository.findAll();
        System.out.println(blogList);
//        THEN // 더미데이터는 3개일 것
        assertEquals(3, blogList.size());
//        2번째 객체의 blogId는 2일 것이다.
        assertEquals(2, blogList.get(blogId).getBlogId());
    }

    @Test
    public void saveTest() {
//        GIVEN
        String writer = "4번 유저";
        String blogTitle = "4번 제목";
        String blogContent = "4번 내용";

        /*Blog blog = new Blog();
        blog.setWriter(writer);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(blogContent);*/

//        빌더 패턴
//        장점: 파라미터 순서를 뒤바꿔서 집어넣어도 됨
        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
//        WHEN
        blogRepository.save(blog);
        List<Blog> blogList = blogRepository.findAll();
//        THEN
        assertEquals(4, blogList.size());
        assertEquals(writer, blogList.get(3).getWriter());
        assertEquals(blogTitle, blogList.get(3).getBlogTitle());
        assertEquals(blogContent, blogList.get(3).getBlogContent());
    }

    @Test
    @DisplayName("2번 글을 조회했을 때, 제목과 글쓴이와 번호가 단언대로 받아와지는지 확인")
    public void findByIdTest() {
//        GIVEN
        long blogId = 2;
//        WHEN
        Blog blog = blogRepository.findById(blogId);
//        THEN
        assertEquals("2번 유저", blog.getWriter());
        assertEquals("2번 제목", blog.getBlogTitle());
        assertEquals(2, blog.getBlogId());
    }

    @Test
    @DisplayName("2번 삭제 후 전체 목록 가져왔을 때 남은 행수 2개, 삭제한 번호 조회시 null")
    public void deleteByIdTest(){
//        GIVEN
        long blogId = 2;
//        WHEN
        blogRepository.deleteById(blogId);
//        THEN
        assertEquals(2, blogRepository.findAll().size());
        assertNull(blogRepository.findById(blogId));
    }

    @Test
    public void updateTest(){
//        GIVEN
        long blogId = 2;
        String blogTitle = "수정된 제목";
        String blogContent = "수정된 내용";

        /*Blog blog = blogRepository.findById(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(blogContent);*/
        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
//        WHEN
        blogRepository.update(blog);
        System.out.println(blogRepository.findById(blogId));
//        THEN
        assertEquals(blogTitle, blogRepository.findById(blogId).getBlogTitle());
        assertEquals(blogContent, blogRepository.findById(blogId).getBlogContent());
    }

    @AfterEach // 각 단위테스트 끝난 후에 실행할 구문을 작성
    public void dropBlogTable() {
        blogRepository.dropBlogTable(); // blog 테이블 지우기
    }

}
