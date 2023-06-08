package com.spring.blog.controller;

import com.spring.blog.dto.BlogUpdateDTO;
import com.spring.blog.entity.Blog;
import com.spring.blog.exception.NotFoundBlogIdException;
import com.spring.blog.service.BlogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // 컨트롤러 어노테이션 1. Bean 등록 2. URL 매핑 처리 기능을 함께 가지고 있으므로
            // 다른 어노테이션이랑 교환해서 쓸 수 없음
@RequestMapping("/blog")
@Log4j2 // sout이 아닌 로깅을 통한 디버깅을 위해 선언
public class blogController {

//    컨트롤러 레이어는 서비스 레이어를 직접 호출
    private BlogService blogService;

    @Autowired
    public blogController(BlogService blogService){
        this.blogService = blogService;
    }

    @RequestMapping("/list")
    public String list(Model model){
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);
        return "blog/list";
    }

    @RequestMapping("/detail/{bId}")
    public String detail(@PathVariable long bId, Model model){
        Blog blog = blogService.findById(bId);

        if (blog == null){
            try {
                throw new NotFoundBlogIdException("없는 번호 조회. 조회번호: "+bId);
            }catch (NotFoundBlogIdException e){
                e.printStackTrace();
                return "blog/NotFoundBlogIdExceptionResultPage";
            }
        }
        blogService.updateBlogCount(blog);
        model.addAttribute("blog", blog);
        return "blog/detail";
    }
    
//    폼 페이지와 실제 등록 url은 같은 url을 쓰도록 함
//    대신 폼 페이지는 GET방식으로 접속했을 때 연결
//    폼에서 작성 완료한 내용을 POST 방식으로 제출해 저장
    @GetMapping("/insert")
    public String insert(){
        return "blog/blog-form";
    }

    @PostMapping("/insert")
    public String insert(Blog blog){
        blogService.save(blog);
        return "redirect:/blog/list";
    }

//    DELETE 로직은 삭제 후 /blog/list 로 리다이렉트 되어서 자료가 삭제된 것을 확인
//    글 번호만으로 삭제 진행
//    폼에 추가한 삭제버튼 코드와 컨트롤러에 작성한 delete 메서드
    @PostMapping("/delete")
    public String delete(long blogId){
        blogService.deleteById(blogId);
//        log.info(blogId);
        return "redirect:/blog/list";
    }

//    UPDATE 구문은 대부분 INSERT와 비슷하지만
//    한 가지 차이점은, 폼이 이미 기존에 작성된 정보로 채워져 있다는 점
//    이를 구현하기 위해 수정 버튼이 눌렸을 때, 제일 먼저 해당 글 정보를 획득한 다음
//    폼 페이지에 model.addAttribute()로 보내줘야 함
//    그 다음 수정용 폼 페이지에 해당 자료를 채운 채 연결해주면 됨
//    이를 위해 value= 를 이용하면 미리 원하는 내용으로 폼을 채워둘 수 있음
    @PostMapping("/updateform")
    public String update(long blogId, Model model){
        Blog blog = blogService.findById(blogId);
        model.addAttribute("blog", blog);
        return "blog/blog-update-form";
    }

    @PostMapping("/update")
    public String update(Blog blog){
        blogService.update(blog);
//        log.info(blog);
        return "redirect:/blog/detail/"+blog.getBlogId();
    }
}
