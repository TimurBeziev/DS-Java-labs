<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="main"/>

<html>
<head>
    <title>Title</title>
    <style>
        #nalog_info_header {
            background-color: #0096ff;
            color: #f2f2f2;
        }

        #customers {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #customers td, #customers th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #customers tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #customers tr:hover {
            background-color: #ddd;
        }

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>

<h1><b>Beziev Timur</b></h1>
<jsp:useBean id="time" class="java.util.Date"/>
<h1><b><fmt:formatDate type="time" value="${time}" pattern="HH:mm:ss"/></b></h1>
<br>

<h2>Все платежи</h2>
<fmt:message key = "table.payments" var = "payments"/>
${payments}
<%--<h1><fmt:message key="payments" /></h1>--%>
<h3 id="nalog_info_header">Max NALOG Payment: ${max_payment}</h3>
<h3 id="nalog_info_header"> NALOG count: ${nalog_count}</h3>

<table id="customers">
    <tr>
        <th>ID</th>
        <th>TYPE</th>
        <th>PAYER</th>
        <th>PRICE</th>
    </tr>
    <c:forEach items="${users}" var="container">
        <tr>
            <td>${container.id}</td>
            <td>${container.type}</td>
            <td>${container.payer}</td>
            <td>${container.price}</td>
        </tr>
    </c:forEach>
</table>

<form id="localeForm" action="${pageContext.request.contextPath}/" method="post">
    <button form="localeForm" type="submit" name="locale" value="en">En</button>
    <button form="localeForm" type="submit" name="locale" value="ru"> Ru</button>
</form>

</body>
</html>
