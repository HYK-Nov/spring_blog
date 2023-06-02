package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // 컨트롤러 어노테이션 1. Bean 등록 2. URL 매핑 처리 기능을 함께 가지고 있으므로
            // 다른 어노테이션이랑 교환해서 쓸 수 없음
@RequestMapping("/blog")
public class blogController {

//    컨트롤러 레이어는 서비스 레이어를 직접 호출
    private BlogService blogService;

    @Autowired
    public blogController(BlogService blogService){
        this.blogService = blogService;
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);
        return "board/list";
    }

    @GetMapping("/list/{bId}")
    public String detail(@PathVariable long bId, Model model){
        System.out.println("/detail/"+bId);
        Blog blog = blogService.findById(bId);
        model.addAttribute("blog", blog);
        return "board/detail";
    }

}
