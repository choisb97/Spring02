<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Spring NullPointerException Message **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** Spring NullPointerException Message **</h2>
	<h2>서버에 문제가 발생 했습니다 ~</h2>
	<h2>exception Message => ${exception.message}</h2>
	<%-- <h2>exception ToString => ${exception.toString}</h2> --%>
	<h2>잠시만 기다려 주세요 ~~</h2>
	<img src="resources/images/simson9.gif" width="300" height="250">
	<br><br>
	<a href="#" onclick="history.back()">[이전으로 돌아가기]</a>&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>