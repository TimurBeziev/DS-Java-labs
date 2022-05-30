<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="stocksRepository" type="kr.repositories.StocksRepository" scope="session"/>

<div class="container">
    <h1>stocks</h1>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Stock Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${stocksRepository.stocks}" var="element">
            <tr>
                <td>${element.uniqueID}</td>
                <td>${element.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
