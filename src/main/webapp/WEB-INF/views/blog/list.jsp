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
                <h1 class="text-center">게시물 목록</h1>
                <table class="table table-hover">
                    <thead>
                        <tr id="listHead">
                            <th scope="col" class="col-1">번호</th>
                            <th scope="col" class="col-8">제목</th>
                            <th scope="col" class="col-1">이름</th>
                            <th scope="col" class="col-1">작성일</th>
                            <th scope="col" class="col-1">조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="blog" items="${blogList}">
                            <tr id="listBody">
                                <td>${blog.blogId}</td>
                                <td onclick="location.href='/blog/detail/${blog.blogId}'">${blog.blogTitle}</td>
                                <td>${blog.writer}</td>
                                <td>${blog.publishedAt}</td>
                                <td>${blog.blogCount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-1">
                        <a href="/blog/insert"><button type="button" class="btn btn-outline-primary">글쓰기</button></a>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                const headAlign = document.querySelectorAll('#listHead th:nth-child(1), th:nth-last-child(-n+2)');
                for (let $th of headAlign) {
                    $th.style.textAlign = "center";
                }
                const bodyAlign = document.querySelectorAll('#listBody td:nth-child(1), td:nth-last-child(-n+2)');
                for (let $td of bodyAlign) {
                    $td.style.textAlign = "center";
                }
            </script>
        </body>

        </html>