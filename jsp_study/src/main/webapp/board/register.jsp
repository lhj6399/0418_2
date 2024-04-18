<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Board Register Page</h1>

<form action="/brd/insert" method="post">
	title : <input type="text" name="title"> <br>
	writer : <input type="text" name="writer" value="${ses.id }" readonly="readonly"> <br>
	content : <textarea rows="10" cols="30" name="content"></textarea> <br>
	<button type="submit">전송</button>
</form>
</body>
</html>