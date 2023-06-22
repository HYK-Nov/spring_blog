<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Insert title here</title>
            <!-- CSS only -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
                crossorigin="anonymous">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        </head>

        <body>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <h5><strong>${blog.blogTitle}</strong></h5>
                    </div>
                </div>
                <div class="row border-bottom">
                    <div class="col">${blog.writer}</div>
                    <div class="col-1">${blog.publishedAt}</div>
                    <div class="col-1">${blog.blogCount}</div>
                </div>
                <div class="row border-bottom">
                    <div class="col" style="padding-bottom: 100px;">
                        ${blog.blogContent}
                    </div>
                </div>
                <div class="row">
                    <div class="col-1">
                        <a href="/blog/list"><button type="button" class="btn btn-secondary">목록</button></a>
                    </div>
                    <div class="col-1">
                        <form action="/blog/updateform" method="POST">
                            <input type="hidden" class="form-control" name="blogId" value="${blog.blogId}">
                            <button type="submit" class="btn btn-light">수정</button>
                        </form>
                    </div>
                    <div class="col"></div>
                    <div class="col-1">
                        <form action="/blog/delete" method="POST">
                            <input type="hidden" class="form-control" name="blogId" value="${blog.blogId}">
                            <button type="submit" class="btn btn-outline-danger">삭제</button>
                        </form>
                    </div>
                </div>
                <div id="replies"></div>
                <div class="row">
                    <!-- 비동기 form의 경우는 목적지로 이동하지 않고 페이지 내에서 처리가 되므로
                    action을 가지지 않음. 그리고 제출버튼도 제출 기능을 막고 fetch 요청만 넣음 -->
                    <div class="col-1">
                        <input type="text" class="form-control" id="replyWriter" name="replyWriter" placeholder="글쓴이">
                    </div>
                    <div class="col-10">
                        <input type="text" class="form-control" id="replyContent" name="replyContent" placeholder="내용">
                    </div>
                    <div class="col-1">
                        <button type="button" class="btn btn-primary" id="replySubmit">글쓰기</button>
                    </div>
                </div>
            </div>
            <script>
                // 글 구성에 필요한 글 번호를 JS 변수에 저장
                let blogId = "${blog.blogId}";

                // blogId를 받아 전체 데이터를 JS내부로 가져오는 함수
                function getAllReplies(bId) {
                    /* 
                        <%-- jsp와 js가 모두 ${변수명} 문법을 공유하고, 이 중 .jsp파일에서는
                        ${}의 해석을 jsp식으로 먼저 하기 때문에, 해당 ${}가 백틱 내부에서 쓰이는 경우
                        \${} 형식으로 \를 추가로 왼쪽에 붙여서 jsp용으로 작성한 것이 아님을 명시해야 함 --%>
                    */
                    let url = `/reply/\${bId}/all`;
                    let str = '';
                    fetch(url, { method: 'get' }) // get방식으로 위 주소에 요청 넣기
                        .then(res => res.json()) // 응답받은 요소중 json만 뽑기
                        .then(replies => { // 뽑아온 json으로 처리 작업하기
                            // for (reply of replies) {
                            //     console.log(reply);
                            //     str += `<h3>글쓴이: \${reply.replyWriter}, 댓글내용: \${reply.replyContent}</h3>`;
                            // }
                            // console.log(str); // 저장된 태그 확인
                            // const $replies = document.querySelector('#replies');
                            // $replies.innerHTML = str;

                            replies.map((reply, i) => { // 첫 파라미터: 반복대상자료, 두번째 파라미터: 순번
                                str += `
                                    <div class="row">
                                        <div class="col">\${reply.replyWriter}</div>
                                        <div class="col-9">\${reply.replyContent}</div>
                                        <div class="col">\${reply.updatedAt}</div>
                                        <div class="col"><i class="bi bi-x-lg deleteReplyBtn btn" data-replyId="\${reply.replyId}"></i></div>
                                    </div>
                                    <hr>
                                    `;
                            });
                            const $replies = document.querySelector('#replies');
                            $replies.innerHTML = str;
                        });
                }
                // 함수 호출
                getAllReplies(blogId);

                let filter = ['욕아님', '심한욕'];

                // 해당 함수 실행 시 비동기 폼에 작성된 글쓴이, 내용으로 댓글 입력
                function insertReply() {
                    const $replyWriter = document.querySelector("#replyWriter");
                    const $replyContent = document.querySelector("#replyContent");
                    let url = `/reply`;

                    if ($replyWriter.value.trim() === "") { // trim() 공백 제거
                        alert("글쓴이를 채워주세요");
                        return;
                    }

                    if ($replyContent.value.trim() === "") {
                        alert("내용을 채워주세요");
                        return;
                    }

                    for (ban of filter) {
                        if ($replyContent.value.match(ban)) {
                            alert("욕 하지 마라");
                            return;
                        }
                    }

                    fetch(url, {
                        method: 'post',
                        headers: { // header에 보내는 데이터의 자료형에 대해 기술
                            // json 데이터를 요청과 함께 전달, @RequestBody를 입력받는 로직에 추가
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify({ // 여기에 실질적으로 요청과 보낼 json 정보를 기술함
                            replyWriter: $replyWriter.value,
                            replyContent: $replyContent.value,
                            blogId: "${blog.blogId}",
                        }), // insert 로직이기 때문에 response에 실제 화면에서 사용할 데이터 전송X
                    }).then(() => {
                        // 댓글 작성 후 폼에 작성되어있던 내용 삭제
                        $replyWriter.value = "";
                        $replyContent.value = "";
                        alert("댓글 작성 완료");
                        // 댓글 갱신 추가로 호출
                        getAllReplies(blogId);
                    })
                }

                // 제출 버튼에 이벤트 연결하기
                const $replySubmit = document.querySelector("#replySubmit");
                $replySubmit.addEventListener("click", insertReply);


                // 이벤트 객체를 활용해야 이벤트 위임을 구현하기 수월하므로 먼저 html 객체부터 가져옴
                // 모든 댓글을 포함하고 있으면서 가장 가까운 영역인 #replies에 설정.
                const $replies = document.querySelector("#replies");
                $replies.onclick = (e) => {
                    // 클릭한 요소가 #replies의 자손태그인 .deleteReplyBtn 인지 검사하기
                    // 이벤트 객체 .target.dataset.replyId
                    if (!e.target.matches('#replies .deleteReplyBtn')) {
                        return;
                    }

                    // 이벤트 객체의 target 속성의 dataset 속성 내부에 댓글번호가 있으므로 확인
                    const $replyId = e.target.dataset.replyid;

                    if (confirm("삭제하시겠습니까?")) {
                        let url = `/reply/\${$replyId}`;

                        fetch(url, { method: 'delete', })
                            .then(() => {
                                getAllReplies(blogId);
                            });
                    }
                }
            </script>
        </body>

        </html>