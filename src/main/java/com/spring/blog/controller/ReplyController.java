package com.spring.blog.controller;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {
//    컨트롤러는 서비스를 호출
    ReplyService replyService;
    
    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }
    
//    글 번호에 맞는 전체 댓글을 가져오는 메서드
//    어떤 자원에 접근할 것인지만 URI에 명시(메서드가 행동을 결정)
    @GetMapping("/{blogId}/all")
    //        rest 서버는 응답시 응답코드와 응답객체를 넘기기 때문에 ResponseEntity<자료형>을 리턴
    public ResponseEntity<List<ReplyFindByIdDTO>> findAllReplies(@PathVariable long blogId){

        List<ReplyFindByIdDTO> replyList = replyService.findAllByBlogId(blogId);

        return ResponseEntity
                .ok() // 200코드
                .body(replyList); // 리플목록
    }
}
