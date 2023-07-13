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
                <table class="table">
                    <thead>
                        <tr id="listHead">
                            <th scope="col" class="col-1">번호</th>
                            <th scope="col" class="col">제목</th>
                            <th scope="col" class="col-1">이름</th>
                            <th scope="col" class="col-1">작성일</th>
                            <th scope="col" class="col-1">수정일</th>
                            <th scope="col" class="col-1">조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="blog" items="${pageInfo.toList()}">
                            <tr id="listBody">
                                <td>${blog.blogId}</td>
                                <td onclick="location.href='/blog/detail/${blog.blogId}'">${blog.blogTitle}</td>
                                <td>${blog.writer}</td>
                                <td>${blog.publishedAt}</td>
                                <td>${blog.updatedAt}</td>
                                <td>${blog.blogCount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-1">
                        <a href="/blog/insert"><button type="button" class="btn btn-primary">글쓰기</button></a>
                    </div>
                    <div class="col-10">
                        <!-- 페이징 처리 버튼 자리 -->
                        <ul class="pagination justify-content-center">

                            <!-- 이전 페이지 -->
                            <!-- c:if 태그는 test 프로퍼티에 참, 거짓을 판단할 수 있는 식을 넣어주면 참인 경우에만 해당 요소를 표시 -->
                            <c:if test="${startPageNum != 1}">
                                <li class="page-item">
                                    <a class="page-link" href="/blog/list/${startPageNum - 1}">&laquo;</a>
                                </li>
                            </c:if>

                            <!-- 번호 버튼 
                            begin = 시작 숫자, end = 끝 숫자, var = 반복문 내에서 사용할 변수명 -->
                            <c:forEach begin="${startPageNum}" end="${endPageNum}" var="btnNum">
                                <li class="page-item ${currentPageNum == btnNum ? 'active' : ''}">
                                    <a class="page-link" href="/blog/list/${btnNum}">${btnNum}</a>
                                </li>
                            </c:forEach>

                            <!-- 다음 페이지 -->
                            <c:if test="${endPageNum < pageInfo.getTotalPages()}">
                                <li class="page-item">
                                    <a class="page-link" href="/blog/list/${endPageNum + 1}">&raquo;</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                const $headAlign = document.querySelectorAll('#listHead th:nth-child(1), th:nth-last-child(-n+4)');
                for (let th of $headAlign) {
                    th.style.textAlign = "center";
                }
                const $bodyAlign = document.querySelectorAll('#listBody td:nth-child(1), td:nth-last-child(-n+4)');
                for (let td of $bodyAlign) {
                    td.style.textAlign = "center";
                }
            </script>
        </body>

        </html>