package com.spring.blog.controller;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.exception.NotFoundReplyByReplyIdException;
import com.spring.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    //    컨트롤러는 서비스를 호출
    ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    //    글 번호에 맞는 전체 댓글을 가져오는 메서드
//    어떤 자원에 접근할 것인지만 URI에 명시(메서드가 행동을 결정)
    @GetMapping("/{blogId}/all")
    //        rest 서버는 응답시 응답코드와 응답객체를 넘기기 때문에 ResponseEntity<자료형>을 리턴
    public ResponseEntity<List<ReplyResponseDTO>> findAllReplies(@PathVariable long blogId) {

        List<ReplyResponseDTO> replies = replyService.findAllByBlogId(blogId);

        return ResponseEntity
                .ok() // 200코드, 상태 코드와 body에 전송할 데이터를 같이 작성할 수 있음
                .body(replies); // 리플목록
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<?> findReply(@PathVariable long replyId) {

        ReplyResponseDTO reply = replyService.findByReplyId(replyId);

        if (reply == null) {
            try {
                throw new NotFoundReplyByReplyIdException("없는 댓글 번호를 조회했습니다");
            } catch (NotFoundReplyByReplyIdException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾는 댓글 번호가 없습니다.");
            }
        }
        return ResponseEntity.ok(reply);
    }

    @PostMapping("")
//    RestController는 데이터를 JSON으로 주고 받음
//    따라서 @RequestBody를 이용해 JSON으로 들어온 데이터를 역직렬화 하도록 설정
    public ResponseEntity<String> insertReply(@RequestBody ReplyCreateDTO replyCreateDTO){
        
        replyService.save(replyCreateDTO);
        
        return ResponseEntity.ok("저장 완료");
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable long replyId){

        replyService.deleteByReplyId(replyId);

        return ResponseEntity.ok("삭제 완료");
    }
    
//    수정 로직은 put, patch 메서드로 /reply/댓글번호 ReplyUpdateRequestDTO를 requestBody로 받아 요청처리 함
//    @PutMapping("/{replyId}")
    @PatchMapping("/{replyId}")
    public ResponseEntity<String> updateReply(@PathVariable long replyId, @RequestBody ReplyUpdateRequestDTO replyUpdateRequestDTO){

//        JSON 데이터에 replyId를 포함하는 대신 url에 포함시켰으므로 requestBody에 추가
        replyUpdateRequestDTO.setReplyId(replyId);
//        setter에 포함
        replyService.update(replyUpdateRequestDTO);

        return ResponseEntity.ok("수정 완료");
    }
}
