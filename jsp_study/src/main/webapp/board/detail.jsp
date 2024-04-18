<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h1>Board detail Page</h1>
 
 <table border="1">
 	<tr>
 		<th>bno</th>
 		<td>${bvo.bno }</td>
 	</tr>
 	<tr>
 		<th>title</th>
 		<td>${bvo.title }</td>
 	</tr>
 	<tr>
 		<th>writer</th>
 		<td>${bvo.writer }</td>
 	</tr>
 	<tr>
 		<th>regdate</th>
 		<td>${bvo.regdate }</td>
 	</tr>
 	<tr>
 		<th>moddate</th>
 		<td>${bvo.moddate }</td>
 	</tr>
 	<tr>
 		<th>content</th>
 		<td>${bvo.content }</td>
 	</tr>
 </table>
 <c:if test="${bvo.writer eq ses.id }">
	 <a href="/brd/modify?bno=${bvo.bno }"><button>modify</button></a>
	 <a href="/brd/remove?bno=${bvo.bno }"><button>remove</button></a>
 </c:if>
 <a href="/brd/list"><button>list</button></a>
</body>
</html>