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
                <form action="/blog/update" method="POST"> <!-- action 속성을 주지 않아도 자동으로 현재 주소로 전송 -->
                    <div class="row">
                        <div class="col-3">
                            <label for="writer" class="form-label">글쓴이</label>
                            <input type="text" class="form-control" id="writer" name="writer" value="${blog.writer}"
                                disabled readonly>
                        </div>
                        <div class="col-6">
                            <label for="title" class="form-label">제목</label>
                            <input type="text" class="form-control" placeholder="제목을 입력해주세요" id="title" name="blogTitle"
                                value="${blog.blogTitle}">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-9">
                            <label for="content" class="form-label">내용</label>
                            <textarea class="form-control" placeholder="내용을 입력해주세요" rows="10" id="content"
                                name="blogContent">${blog.blogContent}</textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-9">
                            <input type="hidden" name="blogId" value="${blog.blogId}">
                            <button type="submit" class="btn btn-outline-primary">작성</button>
                        </div>
                    </div>
                </form>
            </div>
        </body>

        </html>