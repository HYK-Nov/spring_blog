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
                    <div class="col"></div>
                    <div class="col-4">
                        <form action="/signup" method="POST">
                            <div class="d-grid gap-2">
                                아이디: <input type="text" class="form-control" name="loginId" placeholder="아이디" required>
                                비밀번호: <input type="password" class="form-control" name="password" placeholder="비밀번호"
                                    required>
                                이메일: <input type="email" class="form-control" name="email" placeholder="이메일" required>
                                <input type="submit" class="btn btn-primary" value="가입하기">
                            </div>
                        </form>
                    </div>
                    <div class="col"></div>
                </div>
            </div>
            </div>
        </body>

        </html>