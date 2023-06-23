package com.spring.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.ReplyCreateDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // 원래 MVC 테스트는 브라우저를 켜아 테스트할 수 있기 때문에 브라우저를 대체할 객체를 만들어 수행
class ReplyControllerTest {

//    임시적으로 ReplyRepository 생성
//    Repository 레이어의 메서드는 쿼리문을 하나만 호출하는 것이 보장되지만
//    Service 레이어의 메서드는 추후에 쿼리문을 두 개 이상 호출 할 수도 있고, 그런 변경 점이 생겼을 때 테스트 코드도 같이 수정할 가능성이 생김
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private MockMvc mockMvc; // @AutoConfigureMockMvc 로 자동 주입

    @Autowired
    WebApplicationContext context;

    @Autowired // 데이터 직렬화에 사용하는 객체
    private ObjectMapper objectMapper;

    //    컨트롤러를 테스트 해야하는데 컨트롤러는 서버에 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지 않음
//    각 테스트 전에 설정
    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @DisplayName("2번 글에 대한 전체 댓글을 조회했을 때, 0번째 요소의 replyWriter는 만두콘, replyId는 1")
    void findAllRepliesTest() throws Exception {
//        GIVEN
        String replyWriter = "만두콘";
        long replyId = 1;
        String url = "/reply/2/all";

//        WHEN: 해당 주소로 접속 후 JSON 데이터 리턴받아 저장. ResultActions 형 자료로 JSON 저장
//        get() 메서드의 경우 작성 후 alt + enter 눌러서 mockmvc 관련 요소로 import
//        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        // fetch(url).then(res => res.json()); 에 대응하는 코드
        final ResultActions result = mockMvc.perform(get(url) // url 주소로 get 요청 넣기
                .accept(MediaType.APPLICATION_JSON)); // 리턴자료가 JSON임을 명시

//        THEN: 리턴 받은 JSON 목록의 0번째 요소의 replyWriter와 replyId가 예상과 일치하는지 확인
//        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath ;
//        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status ;
        result
                .andExpect(status().isOk()) // 200 코드 출력 확인
                // $: JSON 전체 데이터
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter)) // 첫 json의 replyWriter 검사
                .andExpect(jsonPath("$[0].replyId").value(replyId)); // 첫 JSON의 replyId 검사

    }

    @Test
    @Transactional
    @DisplayName("replyId 2번 조회시 얻어진 json객체의 replyWriter는 ㅇㅇ, replyId는 2번")
    public void findReplyTest() throws Exception {
        String replyWriter = "ㅇㅇ";
        long replyId = 2;
        String url = "/reply/2";

        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

//        $로만 끝나는 이유는 리턴받은 자료가 리스트가 아니기 때문에 인덱싱 할 필요가 없음
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                .andExpect(jsonPath("$.replyId").value(replyId));
    }

    @Test
    @Transactional
    @DisplayName("blogId 1번에 replyWriter는 또사라짐, replyContent 가기싫다를 넣고 등록 후 전체 댓글 조회시 픽스처 일치")
    public void insertReplyTest() throws Exception {
        long blogId = 1;
        String replyWriter = "또 사라짐";
        String replyContent = "가기 싫다";
        ReplyCreateDTO replyCreateDTO = ReplyCreateDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();
        String url = "/reply";
        String url2 = "/reply/1/all";

//        데이터 직렬화(JSON화)
        final String requestBody = objectMapper.writeValueAsString(replyCreateDTO);

        mockMvc.perform(post(url) // /reply 주소에 post 방식으로 요청을 넣고
                .contentType(MediaType.APPLICATION_JSON) // 전달 자료는 json이며
                .content(requestBody)); // 위에서 직렬화한 requestBody 변수를 전달

//        위에서 blogId로 지정한 1번글의 전체 데이터를 가져와서, 픽스쳐와 replyWriter, replyContent가 일치하는지 확인
        final ResultActions result = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[1].replyContent").value(replyContent))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번을 삭제할 경우, 글번호 2번의 댓글수는 2개, 그리고 단일 댓글 조회시 null")
    void deleteReply() throws Exception {
        long replyId = 3;
        long blogId = 2;
        String url = "/reply/"+replyId;

        mockMvc.perform(delete(url));

        assertEquals(replyRepository.findAllByBlogId(blogId).size(), 3);
        assertNull(replyRepository.findByReplyId(replyId));
    }

    @Test
    @Transactional
    public void updateReplyTest() throws Exception {
        long replyId = 3;
        String replyWriter = "냥냥이";
        String replyContent = "왈왈";
        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();
        String url = "/reply/" + replyId;

        final String requestBody = objectMapper.writeValueAsString(replyUpdateRequestDTO);

        mockMvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));

        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyId").value(replyId))
                .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                .andExpect(jsonPath("$.replyContent").value(replyContent));
    }
}