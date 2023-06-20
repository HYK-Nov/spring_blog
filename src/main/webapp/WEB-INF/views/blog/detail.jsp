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
                        <form action="/blog/updateform" method="post">
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
                <div class="row">
                    <div id="replies"></div>
                </div>
            </div>
            <script>
                // 글 구성에 필요한 글 번호를 JS 변수에 저장
                let blogId = "${blog.blogId}";

                // blogId를 받아 전체 데이터를 JS내부로 가져오는 함수
                function getAllReplies(bId) {
                    let url = `http://localhost:8080/reply/${bId}/all`;
                    fetch(url, { method: 'get' }) // get방식으로 위 주소에 요청 넣기
                        .then(res => res.json()) // 응답받은 요소중 json만 뽑기
                        .then(data => { // 뽑아온 json으로 처리 작업하기
                            console.log(data);
                        });
                }

                getAllReplies(blogId);
            </script>
        </body>

        </html>